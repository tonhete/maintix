using Microsoft.AspNetCore.Mvc;
using Maintix_API.DTOs;
using Maintix_API.Services;

namespace Maintix_API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class MantenimientoServiceController : ControllerBase
    {
        private readonly IMantenimientoService _service;

        public MantenimientoServiceController(IMantenimientoService service)
        {
            _service = service;
        }

        // POST: api/MantenimientoService/equipo/5/actualizar-horas
        [HttpPost("equipo/{equipoId}/actualizar-horas")]
        public async Task<ActionResult> ActualizarHorasEquipo(int equipoId, [FromBody] ActualizarHorasDto dto)
        {
            var result = await _service.ActualizarHorasEquipoAsync(equipoId, dto.HorasNuevas);
            if (!result) return NotFound("Equipo no encontrado o horas inválidas");
            return Ok(new { message = "Horas actualizadas correctamente" });
        }

        // GET: api/MantenimientoService/equipo/5/alertas
        [HttpGet("equipo/{equipoId}/alertas")]
        public async Task<ActionResult<AlertaMantenimientoDto>> VerificarAlertas(int equipoId)
        {
            var alertas = await _service.VerificarAlertasEquipoAsync(equipoId);
            if (alertas == null) return NotFound("Equipo no encontrado");
            return Ok(alertas);
        }

        // POST: api/MantenimientoService/crear-con-checklist
       [HttpPost("crear-con-checklist")]
        public async Task<ActionResult<MantenimientoConChecklistDto>> CrearMantenimientoConChecklist([FromBody] CrearMantenimientoDto dto)
        {
            var mantenimiento = await _service.CrearMantenimientoConChecklistAsync(dto);
            if (mantenimiento == null) return NotFound("Equipo o tipo de mantenimiento no encontrado");
            return Ok(mantenimiento);  // ← Cambia esto
        }

        // GET: api/MantenimientoService/5/checklist
        [HttpGet("{mantenimientoId}/checklist")]
        public async Task<ActionResult<MantenimientoConChecklistDto>> ObtenerMantenimientoConChecklist(int mantenimientoId)
        {
            var mantenimiento = await _service.ObtenerMantenimientoConChecklistAsync(mantenimientoId);
            if (mantenimiento == null) return NotFound("Mantenimiento no encontrado");
            return Ok(mantenimiento);
        }

        // PUT: api/MantenimientoService/5/actualizar-checklist
        [HttpPut("{mantenimientoId}/actualizar-checklist")]
        public async Task<ActionResult> ActualizarChecklist(int mantenimientoId, [FromBody] ActualizarChecklistDto dto)
        {
            var result = await _service.ActualizarChecklistAsync(mantenimientoId, dto);
            if (!result) return NotFound("Mantenimiento no encontrado");
            return Ok(new { message = "Checklist actualizado correctamente" });
        }

        // POST: api/MantenimientoService/5/finalizar
        [HttpPost("{mantenimientoId}/finalizar")]
        public async Task<ActionResult> FinalizarMantenimiento(int mantenimientoId, [FromBody] FinalizarMantenimientoDto dto)
        {
            var result = await _service.FinalizarMantenimientoAsync(mantenimientoId, dto);
            if (!result) return NotFound("Mantenimiento no encontrado");
            return Ok(new { message = "Mantenimiento finalizado correctamente" });
        }
    }
}
