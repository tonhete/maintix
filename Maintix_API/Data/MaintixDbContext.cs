using Microsoft.EntityFrameworkCore;
using Maintix_API.Models;

namespace Maintix_API.Data
{
    public class MaintixDbContext : DbContext
    {
        public MaintixDbContext(DbContextOptions<MaintixDbContext> options) : base(options)
        {
        }

        public DbSet<TipoUsuario> TipoUsuario { get; set; }
        public DbSet<Usuario> Usuarios { get; set; }
        public DbSet<Proveedor> Proveedores { get; set; }
        public DbSet<TipoMaquinaria> TipoMaquinaria { get; set; }
        public DbSet<TipoMaquina> TiposMaquina { get; set; }
        public DbSet<Equipo> Equipos { get; set; }
        public DbSet<TipoMantenimiento> TiposMantenimiento { get; set; }
        public DbSet<Mantenimiento> Mantenimientos { get; set; }
        public DbSet<ItemMantenimiento> ItemsMantenimiento { get; set; }
        public DbSet<ChecklistMantenimiento> ChecklistMantenimiento { get; set; }
        public DbSet<Historico> Historico { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);

            // Configurar nombres de tablas para que coincidan con SQL Server
            modelBuilder.Entity<TipoUsuario>().ToTable("tipo_usuario");
            modelBuilder.Entity<Usuario>().ToTable("usuarios");
            modelBuilder.Entity<Proveedor>().ToTable("proveedores");
            modelBuilder.Entity<TipoMaquinaria>().ToTable("tipo_maquinaria");
            modelBuilder.Entity<TipoMaquina>().ToTable("tipos_maquina");
            modelBuilder.Entity<Equipo>().ToTable("equipo");
            modelBuilder.Entity<TipoMantenimiento>().ToTable("tipos_mantenimiento");
            modelBuilder.Entity<Mantenimiento>().ToTable("mantenimientos");
            modelBuilder.Entity<ItemMantenimiento>().ToTable("items_mantenimiento");
            modelBuilder.Entity<ChecklistMantenimiento>().ToTable("checklist_mantenimiento");
            modelBuilder.Entity<Historico>().ToTable("historico");

            // Configurar columnas para que coincidan con SQL Server
            modelBuilder.Entity<TipoUsuario>(entity =>
            {
                entity.Property(e => e.Id).HasColumnName("id");
                entity.Property(e => e.Descripcion).HasColumnName("descripcion");
            });

            modelBuilder.Entity<Usuario>(entity =>
            {
                entity.Property(e => e.Id).HasColumnName("id");
                entity.Property(e => e.TipoUsuarioId).HasColumnName("tipo_usuario_id");
                entity.Property(e => e.Email).HasColumnName("email");
                entity.Property(e => e.Passwd).HasColumnName("passwd");
            });

            modelBuilder.Entity<Proveedor>(entity =>
            {
                entity.Property(e => e.Id).HasColumnName("id");
                entity.Property(e => e.Nombre).HasColumnName("nombre");
                entity.Property(e => e.Tlf).HasColumnName("tlf");
                entity.Property(e => e.Direccion).HasColumnName("direccion");
            });

            modelBuilder.Entity<TipoMaquinaria>(entity =>
            {
                entity.Property(e => e.Id).HasColumnName("id");
                entity.Property(e => e.Descripcion).HasColumnName("descripcion");
                entity.Property(e => e.MantenimientoA).HasColumnName("mantenimientoA");
                entity.Property(e => e.MantenimientoB).HasColumnName("mantenimientoB");
                entity.Property(e => e.MantenimientoC).HasColumnName("mantenimientoC");
                entity.Property(e => e.Despiece).HasColumnName("despiece");
                entity.Property(e => e.ProveedorId).HasColumnName("proveedor_id");
                entity.Property(e => e.Repuestos).HasColumnName("repuestos");
            });

            modelBuilder.Entity<TipoMaquina>(entity =>
            {
                entity.Property(e => e.Id).HasColumnName("id");
                entity.Property(e => e.Nombre).HasColumnName("nombre");
            });

            modelBuilder.Entity<Equipo>(entity =>
            {
                entity.Property(e => e.Id).HasColumnName("id");
                entity.Property(e => e.TipoMaquinariaId).HasColumnName("tipo_maquinaria_id");
                entity.Property(e => e.FechaFabricacion).HasColumnName("fechaFabricacion");
                entity.Property(e => e.NumeroSerie).HasColumnName("numeroSerie");
                entity.Property(e => e.HorasActuales).HasColumnName("horasActuales");
                entity.Property(e => e.ContadorTipoA).HasColumnName("contadorTipoA");
                entity.Property(e => e.ContadorTipoB).HasColumnName("contadorTipoB");
                entity.Property(e => e.ContadorTipoC).HasColumnName("contadorTipoC");
            });

            modelBuilder.Entity<TipoMantenimiento>(entity =>
            {
                entity.Property(e => e.Id).HasColumnName("id");
                entity.Property(e => e.Nombre).HasColumnName("nombre");
                entity.Property(e => e.Descripcion).HasColumnName("descripcion");
            });

            modelBuilder.Entity<Mantenimiento>(entity =>
            {
                entity.Property(e => e.Id).HasColumnName("id");
                entity.Property(e => e.EquipoId).HasColumnName("equipo_id");
                entity.Property(e => e.TipoMantenimientoId).HasColumnName("tipo_mantenimiento_id");
                entity.Property(e => e.FechaInicio).HasColumnName("fecha_inicio");
                entity.Property(e => e.FechaFin).HasColumnName("fecha_fin");
                entity.Property(e => e.Estado).HasColumnName("estado");
            });

            modelBuilder.Entity<ItemMantenimiento>(entity =>
            {
                entity.Property(e => e.Id).HasColumnName("id");
                entity.Property(e => e.TipoMaquinaId).HasColumnName("tipo_maquina_id");
                entity.Property(e => e.TipoMantenimientoId).HasColumnName("tipo_mantenimiento_id");
                entity.Property(e => e.Descripcion).HasColumnName("descripcion");
                entity.Property(e => e.Orden).HasColumnName("orden");
            });

            modelBuilder.Entity<ChecklistMantenimiento>(entity =>
            {
                entity.Property(e => e.Id).HasColumnName("id");
                entity.Property(e => e.MantenimientoId).HasColumnName("mantenimiento_id");
                entity.Property(e => e.ItemId).HasColumnName("item_id");
                entity.Property(e => e.Completado).HasColumnName("completado");
                entity.Property(e => e.Observaciones).HasColumnName("observaciones");
            });

            modelBuilder.Entity<Historico>(entity =>
            {
                entity.Property(e => e.Id).HasColumnName("id");
                entity.Property(e => e.EquipoId).HasColumnName("equipo_id");
                entity.Property(e => e.HorasMaquina).HasColumnName("horasMaquina");
                entity.Property(e => e.Clase).HasColumnName("clase");
                entity.Property(e => e.Operario).HasColumnName("operario");
                entity.Property(e => e.Incidencias).HasColumnName("incidencias");
                entity.Property(e => e.Finalizado).HasColumnName("finalizado");
            });
        }
    }
}