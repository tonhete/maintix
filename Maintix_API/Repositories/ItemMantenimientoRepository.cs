using Microsoft.EntityFrameworkCore;
using Maintix_API.Data;
using Maintix_API.Models;

namespace Maintix_API.Repositories
{
    public class ItemMantenimientoRepository : IItemMantenimientoRepository
    {
        private readonly MaintixDbContext _context;

        public ItemMantenimientoRepository(MaintixDbContext context)
        {
            _context = context;
        }

        public async Task<IEnumerable<ItemMantenimiento>> GetAllAsync()
        {
            return await _context.ItemsMantenimiento.ToListAsync();
        }

        public async Task<ItemMantenimiento?> GetByIdAsync(int id)
        {
            return await _context.ItemsMantenimiento.FindAsync(id);
        }

        public async Task<ItemMantenimiento> CreateAsync(ItemMantenimiento itemMantenimiento)
        {
            _context.ItemsMantenimiento.Add(itemMantenimiento);
            await _context.SaveChangesAsync();
            return itemMantenimiento;
        }

        public async Task<ItemMantenimiento?> UpdateAsync(int id, ItemMantenimiento itemMantenimiento)
        {
            var existing = await _context.ItemsMantenimiento.FindAsync(id);
            if (existing == null) return null;

            existing.TipoMaquinaId = itemMantenimiento.TipoMaquinaId;
            existing.TipoMantenimientoId = itemMantenimiento.TipoMantenimientoId;
            existing.Descripcion = itemMantenimiento.Descripcion;
            existing.DescripcionDetallada = itemMantenimiento.DescripcionDetallada;
            existing.Herramientas = itemMantenimiento.Herramientas;
            existing.ImagenUrl = itemMantenimiento.ImagenUrl;
            existing.Orden = itemMantenimiento.Orden;

            await _context.SaveChangesAsync();
            return existing;
        }

        public async Task<bool> DeleteAsync(int id)
        {
            var itemMantenimiento = await _context.ItemsMantenimiento.FindAsync(id);
            if (itemMantenimiento == null) return false;

            _context.ItemsMantenimiento.Remove(itemMantenimiento);
            await _context.SaveChangesAsync();
            return true;
        }
    }
}
