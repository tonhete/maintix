using Maintix_API.Models;

namespace Maintix_API.Repositories
{
    public interface IHistoricoRepository
    {
        Task<IEnumerable<Historico>> GetAllAsync();
        Task<IEnumerable<Historico>> GetByEquipoIdAsync(int equipoId);
        Task<Historico?> GetByIdAsync(int id);
        Task<Historico> CreateAsync(Historico historico);
        Task<Historico?> UpdateAsync(int id, Historico historico);
        Task<bool> DeleteAsync(int id);
    }
}
