using System.ComponentModel.DataAnnotations.Schema;
namespace Maintix_API.Models
{
    public class Historico
    {
        public int Id { get; set; }
        public int EquipoId { get; set; }
        public int? HorasMaquina { get; set; }
        public string? Clase { get; set; }
        public string? Operario { get; set; }
        public string? Incidencias { get; set; }
        public bool Finalizado { get; set; } = false;

        // Fecha del mantenimiento (inicio) y fecha de finalización (no persistidos)
        [NotMapped]
        public DateTime? FechaMantenimiento { get; set; }
        [NotMapped]
        public DateTime? FechaFinalizacion { get; set; }

        // Información referencial que queremos exponer en la API (no persistidos)
        [NotMapped]
        public string? EquipoNumeroSerie { get; set; }
        [NotMapped]
        public string? TipoMantenimientoNombre { get; set; }
    }
}
