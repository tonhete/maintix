using Maintix_API.Models;

namespace Maintix_API.Repositories
{
    public interface ITipoMaquinariaRepository
    {
        Task<IEnumerable<TipoMaquinaria>> GetAllAsync();
        Task<TipoMaquinaria?> GetByIdAsync(int id);
        Task<TipoMaquinaria> CreateAsync(TipoMaquinaria tipoMaquinaria);
        Task<TipoMaquinaria?> UpdateAsync(int id, TipoMaquinaria tipoMaquinaria);
        Task<bool> DeleteAsync(int id);
    }
}
