using Microsoft.EntityFrameworkCore;
using Maintix_API.Data;
using Maintix_API.DTOs;
using Maintix_API.Models;

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

            // Calcular incremento de horas
            int incremento = horasNuevas - equipo.HorasActuales;
            if (incremento < 0) return false; // No permitir retroceder horas

            equipo.HorasActuales = horasNuevas;
            equipo.ContadorTipoA += incremento;
            equipo.ContadorTipoB += incremento;
            equipo.ContadorTipoC += incremento;

            await _context.SaveChangesAsync();
            return true;
        }

        // Verificar alertas de mantenimiento
        public async Task<AlertaMantenimientoDto?> VerificarAlertasEquipoAsync(int equipoId)
        {
            var equipo = await _context.Equipos
                .Include(e => e.TipoMaquinaria)
                .FirstOrDefaultAsync(e => e.Id == equipoId);

            if (equipo == null) return null;

            var alertas = new List<string>();

            // Verificar si necesita Mantenimiento A
            if (equipo.TipoMaquinaria?.MantenimientoA != null && 
                equipo.ContadorTipoA >= equipo.TipoMaquinaria.MantenimientoA)
            {
                alertas.Add("Tipo A");
            }

            // Verificar si necesita Mantenimiento B
            if (equipo.TipoMaquinaria?.MantenimientoB != null && 
                equipo.ContadorTipoB >= equipo.TipoMaquinaria.MantenimientoB)
            {
                alertas.Add("Tipo B");
            }

            // Verificar si necesita Mantenimiento C
            if (equipo.TipoMaquinaria?.MantenimientoC != null && 
                equipo.ContadorTipoC >= equipo.TipoMaquinaria.MantenimientoC)
            {
                alertas.Add("Tipo C");
            }

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
            var equipo = await _context.Equipos
                .Include(e => e.TipoMaquinaria)
                .FirstOrDefaultAsync(e => e.Id == dto.EquipoId);

            if (equipo == null) return null;

            var tipoMantenimiento = await _context.TiposMantenimiento.FindAsync(dto.TipoMantenimientoId);
            if (tipoMantenimiento == null) return null;

            // Crear el mantenimiento
            var mantenimiento = new Mantenimiento
            {
                EquipoId = dto.EquipoId,
                TipoMantenimientoId = dto.TipoMantenimientoId,
                FechaInicio = DateTime.Now,
                Estado = "pendiente"
            };

            _context.Mantenimientos.Add(mantenimiento);
            await _context.SaveChangesAsync();

            // Obtener los items del checklist para este tipo de máquina y tipo de mantenimiento
            var itemsTemplate = await _context.ItemsMantenimiento
                .Where(i => i.TipoMaquinaId == equipo.TipoMaquinariaId && 
                           i.TipoMantenimientoId == dto.TipoMantenimientoId)
                .OrderBy(i => i.Orden)
                .ToListAsync();

            var checklistItems = new List<ChecklistItemDto>();

            // Crear los items del checklist para este mantenimiento
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
                    Orden = item.Orden,
                    Completado = false
                });
            }

            return new MantenimientoConChecklistDto
            {
                MantenimientoId = mantenimiento.Id,
                EquipoId = equipo.Id,
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

        // Finalizar mantenimiento
        public async Task<bool> FinalizarMantenimientoAsync(int mantenimientoId, FinalizarMantenimientoDto dto)
        {
            var mantenimiento = await _context.Mantenimientos
                .Include(m => m.Equipo)
                .FirstOrDefaultAsync(m => m.Id == mantenimientoId);

            if (mantenimiento == null) return false;

            // Actualizar el mantenimiento
            mantenimiento.FechaFin = DateTime.Now;
            mantenimiento.Estado = "finalizado";

            // Obtener el nombre del usuario
            var usuario = await _context.Usuarios.FindAsync(dto.UsuarioId);
            var nombreOperario = usuario?.Nombre ?? "Desconocido";

            // Crear registro en histórico
            var historico = new Historico
            {
                EquipoId = mantenimiento.EquipoId,
                HorasMaquina = mantenimiento.Equipo.HorasActuales,
                Clase = ObtenerClaseMantenimiento(mantenimiento.TipoMantenimientoId),
                Operario = nombreOperario,
                Incidencias = dto.Incidencias,
                Finalizado = true
            };

            _context.Historico.Add(historico);

            // Resetear el contador correspondiente según el tipo de mantenimiento
            var tipoMant = await _context.TiposMantenimiento.FindAsync(mantenimiento.TipoMantenimientoId);
            if (tipoMant != null && mantenimiento.Equipo != null)
            {
                switch (tipoMant.Nombre.ToUpper())
                {
                    case "A":
                    case "TIPO A":
                        mantenimiento.Equipo.ContadorTipoA = 0;
                        break;
                    case "B":
                    case "TIPO B":
                        mantenimiento.Equipo.ContadorTipoB = 0;
                        break;
                    case "C":
                    case "TIPO C":
                        mantenimiento.Equipo.ContadorTipoC = 0;
                        break;
                }
            }

            await _context.SaveChangesAsync();
            return true;
        }

        // Obtener mantenimiento con checklist
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

        // Helper: Obtener clase de mantenimiento
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
