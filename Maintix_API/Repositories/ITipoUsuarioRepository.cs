using Maintix_API.Models;

namespace Maintix_API.Repositories
{
    public interface ITipoUsuarioRepository
    {
        Task<IEnumerable<TipoUsuario>> GetAllAsync();
        Task<TipoUsuario?> GetByIdAsync(int id);
        Task<TipoUsuario> CreateAsync(TipoUsuario tipoUsuario);
        Task<TipoUsuario?> UpdateAsync(int id, TipoUsuario tipoUsuario);
        Task<bool> DeleteAsync(int id);
    }
}
