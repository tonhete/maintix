namespace Maintix_API.DTOs
{
    public class ChecklistItemDto
    {
        public int ChecklistId { get; set; }
        public int ItemId { get; set; }
        public string Descripcion { get; set; } = string.Empty;
        public int Orden { get; set; }
        public bool Completado { get; set; }
        public string? Observaciones { get; set; }
    }
}