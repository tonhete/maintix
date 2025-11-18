using Microsoft.EntityFrameworkCore;
using Maintix_API.Data;
using Maintix_API.Models;

namespace Maintix_API.Repositories
{
    public class HistoricoRepository : IHistoricoRepository
    {
        private readonly MaintixDbContext _context;

        public HistoricoRepository(MaintixDbContext context)
        {
            _context = context;
        }

        public async Task<IEnumerable<Historico>> GetAllAsync()
        {
            var list = await _context.Historico.ToListAsync();

            foreach (var h in list)
            {
                // Equipo numero de serie
                h.EquipoNumeroSerie = await _context.Equipos
                    .Where(e => e.Id == h.EquipoId)
                    .Select(e => e.NumeroSerie)
                    .FirstOrDefaultAsync();

                // Tipo mantenimiento por clase (A/B/C) heurística
                if (!string.IsNullOrEmpty(h.Clase))
                {
                    var tipo = await _context.TiposMantenimiento
                        .FirstOrDefaultAsync(t => t.Nombre.ToUpper().Contains(h.Clase.ToUpper()));
                    h.TipoMantenimientoNombre = tipo?.Nombre;
                }

                // Intentar recuperar fechas desde el mantenimiento más reciente de ese equipo
                var mantenimiento = await _context.Mantenimientos
                    .Where(m => m.EquipoId == h.EquipoId && m.FechaFin != null)
                    .OrderByDescending(m => m.FechaFin)
                    .FirstOrDefaultAsync();

                if (mantenimiento != null)
                {
                    h.FechaMantenimiento = mantenimiento.FechaInicio;
                    h.FechaFinalizacion = mantenimiento.FechaFin;
                }
            }

            return list;
        }

        public async Task<IEnumerable<Historico>> GetByEquipoIdAsync(int equipoId)
        {
            var list = await _context.Historico
                .Where(h => h.EquipoId == equipoId)
                .ToListAsync();

            foreach (var h in list)
            {
                h.EquipoNumeroSerie = await _context.Equipos
                    .Where(e => e.Id == h.EquipoId)
                    .Select(e => e.NumeroSerie)
                    .FirstOrDefaultAsync();

                if (!string.IsNullOrEmpty(h.Clase))
                {
                    var tipo = await _context.TiposMantenimiento
                        .FirstOrDefaultAsync(t => t.Nombre.ToUpper().Contains(h.Clase.ToUpper()));
                    h.TipoMantenimientoNombre = tipo?.Nombre;
                }

                var mantenimiento = await _context.Mantenimientos
                    .Where(m => m.EquipoId == h.EquipoId && m.FechaFin != null)
                    .OrderByDescending(m => m.FechaFin)
                    .FirstOrDefaultAsync();

                if (mantenimiento != null)
                {
                    h.FechaMantenimiento = mantenimiento.FechaInicio;
                    h.FechaFinalizacion = mantenimiento.FechaFin;
                }
            }

            return list;
        }

        public async Task<Historico?> GetByIdAsync(int id)
        {
            var h = await _context.Historico.FindAsync(id);
            if (h == null) return null;

            h.EquipoNumeroSerie = await _context.Equipos
                .Where(e => e.Id == h.EquipoId)
                .Select(e => e.NumeroSerie)
                .FirstOrDefaultAsync();

            if (!string.IsNullOrEmpty(h.Clase))
            {
                var tipo = await _context.TiposMantenimiento
                    .FirstOrDefaultAsync(t => t.Nombre.ToUpper().Contains(h.Clase.ToUpper()));
                h.TipoMantenimientoNombre = tipo?.Nombre;
            }

            var mantenimiento = await _context.Mantenimientos
                .Where(m => m.EquipoId == h.EquipoId && m.FechaFin != null)
                .OrderByDescending(m => m.FechaFin)
                .FirstOrDefaultAsync();

            if (mantenimiento != null)
            {
                h.FechaMantenimiento = mantenimiento.FechaInicio;
                h.FechaFinalizacion = mantenimiento.FechaFin;
            }

            return h;
        }

        public async Task<Historico> CreateAsync(Historico historico)
        {
            _context.Historico.Add(historico);
            await _context.SaveChangesAsync();
            return historico;
        }

        public async Task<Historico?> UpdateAsync(int id, Historico historico)
        {
            var existing = await _context.Historico.FindAsync(id);
            if (existing == null) return null;

            existing.EquipoId = historico.EquipoId;
            existing.HorasMaquina = historico.HorasMaquina;
            existing.Clase = historico.Clase;
            existing.Operario = historico.Operario;
            existing.Incidencias = historico.Incidencias;
            existing.Finalizado = historico.Finalizado;

            await _context.SaveChangesAsync();
            return existing;
        }

        public async Task<bool> DeleteAsync(int id)
        {
            var historico = await _context.Historico.FindAsync(id);
            if (historico == null) return false;

            _context.Historico.Remove(historico);
            await _context.SaveChangesAsync();
            return true;
        }
    }
}
