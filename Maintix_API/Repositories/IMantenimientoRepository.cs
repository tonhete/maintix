using Maintix_API.Models;

namespace Maintix_API.Repositories
{
    public interface IMantenimientoRepository
    {
        Task<IEnumerable<Mantenimiento>> GetAllAsync();
        Task<Mantenimiento?> GetByIdAsync(int id);
        Task<Mantenimiento> CreateAsync(Mantenimiento mantenimiento);
        Task<Mantenimiento?> UpdateAsync(int id, Mantenimiento mantenimiento);
        Task<bool> DeleteAsync(int id);
    }
}
