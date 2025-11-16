namespace Maintix_API.DTOs
{
    public class AlertaEquipoDto
    {
        public int EquipoId { get; set; }
        public string? NumeroSerie { get; set; }
        public string? DescripcionEquipo { get; set; }
        public int HorasActuales { get; set; }
        public List<string> MantenimientosPendientes { get; set; } = new();
        public string Razon { get; set; } = string.Empty;
    }
}