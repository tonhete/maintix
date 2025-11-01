using Maintix_API.Models;

namespace Maintix_API.Repositories
{
    public interface IItemMantenimientoRepository
    {
        Task<IEnumerable<ItemMantenimiento>> GetAllAsync();
        Task<ItemMantenimiento?> GetByIdAsync(int id);
        Task<ItemMantenimiento> CreateAsync(ItemMantenimiento itemMantenimiento);
        Task<ItemMantenimiento?> UpdateAsync(int id, ItemMantenimiento itemMantenimiento);
        Task<bool> DeleteAsync(int id);
    }
}
