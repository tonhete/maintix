using Microsoft.AspNetCore.Mvc;
using Maintix_API.Models;
using Maintix_API.Repositories;
using System.Linq;

namespace Maintix_API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class HistoricoController : ControllerBase
    {
        private readonly IHistoricoRepository _repository;

        public HistoricoController(IHistoricoRepository repository)
        {
            _repository = repository;
        }

        [HttpGet]
        public async Task<ActionResult<IEnumerable<Historico>>> GetAll()
        {
            var items = await _repository.GetAllAsync();
            return Ok(items);
        }

        [HttpGet("equipo/{equipoId}")]
        public async Task<ActionResult<IEnumerable<Historico>>> GetByEquipo(int equipoId)
        {
            var items = await _repository.GetByEquipoIdAsync(equipoId);
            if (items == null || !items.Any()) return NotFound();
            return Ok(items);
        }

        [HttpGet("{id}")]
        public async Task<ActionResult<Historico>> GetById(int id)
        {
            var item = await _repository.GetByIdAsync(id);
            if (item == null) return NotFound();
            return Ok(item);
        }

        [HttpPost]
        public async Task<ActionResult<Historico>> Create(Historico historico)
        {
            var created = await _repository.CreateAsync(historico);
            return CreatedAtAction(nameof(GetById), new { id = created.Id }, created);
        }

        [HttpPut("{id}")]
        public async Task<ActionResult<Historico>> Update(int id, Historico historico)
        {
            var updated = await _repository.UpdateAsync(id, historico);
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
    }
}
