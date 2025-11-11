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
            return await _context.Mantenimientos
                .Include(m => m.Equipo)
                    .ThenInclude(e => e.TipoMaquinaria)
                .Include(m => m.TipoMantenimiento)
                .ToListAsync();
        }

        public async Task<Mantenimiento?> GetByIdAsync(int id)
        {
            return await _context.Mantenimientos
                .Include(m => m.Equipo)
                    .ThenInclude(e => e.TipoMaquinaria)
                .Include(m => m.TipoMantenimiento)
                .FirstOrDefaultAsync(m => m.Id == id);
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
    }
}
