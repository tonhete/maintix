using Maintix_API.Models;

namespace Maintix_API.Repositories
{
    public interface ITipoMantenimientoRepository
    {
        Task<IEnumerable<TipoMantenimiento>> GetAllAsync();
        Task<TipoMantenimiento?> GetByIdAsync(int id);
        Task<TipoMantenimiento> CreateAsync(TipoMantenimiento tipoMantenimiento);
        Task<TipoMantenimiento?> UpdateAsync(int id, TipoMantenimiento tipoMantenimiento);
        Task<bool> DeleteAsync(int id);
    }
}
