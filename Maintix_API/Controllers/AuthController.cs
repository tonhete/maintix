using Microsoft.AspNetCore.Mvc;
using Maintix_API.Services;

namespace Maintix_API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class AuthController : ControllerBase
    {
        private readonly IAuthService _authService;

        public AuthController(IAuthService authService)
        {
            _authService = authService;
        }

        [HttpPost("login")]
        public async Task<IActionResult> Login([FromBody] LoginRequest request)
        {
            var response = await _authService.LoginAsync(request.Email, request.Password);

            if (response == null)
                return Unauthorized(new { message = "Credenciales inválidas" });

            // Devolvemos token y datos del usuario para que el cliente (Android) los use directamente
            return Ok(new
            {
                token = response.Token,
                usuarioId = response.UsuarioId,
                tipoUsuarioId = response.TipoUsuarioId,
                email = response.Email
            });
        }
    }

    public class LoginRequest
    {
        public string Email { get; set; } = string.Empty;
        public string Password { get; set; } = string.Empty;
    }
}
