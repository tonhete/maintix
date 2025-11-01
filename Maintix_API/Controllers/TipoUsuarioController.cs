using Microsoft.AspNetCore.Mvc;
using Maintix_API.Models;
using Maintix_API.Repositories;

namespace Maintix_API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class TipoUsuarioController : ControllerBase
    {
        private readonly ITipoUsuarioRepository _repository;

        public TipoUsuarioController(ITipoUsuarioRepository repository)
        {
            _repository = repository;
        }

        [HttpGet]
        public async Task<ActionResult<IEnumerable<TipoUsuario>>> GetAll()
        {
            var items = await _repository.GetAllAsync();
            return Ok(items);
        }

        [HttpGet("{id}")]
        public async Task<ActionResult<TipoUsuario>> GetById(int id)
        {
            var item = await _repository.GetByIdAsync(id);
            if (item == null) return NotFound();
            return Ok(item);
        }

        [HttpPost]
        public async Task<ActionResult<TipoUsuario>> Create(TipoUsuario tipoUsuario)
        {
            var created = await _repository.CreateAsync(tipoUsuario);
            return CreatedAtAction(nameof(GetById), new { id = created.Id }, created);
        }

        [HttpPut("{id}")]
        public async Task<ActionResult<TipoUsuario>> Update(int id, TipoUsuario tipoUsuario)
        {
            var updated = await _repository.UpdateAsync(id, tipoUsuario);
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
