using Microsoft.EntityFrameworkCore;
using Maintix_API.Data;
using Maintix_API.Models;

namespace Maintix_API.Repositories
{
    public class EquipoRepository : IEquipoRepository
    {
        private readonly MaintixDbContext _context;

        public EquipoRepository(MaintixDbContext context)
        {
            _context = context;
        }

        public async Task<IEnumerable<Equipo>> GetAllAsync()
        {
            return await _context.Equipos.ToListAsync();
        }

        public async Task<Equipo?> GetByIdAsync(int id)
        {
            return await _context.Equipos.FindAsync(id);
        }

        public async Task<Equipo> CreateAsync(Equipo equipo)
        {
            _context.Equipos.Add(equipo);
            await _context.SaveChangesAsync();
            return equipo;
        }

        public async Task<Equipo?> UpdateAsync(int id, Equipo equipo)
        {
            var existing = await _context.Equipos.FindAsync(id);
            if (existing == null) return null;

            existing.TipoMaquinariaId = equipo.TipoMaquinariaId;
            existing.FechaFabricacion = equipo.FechaFabricacion;
            existing.NumeroSerie = equipo.NumeroSerie;
            existing.HorasActuales = equipo.HorasActuales;
            existing.ContadorTipoA = equipo.ContadorTipoA;
            existing.ContadorTipoB = equipo.ContadorTipoB;
            existing.ContadorTipoC = equipo.ContadorTipoC;

            await _context.SaveChangesAsync();
            return existing;
        }

        public async Task<bool> DeleteAsync(int id)
        {
            var equipo = await _context.Equipos.FindAsync(id);
            if (equipo == null) return false;

            _context.Equipos.Remove(equipo);
            await _context.SaveChangesAsync();
            return true;
        }
    }
}
