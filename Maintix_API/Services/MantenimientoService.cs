using Microsoft.EntityFrameworkCore;
using Maintix_API.Data;
using Maintix_API.DTOs;
using Maintix_API.Models;
using System.Linq;

namespace Maintix_API.Services
{
    public class MantenimientoService : IMantenimientoService
    {
        private readonly MaintixDbContext _context;

        public MantenimientoService(MaintixDbContext context)
        {
            _context = context;
        }

        // Actualizar horas del equipo
        public async Task<bool> ActualizarHorasEquipoAsync(int equipoId, int horasNuevas)
        {
            var equipo = await _context.Equipos.FindAsync(equipoId);
            if (equipo == null) return false;

            int incremento = horasNuevas - equipo.HorasActuales;
            if (incremento < 0) return false;

            equipo.HorasActuales = horasNuevas;
            equipo.ContadorTipoA += incremento;
            equipo.ContadorTipoB += incremento;
            equipo.ContadorTipoC += incremento;

            await _context.SaveChangesAsync();
            return true;
        }

        // Verificar alertas para un equipo
        public async Task<AlertaMantenimientoDto?> VerificarAlertasEquipoAsync(int equipoId)
        {
            var equipo = await _context.Equipos
                .Include(e => e.TipoMaquinaria)
                .FirstOrDefaultAsync(e => e.Id == equipoId);

            if (equipo == null) return null;

            var alertas = new List<string>();

            if (equipo.TipoMaquinaria?.MantenimientoA != null && equipo.ContadorTipoA >= equipo.TipoMaquinaria.MantenimientoA)
                alertas.Add("Tipo A");

            if (equipo.TipoMaquinaria?.MantenimientoB != null && equipo.ContadorTipoB >= equipo.TipoMaquinaria.MantenimientoB)
                alertas.Add("Tipo B");

            if (equipo.TipoMaquinaria?.MantenimientoC != null && equipo.ContadorTipoC >= equipo.TipoMaquinaria.MantenimientoC)
                alertas.Add("Tipo C");

            return new AlertaMantenimientoDto
            {
                EquipoId = equipo.Id,
                NumeroSerie = equipo.NumeroSerie,
                DescripcionEquipo = equipo.TipoMaquinaria?.Descripcion,
                HorasActuales = equipo.HorasActuales,
                MantenimientosPendientes = alertas
            };
        }

        // Crear mantenimiento con checklist
        public async Task<MantenimientoConChecklistDto?> CrearMantenimientoConChecklistAsync(CrearMantenimientoDto dto)
        {
            var equipo = await _context.Equipos.FindAsync(dto.EquipoId);
            if (equipo == null) return null;

            var tipoMantenimiento = await _context.TiposMantenimiento.FindAsync(dto.TipoMantenimientoId);
            if (tipoMantenimiento == null) return null;

            var mantenimiento = new Mantenimiento
            {
                EquipoId = dto.EquipoId,
                TipoMantenimientoId = dto.TipoMantenimientoId,
                FechaInicio = DateTime.Now,
                Estado = "pendiente"
            };

            _context.Mantenimientos.Add(mantenimiento);
            await _context.SaveChangesAsync();

            // Obtener items template para el tipo de máquina y tipo de mantenimiento
            var itemsTemplate = await _context.ItemsMantenimiento
                .Where(i => i.TipoMaquinaId == equipo.TipoMaquinariaId && i.TipoMantenimientoId == dto.TipoMantenimientoId)
                .OrderBy(i => i.Orden)
                .ToListAsync();

            var checklistItems = new List<ChecklistItemDto>();

            foreach (var item in itemsTemplate)
            {
                var checklistItem = new ChecklistMantenimiento
                {
                    MantenimientoId = mantenimiento.Id,
                    ItemId = item.Id,
                    Completado = false
                };

                _context.ChecklistMantenimiento.Add(checklistItem);
                await _context.SaveChangesAsync();

                checklistItems.Add(new ChecklistItemDto
                {
                    ChecklistId = checklistItem.Id,
                    ItemId = item.Id,
                    Descripcion = item.Descripcion,
                    DescripcionDetallada = item.DescripcionDetallada,
                    Herramientas = item.Herramientas,
                    ImagenUrl = item.ImagenUrl,
                    Orden = item.Orden,
                    Completado = false
                });
            }

            return new MantenimientoConChecklistDto
            {
                MantenimientoId = mantenimiento.Id,
                EquipoId = mantenimiento.EquipoId,
                NumeroSerie = equipo.NumeroSerie,
                TipoMantenimiento = tipoMantenimiento.Nombre,
                FechaInicio = mantenimiento.FechaInicio,
                Estado = mantenimiento.Estado,
                Items = checklistItems
            };
        }

