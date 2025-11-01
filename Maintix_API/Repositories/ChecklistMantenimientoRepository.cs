using Microsoft.EntityFrameworkCore;
using Maintix_API.Data;
using Maintix_API.Models;

namespace Maintix_API.Repositories
{
    public class ChecklistMantenimientoRepository : IChecklistMantenimientoRepository
    {
        private readonly MaintixDbContext _context;

        public ChecklistMantenimientoRepository(MaintixDbContext context)
        {
            _context = context;
        }

        public async Task<IEnumerable<ChecklistMantenimiento>> GetAllAsync()
        {
            return await _context.ChecklistMantenimiento.ToListAsync();
        }

        public async Task<ChecklistMantenimiento?> GetByIdAsync(int id)
        {
            return await _context.ChecklistMantenimiento.FindAsync(id);
        }

        public async Task<ChecklistMantenimiento> CreateAsync(ChecklistMantenimiento checklistMantenimiento)
        {
            _context.ChecklistMantenimiento.Add(checklistMantenimiento);
            await _context.SaveChangesAsync();
            return checklistMantenimiento;
        }

        public async Task<ChecklistMantenimiento?> UpdateAsync(int id, ChecklistMantenimiento checklistMantenimiento)
        {
            var existing = await _context.ChecklistMantenimiento.FindAsync(id);
            if (existing == null) return null;

            existing.MantenimientoId = checklistMantenimiento.MantenimientoId;
            existing.ItemId = checklistMantenimiento.ItemId;
            existing.Completado = checklistMantenimiento.Completado;
            existing.Observaciones = checklistMantenimiento.Observaciones;

            await _context.SaveChangesAsync();
            return existing;
        }

        public async Task<bool> DeleteAsync(int id)
        {
            var checklistMantenimiento = await _context.ChecklistMantenimiento.FindAsync(id);
            if (checklistMantenimiento == null) return false;

            _context.ChecklistMantenimiento.Remove(checklistMantenimiento);
            await _context.SaveChangesAsync();
            return true;
        }
    }
}
