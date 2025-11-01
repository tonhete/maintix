using Microsoft.EntityFrameworkCore;
using Maintix_API.Data;
using Maintix_API.Models;

namespace Maintix_API.Repositories
{
    public class TipoMaquinaRepository : ITipoMaquinaRepository
    {
        private readonly MaintixDbContext _context;

        public TipoMaquinaRepository(MaintixDbContext context)
        {
            _context = context;
        }

        public async Task<IEnumerable<TipoMaquina>> GetAllAsync()
        {
            return await _context.TiposMaquina.ToListAsync();
        }

        public async Task<TipoMaquina?> GetByIdAsync(int id)
        {
            return await _context.TiposMaquina.FindAsync(id);
        }

        public async Task<TipoMaquina> CreateAsync(TipoMaquina tipoMaquina)
        {
            _context.TiposMaquina.Add(tipoMaquina);
            await _context.SaveChangesAsync();
            return tipoMaquina;
        }

        public async Task<TipoMaquina?> UpdateAsync(int id, TipoMaquina tipoMaquina)
        {
            var existing = await _context.TiposMaquina.FindAsync(id);
            if (existing == null) return null;

            existing.Nombre = tipoMaquina.Nombre;

            await _context.SaveChangesAsync();
            return existing;
        }

        public async Task<bool> DeleteAsync(int id)
        {
            var tipoMaquina = await _context.TiposMaquina.FindAsync(id);
            if (tipoMaquina == null) return false;

            _context.TiposMaquina.Remove(tipoMaquina);
            await _context.SaveChangesAsync();
            return true;
        }
    }
}
