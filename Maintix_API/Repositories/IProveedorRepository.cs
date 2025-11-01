using Maintix_API.Models;

namespace Maintix_API.Repositories
{
    public interface IProveedorRepository
    {
        Task<IEnumerable<Proveedor>> GetAllAsync();
        Task<Proveedor?> GetByIdAsync(int id);
        Task<Proveedor> CreateAsync(Proveedor proveedor);
        Task<Proveedor?> UpdateAsync(int id, Proveedor proveedor);
        Task<bool> DeleteAsync(int id);
    }
}
