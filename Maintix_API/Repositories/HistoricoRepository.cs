using Microsoft.EntityFrameworkCore;
using Maintix_API.Data;
using Maintix_API.Models;

namespace Maintix_API.Repositories
{
    public class HistoricoRepository : IHistoricoRepository
    {
        private readonly MaintixDbContext _context;

        public HistoricoRepository(MaintixDbContext context)
        {
            _context = context;
        }

        public async Task<IEnumerable<Historico>> GetAllAsync()
        {
            return await _context.Historico.ToListAsync();
        }

        public async Task<Historico?> GetByIdAsync(int id)
        {
            return await _context.Historico.FindAsync(id);
        }

        public async Task<Historico> CreateAsync(Historico historico)
        {
            _context.Historico.Add(historico);
            await _context.SaveChangesAsync();
            return historico;
        }

        public async Task<Historico?> UpdateAsync(int id, Historico historico)
        {
            var existing = await _context.Historico.FindAsync(id);
            if (existing == null) return null;

            existing.EquipoId = historico.EquipoId;
            existing.HorasMaquina = historico.HorasMaquina;
            existing.Clase = historico.Clase;
            existing.Operario = historico.Operario;
            existing.Incidencias = historico.Incidencias;
            existing.Finalizado = historico.Finalizado;

            await _context.SaveChangesAsync();
            return existing;
        }

        public async Task<bool> DeleteAsync(int id)
        {
            var historico = await _context.Historico.FindAsync(id);
            if (historico == null) return false;

            _context.Historico.Remove(historico);
            await _context.SaveChangesAsync();
            return true;
        }
    }
}
