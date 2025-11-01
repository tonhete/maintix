using Maintix_API.Models;

namespace Maintix_API.Repositories
{
    public interface ITipoMaquinaRepository
    {
        Task<IEnumerable<TipoMaquina>> GetAllAsync();
        Task<TipoMaquina?> GetByIdAsync(int id);
        Task<TipoMaquina> CreateAsync(TipoMaquina tipoMaquina);
        Task<TipoMaquina?> UpdateAsync(int id, TipoMaquina tipoMaquina);
        Task<bool> DeleteAsync(int id);
    }
}
