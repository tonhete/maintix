using Microsoft.EntityFrameworkCore;
using Maintix_API.Data;
using Maintix_API.Models;

namespace Maintix_API.Repositories
{
    public class MantenimientoRepository : IMantenimientoRepository
    {
        private readonly MaintixDbContext _context;

        public MantenimientoRepository(MaintixDbContext context)
        {
            _context = context;
        }

        public async Task<IEnumerable<Mantenimiento>> GetAllAsync()
        {
            var list = await _context.Mantenimientos
                .Include(m => m.Equipo)
                    .ThenInclude(e => e.TipoMaquinaria)
                .Include(m => m.TipoMantenimiento)
                .ToListAsync();

            // Rellenar campos no mapeados
            foreach (var m in list)
            {
                m.NumeroSerie = m.Equipo?.NumeroSerie;
                m.HorasActuales = m.Equipo?.HorasActuales ?? 0;
                m.TipoMaquinariaInfo = m.Equipo?.TipoMaquinaria;
                m.MaquinaNombre = m.Equipo?.TipoMaquinaria?.Descripcion;
                
                // ProgresoChecklist: calcular con subconsulta (eficiente)
                var totals = await _context.ChecklistMantenimiento
                    .Where(c => c.MantenimientoId == m.Id)
                    .Select(c => c.Completado ? 1 : 0)
                    .ToListAsync();

                m.ProgresoChecklist = totals.Count == 0 ? 0.0 : totals.Average();
            }

            return list;
        }

        public async Task<Mantenimiento?> GetByIdAsync(int id)
        {
            var m = await _context.Mantenimientos
                .Include(x => x.Equipo)
                    .ThenInclude(e => e.TipoMaquinaria)
                .Include(x => x.TipoMantenimiento)
                .FirstOrDefaultAsync(x => x.Id == id);

            if (m == null) return null;

            m.NumeroSerie = m.Equipo?.NumeroSerie;
            m.HorasActuales = m.Equipo?.HorasActuales ?? 0;
            m.TipoMaquinariaInfo = m.Equipo?.TipoMaquinaria;
            m.MaquinaNombre = m.Equipo?.TipoMaquinaria?.Descripcion;

            var totals = await _context.ChecklistMantenimiento
                .Where(c => c.MantenimientoId == m.Id)
                .Select(c => c.Completado ? 1 : 0)
                .ToListAsync();
            m.ProgresoChecklist = totals.Count == 0 ? 0.0 : totals.Average();

            return m;
        }

        public async Task<Mantenimiento> CreateAsync(Mantenimiento mantenimiento)
        {
            _context.Mantenimientos.Add(mantenimiento);
            await _context.SaveChangesAsync();
            return mantenimiento;
        }

        public async Task<Mantenimiento?> UpdateAsync(int id, Mantenimiento mantenimiento)
        {
            var existing = await _context.Mantenimientos.FindAsync(id);
            if (existing == null) return null;

            existing.EquipoId = mantenimiento.EquipoId;
            existing.TipoMantenimientoId = mantenimiento.TipoMantenimientoId;
            existing.OperarioAsignadoId = mantenimiento.OperarioAsignadoId;
            existing.FechaInicio = mantenimiento.FechaInicio;
            existing.FechaFin = mantenimiento.FechaFin;
            existing.Estado = mantenimiento.Estado;

            await _context.SaveChangesAsync();
            return existing;
        }

        public async Task<bool> DeleteAsync(int id)
        {
            var mantenimiento = await _context.Mantenimientos.FindAsync(id);
            if (mantenimiento == null) return false;

            _context.Mantenimientos.Remove(mantenimiento);
            await _context.SaveChangesAsync();
            return true;
        }

        // Método solicitado: obtener mantenimientos por técnico (una sola consulta por mantenimiento para totals)
        public async Task<List<Mantenimiento>> GetMantenimientosByTecnicoAsync(int tecnicoId)
        {
            // Cargar mantenimientos del técnico con equipo y tipo maquinaria
            var items = await _context.Mantenimientos
                .Where(m => m.OperarioAsignadoId == tecnicoId)
                .Include(m => m.Equipo)
                    .ThenInclude(e => e.TipoMaquinaria)
                .Include(m => m.TipoMantenimiento)
                .AsNoTracking()
                .ToListAsync();

            if (!items.Any()) return new List<Mantenimiento>();

            // Obtener conteos (total y completados) para todos los mantenimientos de forma agregada
            var mantenimientoIds = items.Select(m => m.Id).ToList();

            var counts = await _context.ChecklistMantenimiento
                .Where(c => mantenimientoIds.Contains(c.MantenimientoId))
                .GroupBy(c => c.MantenimientoId)
                .Select(g => new
                {
                    MantenimientoId = g.Key,
                    Total = g.Count(),
                    Completed = g.Count(ci => ci.Completado)
                })
                .ToListAsync();

            var dict = counts.ToDictionary(x => x.MantenimientoId, x => (x.Total, x.Completed));

            // Asignar progreso y datos de equipo/tipo maquinaria
            foreach (var m in items)
            {
                m.NumeroSerie = m.Equipo?.NumeroSerie;
                m.HorasActuales = m.Equipo?.HorasActuales ?? 0;
                m.TipoMaquinariaInfo = m.Equipo?.TipoMaquinaria;
                m.MaquinaNombre = m.Equipo?.TipoMaquinaria?.Descripcion;

                if (dict.TryGetValue(m.Id, out var val) && val.Total > 0)
                {
                    m.ProgresoChecklist = (double)val.Completed / val.Total;
                }
                else
                {
                    m.ProgresoChecklist = 0.0;
                }
            }

            return items;
        }
    }
}