using System;

namespace Maintix_API.DTOs
{
    public class MantenimientoConChecklistDto
    {
        public int MantenimientoId { get; set; }
        public int EquipoId { get; set; }
        public string? NumeroSerie { get; set; }
        public string? TipoMantenimiento { get; set; }
        public DateTime FechaInicio { get; set; }
        public string Estado { get; set; } = string.Empty;
        public List<ChecklistItemDto> Items { get; set; } = new List<ChecklistItemDto>();
    }
}