using Microsoft.EntityFrameworkCore;
using Maintix_API.Data;
using Maintix_API.Models;

namespace Maintix_API.Repositories
{
    public class ProveedorRepository : IProveedorRepository
    {
        private readonly MaintixDbContext _context;

        public ProveedorRepository(MaintixDbContext context)
        {
            _context = context;
        }

        public async Task<IEnumerable<Proveedor>> GetAllAsync()
        {
            return await _context.Proveedores.ToListAsync();
        }

        public async Task<Proveedor?> GetByIdAsync(int id)
        {
            return await _context.Proveedores.FindAsync(id);
        }

        public async Task<Proveedor> CreateAsync(Proveedor proveedor)
        {
            _context.Proveedores.Add(proveedor);
            await _context.SaveChangesAsync();
            return proveedor;
        }

        public async Task<Proveedor?> UpdateAsync(int id, Proveedor proveedor)
        {
            var existing = await _context.Proveedores.FindAsync(id);
            if (existing == null) return null;

            existing.Nombre = proveedor.Nombre;
            existing.Tlf = proveedor.Tlf;
            existing.Direccion = proveedor.Direccion;

            await _context.SaveChangesAsync();
            return existing;
        }

        public async Task<bool> DeleteAsync(int id)
        {
            var proveedor = await _context.Proveedores.FindAsync(id);
            if (proveedor == null) return false;

            _context.Proveedores.Remove(proveedor);
            await _context.SaveChangesAsync();
            return true;
        }
    }
}
