using Maintix_API.Models;

namespace Maintix_API.Repositories
{
    public interface IChecklistMantenimientoRepository
    {
        Task<IEnumerable<ChecklistMantenimiento>> GetAllAsync();
        Task<ChecklistMantenimiento?> GetByIdAsync(int id);
        Task<ChecklistMantenimiento> CreateAsync(ChecklistMantenimiento checklistMantenimiento);
        Task<ChecklistMantenimiento?> UpdateAsync(int id, ChecklistMantenimiento checklistMantenimiento);
        Task<bool> DeleteAsync(int id);
    }
}
