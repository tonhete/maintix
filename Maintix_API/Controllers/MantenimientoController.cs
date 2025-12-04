
using Microsoft.AspNetCore.Mvc;
using Maintix_API.Models;
using Maintix_API.Repositories;
using Microsoft.AspNetCore.Authorization;

namespace Maintix_API.Controllers
{
        [Authorize]
        [Route("api/[controller]")]
        [ApiController]
        public class MantenimientoController : ControllerBase
    {
        private readonly IMantenimientoRepository _repository;

        public MantenimientoController(IMantenimientoRepository repository)
        {
            _repository = repository;
        }

        [HttpGet]
        public async Task<ActionResult<IEnumerable<Mantenimiento>>> GetAll()
        {
            var items = await _repository.GetAllAsync();
            return Ok(items);
        }

        [HttpGet("{id}")]
        public async Task<ActionResult<Mantenimiento>> GetById(int id)
        {
            var item = await _repository.GetByIdAsync(id);
            if (item == null) return NotFound();
            return Ok(item);
        }

        [HttpPost]
        public async Task<ActionResult<Mantenimiento>> Create(Mantenimiento mantenimiento)
        {
            var created = await _repository.CreateAsync(mantenimiento);
            return CreatedAtAction(nameof(GetById), new { id = created.Id }, created);
        }

        [HttpPut("{id}")]
        public async Task<ActionResult<Mantenimiento>> Update(int id, Mantenimiento mantenimiento)
        {
            var updated = await _repository.UpdateAsync(id, mantenimiento);
            if (updated == null) return NotFound();
            return Ok(updated);
        }

        [HttpDelete("{id}")]
        public async Task<ActionResult> Delete(int id)
        {
            var result = await _repository.DeleteAsync(id);
            if (!result) return NotFound();
            return NoContent();
        }
    
    [HttpGet("tecnico/{tecnicoId}")]
        public async Task<ActionResult<IEnumerable<Mantenimiento>>> GetByTecnico(int tecnicoId)
        {
            var mantenimientos = await _repository.GetMantenimientosByTecnicoAsync(tecnicoId);
            if (mantenimientos == null || !mantenimientos.Any())
                return NotFound();
            return Ok(mantenimientos);
        }
    }
}
