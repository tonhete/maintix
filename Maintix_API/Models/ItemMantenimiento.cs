namespace Maintix_API.Models
{
    public class ItemMantenimiento
    {
        public int Id { get; set; }
        public int TipoMaquinaId { get; set; }
        public int TipoMantenimientoId { get; set; }
        public string Descripcion { get; set; } = string.Empty;
        public int Orden { get; set; } = 0;
    }
}
