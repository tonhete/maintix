using Microsoft.EntityFrameworkCore;
using Maintix_API.Data;
using Maintix_API.Models;

namespace Maintix_API.Repositories
{
    public class TipoMaquinariaRepository : ITipoMaquinariaRepository
    {
        private readonly MaintixDbContext _context;

        public TipoMaquinariaRepository(MaintixDbContext context)
        {
            _context = context;
        }

        public async Task<IEnumerable<TipoMaquinaria>> GetAllAsync()
        {
            return await _context.TipoMaquinaria.ToListAsync();
        }

        public async Task<TipoMaquinaria?> GetByIdAsync(int id)
        {
            return await _context.TipoMaquinaria.FindAsync(id);
        }

        public async Task<TipoMaquinaria> CreateAsync(TipoMaquinaria tipoMaquinaria)
        {
            _context.TipoMaquinaria.Add(tipoMaquinaria);
            await _context.SaveChangesAsync();
            return tipoMaquinaria;
        }

        public async Task<TipoMaquinaria?> UpdateAsync(int id, TipoMaquinaria tipoMaquinaria)
        {
            var existing = await _context.TipoMaquinaria.FindAsync(id);
            if (existing == null) return null;

            existing.Descripcion = tipoMaquinaria.Descripcion;
            existing.MantenimientoA = tipoMaquinaria.MantenimientoA;
            existing.MantenimientoB = tipoMaquinaria.MantenimientoB;
            existing.MantenimientoC = tipoMaquinaria.MantenimientoC;
            existing.Despiece = tipoMaquinaria.Despiece;
            existing.ProveedorId = tipoMaquinaria.ProveedorId;
            existing.Repuestos = tipoMaquinaria.Repuestos;

            await _context.SaveChangesAsync();
            return existing;
        }

        public async Task<bool> DeleteAsync(int id)
        {
            var tipoMaquinaria = await _context.TipoMaquinaria.FindAsync(id);
            if (tipoMaquinaria == null) return false;

            _context.TipoMaquinaria.Remove(tipoMaquinaria);
            await _context.SaveChangesAsync();
            return true;
        }
    }
}
