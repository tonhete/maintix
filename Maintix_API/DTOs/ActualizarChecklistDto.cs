namespace Maintix_API.DTOs
{
    public class ActualizarChecklistDto
    {
        public List<ActualizarItemChecklistDto> Items { get; set; } = new();
    }

    public class ActualizarItemChecklistDto
    {
        public int ChecklistId { get; set; }
        public bool Completado { get; set; }
        public string? Observaciones { get; set; }
    }
}
