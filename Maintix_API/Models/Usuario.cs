namespace Maintix_API.Models
{
    public class Usuario
    {
        public int Id { get; set; }
        public int TipoUsuarioId { get; set; }
        public string Email { get; set; } = string.Empty;
        public string Passwd { get; set; } = string.Empty;
    }
}
