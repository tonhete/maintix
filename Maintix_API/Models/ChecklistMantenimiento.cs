namespace Maintix_API.Models
{
    public class ChecklistMantenimiento
    {
        public int Id { get; set; }
        public int MantenimientoId { get; set; }
        public int ItemId { get; set; }
        public bool Completado { get; set; } = false;
        public string? Observaciones { get; set; }
    }
}
