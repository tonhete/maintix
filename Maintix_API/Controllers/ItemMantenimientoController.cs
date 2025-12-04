
using Microsoft.AspNetCore.Mvc;
using Maintix_API.Models;
using Maintix_API.Repositories;
using Microsoft.AspNetCore.Authorization;

namespace Maintix_API.Controllers
{
        [Authorize]
        [Route("api/[controller]")]
        [ApiController]
        public class ItemMantenimientoController : ControllerBase
    {
        private readonly IItemMantenimientoRepository _repository;

        public ItemMantenimientoController(IItemMantenimientoRepository repository)
        {
            _repository = repository;
        }

        [HttpGet]
        public async Task<ActionResult<IEnumerable<ItemMantenimiento>>> GetAll()
        {
            var items = await _repository.GetAllAsync();
            return Ok(items);
        }

        [HttpGet("{id}")]
        public async Task<ActionResult<ItemMantenimiento>> GetById(int id)
        {
            var item = await _repository.GetByIdAsync(id);
            if (item == null) return NotFound();
            return Ok(item);
        }

        [HttpPost]
        public async Task<ActionResult<ItemMantenimiento>> Create(ItemMantenimiento itemMantenimiento)
        {
            var created = await _repository.CreateAsync(itemMantenimiento);
            return CreatedAtAction(nameof(GetById), new { id = created.Id }, created);
        }

        [HttpPut("{id}")]
        public async Task<ActionResult<ItemMantenimiento>> Update(int id, ItemMantenimiento itemMantenimiento)
        {
            var updated = await _repository.UpdateAsync(id, itemMantenimiento);
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
