using Microsoft.EntityFrameworkCore;
using Maintix_API.Data;
using Maintix_API.Models;

namespace Maintix_API.Repositories
{
    public class TipoUsuarioRepository : ITipoUsuarioRepository
    {
        private readonly MaintixDbContext _context;

        public TipoUsuarioRepository(MaintixDbContext context)
        {
            _context = context;
        }

        public async Task<IEnumerable<TipoUsuario>> GetAllAsync()
        {
            return await _context.TipoUsuario.ToListAsync();
        }

        public async Task<TipoUsuario?> GetByIdAsync(int id)
        {
            return await _context.TipoUsuario.FindAsync(id);
        }

        public async Task<TipoUsuario> CreateAsync(TipoUsuario tipoUsuario)
        {
            _context.TipoUsuario.Add(tipoUsuario);
            await _context.SaveChangesAsync();
            return tipoUsuario;
        }

        public async Task<TipoUsuario?> UpdateAsync(int id, TipoUsuario tipoUsuario)
        {
            var existing = await _context.TipoUsuario.FindAsync(id);
            if (existing == null) return null;

            existing.Descripcion = tipoUsuario.Descripcion;

            await _context.SaveChangesAsync();
            return existing;
        }

        public async Task<bool> DeleteAsync(int id)
        {
            var tipoUsuario = await _context.TipoUsuario.FindAsync(id);
            if (tipoUsuario == null) return false;

            _context.TipoUsuario.Remove(tipoUsuario);
            await _context.SaveChangesAsync();
            return true;
        }
    }
}
