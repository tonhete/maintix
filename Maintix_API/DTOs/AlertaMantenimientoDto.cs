namespace Maintix_API.DTOs
{
    public class AlertaMantenimientoDto
    {
        public int EquipoId { get; set; }
        public string? NumeroSerie { get; set; }
        public string? DescripcionEquipo { get; set; }
        public List<MantenimientoPendienteInfo> MantenimientosPendientes { get; set; } = new();
        public int HorasActuales { get; set; }
    }
    public class MantenimientoPendienteInfo
    {
        public int Id { get; set; }
        public string Nombre { get; set; } = string.Empty;
    }
}
