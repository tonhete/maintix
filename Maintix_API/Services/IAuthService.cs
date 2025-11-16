using Maintix_API.Models;

namespace Maintix_API.Services
{
    using Maintix_API.DTOs;

    public interface IAuthService
    {
        Task<LoginResponseDto?> LoginAsync(string email, string password);
    }
}
