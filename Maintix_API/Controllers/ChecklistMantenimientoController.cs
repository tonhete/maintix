using Microsoft.AspNetCore.Mvc;
using Maintix_API.Models;
using Maintix_API.Repositories;
using Microsoft.AspNetCore.Authorization;

namespace Maintix_API.Controllers
{   
        [Authorize]
        [Route("api/[controller]")]
        [ApiController]
        public class ChecklistMantenimientoController : ControllerBase
    {
        private readonly IChecklistMantenimientoRepository _repository;

        public ChecklistMantenimientoController(IChecklistMantenimientoRepository repository)
        {
            _repository = repository;
        }

        [HttpGet]
        public async Task<ActionResult<IEnumerable<ChecklistMantenimiento>>> GetAll()
        {
            var items = await _repository.GetAllAsync();
            return Ok(items);
        }

        [HttpGet("{id}")]
        public async Task<ActionResult<ChecklistMantenimiento>> GetById(int id)
        {
            var item = await _repository.GetByIdAsync(id);
            if (item == null) return NotFound();
            return Ok(item);
        }

        [HttpPost]
        public async Task<ActionResult<ChecklistMantenimiento>> Create(ChecklistMantenimiento checklistMantenimiento)
        {
            var created = await _repository.CreateAsync(checklistMantenimiento);
            return CreatedAtAction(nameof(GetById), new { id = created.Id }, created);
        }

        [HttpPut("{id}")]
        public async Task<ActionResult<ChecklistMantenimiento>> Update(int id, ChecklistMantenimiento checklistMantenimiento)
        {
            var updated = await _repository.UpdateAsync(id, checklistMantenimiento);
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
