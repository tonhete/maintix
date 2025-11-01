using Maintix_API.Models;

namespace Maintix_API.Repositories
{
    public interface IEquipoRepository
    {
        Task<IEnumerable<Equipo>> GetAllAsync();
        Task<Equipo?> GetByIdAsync(int id);
        Task<Equipo> CreateAsync(Equipo equipo);
        Task<Equipo?> UpdateAsync(int id, Equipo equipo);
        Task<bool> DeleteAsync(int id);
    }
}
