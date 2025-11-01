namespace Maintix_API.DTOs
{
    public class AlertaMantenimientoDto
    {
        public int EquipoId { get; set; }
        public string? NumeroSerie { get; set; }
        public string? DescripcionEquipo { get; set; }
        public int TipoMantenimientoPendienteId { get; set; }
        public List<string> MantenimientosPendientes { get; set; } = new();
        public int HorasActuales { get; set; }
    }
}
