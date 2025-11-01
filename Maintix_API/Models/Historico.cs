namespace Maintix_API.Models
{
    public class Historico
    {
        public int Id { get; set; }
        public int EquipoId { get; set; }
        public int? HorasMaquina { get; set; }
        public string? Clase { get; set; }
        public string? Operario { get; set; }
        public string? Incidencias { get; set; }
        public bool Finalizado { get; set; } = false;
    }
}
