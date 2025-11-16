namespace Maintix_API.DTOs
{
    public class LoginDto
    {
        public string Email { get; set; } = string.Empty;
        public string Password { get; set; } = string.Empty;
    }

    public class LoginResponseDto
    {
        // Response shape requested by the client
        public string Token { get; set; } = string.Empty;
        public int UsuarioId { get; set; }
        public int TipoUsuarioId { get; set; }
        public string Email { get; set; } = string.Empty;
    }
}