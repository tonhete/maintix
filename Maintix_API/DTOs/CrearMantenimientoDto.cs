namespace Maintix_API.DTOs
{
    public class CrearMantenimientoDto
    {
        public int EquipoId { get; set; }
        public int TipoMantenimientoId { get; set; }
    }

    // ← Aquí dentro del mismo archivo
    public class CrearMantenimientoMasivoDto
    {
        public List<CrearMantenimientoDto> Mantenimientos { get; set; } = new();
    }

    public class AsignarOperarioDto
    {
        public int OperarioId { get; set; }
    }
}