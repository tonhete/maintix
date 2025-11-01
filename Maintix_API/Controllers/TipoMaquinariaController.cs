using Microsoft.AspNetCore.Mvc;
using Maintix_API.Models;
using Maintix_API.Repositories;

namespace Maintix_API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class TipoMaquinariaController : ControllerBase
    {
        private readonly ITipoMaquinariaRepository _repository;

        public TipoMaquinariaController(ITipoMaquinariaRepository repository)
        {
            _repository = repository;
        }

        [HttpGet]
        public async Task<ActionResult<IEnumerable<TipoMaquinaria>>> GetAll()
        {
            var items = await _repository.GetAllAsync();
            return Ok(items);
        }

        [HttpGet("{id}")]
        public async Task<ActionResult<TipoMaquinaria>> GetById(int id)
        {
            var item = await _repository.GetByIdAsync(id);
            if (item == null) return NotFound();
            return Ok(item);
        }

        [HttpPost]
        public async Task<ActionResult<TipoMaquinaria>> Create(TipoMaquinaria tipoMaquinaria)
        {
            var created = await _repository.CreateAsync(tipoMaquinaria);
            return CreatedAtAction(nameof(GetById), new { id = created.Id }, created);
        }

        [HttpPut("{id}")]
        public async Task<ActionResult<TipoMaquinaria>> Update(int id, TipoMaquinaria tipoMaquinaria)
        {
            var updated = await _repository.UpdateAsync(id, tipoMaquinaria);
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