        // Actualizar checklist
        public async Task<bool> ActualizarChecklistAsync(int mantenimientoId, ActualizarChecklistDto dto)
        {
            var mantenimiento = await _context.Mantenimientos.FindAsync(mantenimientoId);
            if (mantenimiento == null) return false;

            foreach (var item in dto.Items)
            {
                var checklistItem = await _context.ChecklistMantenimiento.FindAsync(item.ChecklistId);
                if (checklistItem != null && checklistItem.MantenimientoId == mantenimientoId)
                {
                    checklistItem.Completado = item.Completado;
                    checklistItem.Observaciones = item.Observaciones;
                }
            }

            await _context.SaveChangesAsync();
            return true;
        }

        // Finalizar mantenimiento (añade histórico)
        public async Task<Historico?> FinalizarMantenimientoAsync(int mantenimientoId, FinalizarMantenimientoDto dto)
        {
            var mantenimiento = await _context.Mantenimientos
                .Include(m => m.Equipo)
                    .ThenInclude(e => e.TipoMaquinaria)
                .Include(m => m.TipoMantenimiento)
                .FirstOrDefaultAsync(m => m.Id == mantenimientoId);

            if (mantenimiento == null) return null;

            mantenimiento.FechaFin = DateTime.Now;
            mantenimiento.Estado = "finalizado";

            var usuario = await _context.Usuarios.FindAsync(dto.UsuarioId);
            var nombreOperario = usuario?.Email ?? "Desconocido";

            var historico = new Historico
            {
                EquipoId = mantenimiento.EquipoId,
                HorasMaquina = mantenimiento.Equipo?.HorasActuales,
                Clase = ObtenerClaseMantenimiento(mantenimiento.TipoMantenimientoId),
                Operario = nombreOperario,
                Incidencias = dto.Incidencias,
                Finalizado = true
            };

            // Asignar los metadatos en memoria (NotMapped) para devolverlos en la respuesta
            historico.FechaMantenimiento = mantenimiento.FechaInicio;
            historico.FechaFinalizacion = mantenimiento.FechaFin;
            historico.EquipoNumeroSerie = mantenimiento.Equipo?.NumeroSerie;
            historico.TipoMantenimientoNombre = mantenimiento.TipoMantenimiento?.Nombre;

            _context.Historico.Add(historico);

            // Resetear contadores según tipo de mantenimiento
            var tipoMant = mantenimiento.TipoMantenimiento;
            if (tipoMant != null && mantenimiento.Equipo != null)
            {
                var clase = ObtenerClaseMantenimiento(mantenimiento.TipoMantenimientoId);
                switch (clase)
                {
                    case "A":
                        mantenimiento.Equipo.ContadorTipoA = 0;
                        break;
                    case "B":
                        mantenimiento.Equipo.ContadorTipoB = 0;
                        break;
                    case "C":
                        mantenimiento.Equipo.ContadorTipoC = 0;
                        break;
                }
            }

            await _context.SaveChangesAsync();
            return historico;
        }

        // Obtener mantenimiento con checklist (para detalle)
        public async Task<MantenimientoConChecklistDto?> ObtenerMantenimientoConChecklistAsync(int mantenimientoId)
        {
            var mantenimiento = await _context.Mantenimientos
                .Include(m => m.Equipo)
                .Include(m => m.TipoMantenimiento)
                .FirstOrDefaultAsync(m => m.Id == mantenimientoId);

            if (mantenimiento == null) return null;

            var checklistItems = await _context.ChecklistMantenimiento
                .Where(c => c.MantenimientoId == mantenimientoId)
                .Include(c => c.Item)
                .OrderBy(c => c.Item.Orden)
                .Select(c => new ChecklistItemDto
                {
                    ChecklistId = c.Id,
                    ItemId = c.ItemId,
                    Descripcion = c.Item.Descripcion,
                    DescripcionDetallada = c.Item.DescripcionDetallada,
                    Herramientas = c.Item.Herramientas,
                    ImagenUrl = c.Item.ImagenUrl,
                    Orden = c.Item.Orden,
                    Completado = c.Completado,
                    Observaciones = c.Observaciones
                })
                .ToListAsync();

            return new MantenimientoConChecklistDto
            {
                MantenimientoId = mantenimiento.Id,
                EquipoId = mantenimiento.EquipoId,
                NumeroSerie = mantenimiento.Equipo?.NumeroSerie,
                TipoMantenimiento = mantenimiento.TipoMantenimiento?.Nombre,
                FechaInicio = mantenimiento.FechaInicio,
                Estado = mantenimiento.Estado,
                Items = checklistItems
            };
        }

