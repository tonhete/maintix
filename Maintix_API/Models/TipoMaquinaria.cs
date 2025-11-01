namespace Maintix_API.Models
{
    public class TipoMaquinaria
    {
        public int Id { get; set; }
        public string Descripcion { get; set; } = string.Empty;
        public int? MantenimientoA { get; set; }
        public int? MantenimientoB { get; set; }
        public int? MantenimientoC { get; set; }
        public string? Despiece { get; set; }
        public int? ProveedorId { get; set; }
        public string? Repuestos { get; set; }
    }
}
