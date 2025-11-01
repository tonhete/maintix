namespace Maintix_API.Models
{
    public class Equipo
    {
        public int Id { get; set; }
        public int TipoMaquinariaId { get; set; }
        public DateTime? FechaFabricacion { get; set; }
        public string? NumeroSerie { get; set; }
        public int HorasActuales { get; set; } = 0;
        public int ContadorTipoA { get; set; } = 0;
        public int ContadorTipoB { get; set; } = 0;
        public int ContadorTipoC { get; set; } = 0;
    }
}
