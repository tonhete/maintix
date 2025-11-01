using Microsoft.EntityFrameworkCore;
using Maintix_API.Data;
using Maintix_API.Models;

namespace Maintix_API.Repositories
{
    public class TipoMantenimientoRepository : ITipoMantenimientoRepository
    {
        private readonly MaintixDbContext _context;

        public TipoMantenimientoRepository(MaintixDbContext context)
        {
            _context = context;
        }

        public async Task<IEnumerable<TipoMantenimiento>> GetAllAsync()
        {
            return await _context.TiposMantenimiento.ToListAsync();
        }

        public async Task<TipoMantenimiento?> GetByIdAsync(int id)
        {
            return await _context.TiposMantenimiento.FindAsync(id);
        }

        public async Task<TipoMantenimiento> CreateAsync(TipoMantenimiento tipoMantenimiento)
        {
            _context.TiposMantenimiento.Add(tipoMantenimiento);
            await _context.SaveChangesAsync();
            return tipoMantenimiento;
        }

        public async Task<TipoMantenimiento?> UpdateAsync(int id, TipoMantenimiento tipoMantenimiento)
        {
            var existing = await _context.TiposMantenimiento.FindAsync(id);
            if (existing == null) return null;

            existing.Nombre = tipoMantenimiento.Nombre;
            existing.Descripcion = tipoMantenimiento.Descripcion;

            await _context.SaveChangesAsync();
            return existing;
        }

        public async Task<bool> DeleteAsync(int id)
        {
            var tipoMantenimiento = await _context.TiposMantenimiento.FindAsync(id);
            if (tipoMantenimiento == null) return false;

            _context.TiposMantenimiento.Remove(tipoMantenimiento);
            await _context.SaveChangesAsync();
            return true;
        }
    }
}
