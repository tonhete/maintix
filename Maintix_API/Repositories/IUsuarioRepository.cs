using Maintix_API.Models;

namespace Maintix_API.Repositories
{
    public interface IUsuarioRepository
    {
        Task<IEnumerable<Usuario>> GetAllAsync();
        Task<Usuario?> GetByIdAsync(int id);
        Task<Usuario> CreateAsync(Usuario usuario);
        Task<Usuario?> UpdateAsync(int id, Usuario usuario);
        Task<bool> DeleteAsync(int id);
    }
}
