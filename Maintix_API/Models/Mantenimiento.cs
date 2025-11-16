using System.ComponentModel.DataAnnotations.Schema;

namespace Maintix_API.Models
{
    public class Mantenimiento
    {
        public int Id { get; set; }
        public int EquipoId { get; set; }
        public int TipoMantenimientoId { get; set; }
        public int? OperarioAsignadoId { get; set; }
        public DateTime FechaInicio { get; set; } = DateTime.Now;
        public DateTime? FechaFin { get; set; }
        public string Estado { get; set; } = "pendiente";

        // Navegación
        public Equipo? Equipo { get; set; }
        public TipoMantenimiento? TipoMantenimiento { get; set; }
        public Usuario? OperarioAsignado { get; set; }

        [NotMapped]
        public double ProgresoChecklist { get; set; } = 0.0;


    }
}