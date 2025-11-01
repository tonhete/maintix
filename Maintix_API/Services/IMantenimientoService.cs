using Maintix_API.DTOs;

namespace Maintix_API.Services
{
    public interface IMantenimientoService
    {
        Task<bool> ActualizarHorasEquipoAsync(int equipoId, int horasNuevas);
        Task<AlertaMantenimientoDto?> VerificarAlertasEquipoAsync(int equipoId);
        Task<MantenimientoConChecklistDto?> CrearMantenimientoConChecklistAsync(CrearMantenimientoDto dto);
        Task<bool> ActualizarChecklistAsync(int mantenimientoId, ActualizarChecklistDto dto);
        Task<bool> FinalizarMantenimientoAsync(int mantenimientoId, FinalizarMantenimientoDto dto);
        Task<MantenimientoConChecklistDto?> ObtenerMantenimientoConChecklistAsync(int mantenimientoId);
    }
}
