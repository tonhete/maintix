namespace Maintix_API.Models
{
    public class Mantenimiento
    {
        public int Id { get; set; }
        public int EquipoId { get; set; }
        public int TipoMantenimientoId { get; set; }
        public DateTime FechaInicio { get; set; } = DateTime.Now;
        public DateTime? FechaFin { get; set; }
        public string Estado { get; set; } = "pendiente";
    }
}
