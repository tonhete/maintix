using Microsoft.AspNetCore.Mvc;
using Maintix_API.Data;

namespace Maintix_API.Controllers
{
    [ApiController]
    [Route("api/health")]
    public class HealthController : ControllerBase
    {
        private readonly MaintixDbContext _context;
        private readonly ILogger<HealthController> _logger;

        public HealthController(MaintixDbContext context, ILogger<HealthController> logger)
        {
            _context = context;
            _logger = logger;
        }

        [HttpGet("database")]
        public async Task<IActionResult> CheckDatabaseConnection()
        {
            try
            {
                // Intentar abrir una conexión a la base de datos
                await _context.Database.CanConnectAsync();

                return Ok(new
                {
                    status = "connected"
                });
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, "Error al conectar con la base de datos");

                return StatusCode(500, new
                {
                    status = "error",
                    message = ex.Message
                });
            }
        }
    }
}