        // Verificar alertas de todos los equipos
        public async Task<List<AlertaMantenimientoDto>> VerificarTodasAlertasAsync()
        {
            var equipos = await _context.Equipos
                .Include(e => e.TipoMaquinaria)
                .ToListAsync();

            var alertas = new List<AlertaMantenimientoDto>();

            foreach (var equipo in equipos)
            {
                if (equipo.TipoMaquinaria == null) continue;

                var mantenimientosPendientesIds = await _context.Mantenimientos
                    .Where(m => m.EquipoId == equipo.Id && (m.Estado == "pendiente" || m.Estado == "pendiente_asignacion"))
                    .Select(m => m.TipoMantenimientoId)
                    .ToListAsync();

                var mantenimientosPendientes = new List<string>();
                var razones = new List<string>();

                if (equipo.TipoMaquinaria.MantenimientoA > 0 && equipo.ContadorTipoA >= equipo.TipoMaquinaria.MantenimientoA)
                {
                    var tipoA = await _context.TiposMantenimiento.FirstOrDefaultAsync(t => t.Nombre.ToUpper().Contains("A"));
                    if (tipoA != null && !mantenimientosPendientesIds.Contains(tipoA.Id))
                    {
                        mantenimientosPendientes.Add("Tipo A");
                        razones.Add($"Horas tipo A: {equipo.ContadorTipoA}/{equipo.TipoMaquinaria.MantenimientoA}");
                    }
                }

                if (equipo.TipoMaquinaria.MantenimientoB > 0 && equipo.ContadorTipoB >= equipo.TipoMaquinaria.MantenimientoB)
                {
                    var tipoB = await _context.TiposMantenimiento.FirstOrDefaultAsync(t => t.Nombre.ToUpper().Contains("B"));
                    if (tipoB != null && !mantenimientosPendientesIds.Contains(tipoB.Id))
                    {
                        mantenimientosPendientes.Add("Tipo B");
                        razones.Add($"Horas tipo B: {equipo.ContadorTipoB}/{equipo.TipoMaquinaria.MantenimientoB}");
                    }
                }

                if (equipo.TipoMaquinaria.MantenimientoC > 0 && equipo.ContadorTipoC >= equipo.TipoMaquinaria.MantenimientoC)
                {
                    var tipoC = await _context.TiposMantenimiento.FirstOrDefaultAsync(t => t.Nombre.ToUpper().Contains("C"));
                    if (tipoC != null && !mantenimientosPendientesIds.Contains(tipoC.Id))
                    {
                        mantenimientosPendientes.Add("Tipo C");
                        razones.Add($"Horas tipo C: {equipo.ContadorTipoC}/{equipo.TipoMaquinaria.MantenimientoC}");
                    }
                }

                if (mantenimientosPendientes.Any())
                {
                    alertas.Add(new AlertaMantenimientoDto
                    {
                        EquipoId = equipo.Id,
                        NumeroSerie = equipo.NumeroSerie,
                        DescripcionEquipo = equipo.TipoMaquinaria.Descripcion,
                        HorasActuales = equipo.HorasActuales,
                        MantenimientosPendientes = mantenimientosPendientes
                    });
                }
            }

            return alertas;
        }

        // Crear mantenimientos masivos
        public async Task<List<MantenimientoConChecklistDto>> CrearMantenimientosMasivosAsync(CrearMantenimientoMasivoDto dto)
        {
            var resultados = new List<MantenimientoConChecklistDto>();

            foreach (var item in dto.Mantenimientos)
            {
                var resultado = await CrearMantenimientoConChecklistAsync(item);
                if (resultado != null)
                {
                    var mantenimiento = await _context.Mantenimientos.FindAsync(resultado.MantenimientoId);
                    if (mantenimiento != null)
                    {
                        mantenimiento.Estado = "pendiente_asignacion";
                        await _context.SaveChangesAsync();
                    }
                    resultados.Add(resultado);
                }
            }

            return resultados;
        }

        // Asignar operario
        public async Task<bool> AsignarOperarioAsync(int mantenimientoId, int operarioId)
        {
            var mantenimiento = await _context.Mantenimientos.FindAsync(mantenimientoId);
            if (mantenimiento == null) return false;

            var operario = await _context.Usuarios.FindAsync(operarioId);
            if (operario == null) return false;

            mantenimiento.OperarioAsignadoId = operarioId;
            mantenimiento.Estado = "pendiente";

            await _context.SaveChangesAsync();
            return true;
        }

        // Helper: determinar clase A/B/C a partir del nombre del tipo
        private string? ObtenerClaseMantenimiento(int tipoMantenimientoId)
        {
            var tipo = _context.TiposMantenimiento.Find(tipoMantenimientoId);
            if (tipo == null) return null;

            return tipo.Nombre.ToUpper() switch
            {
                "A" or "TIPO A" => "A",
                "B" or "TIPO B" => "B",
                "C" or "TIPO C" => "C",
                _ => null
            };
        }
    }
}