using Microsoft.EntityFrameworkCore;

namespace Maintix_API.Data
{
    public class MaintixDbContext : DbContext
    {
        public MaintixDbContext(DbContextOptions<MaintixDbContext> options)
            : base(options)
        {
        }

        // Los DbSet para las entidades se agregarán aquí
        // Ejemplo: public DbSet<Entity> Entities { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);

            // Configuraciones adicionales del modelo se agregarán aquí
        }
    }
}
