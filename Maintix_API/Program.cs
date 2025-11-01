using Maintix_API.Data;
using Microsoft.EntityFrameworkCore;
using Maintix_API.Repositories;
using Maintix_API.Services;
using Maintix_API.DTOs;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.

// Configurar DbContext con SQL Server
builder.Services.AddDbContext<MaintixDbContext>(options =>
    options.UseSqlServer(builder.Configuration.GetConnectionString("DefaultConnection")));

// Configurar CORS
builder.Services.AddCors(options =>
{
    options.AddPolicy("AllowAll",
        builder =>
        {
            builder.AllowAnyOrigin()
                   .AllowAnyMethod()
                   .AllowAnyHeader();
        });
});

builder.Services.AddControllers();
// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

builder.Services.AddScoped<IMantenimientoService, MantenimientoService>();
builder.Services.AddScoped<ITipoUsuarioRepository, TipoUsuarioRepository>();
builder.Services.AddScoped<IUsuarioRepository, UsuarioRepository>();
builder.Services.AddScoped<IProveedorRepository, ProveedorRepository>();
builder.Services.AddScoped<ITipoMaquinariaRepository, TipoMaquinariaRepository>();
builder.Services.AddScoped<ITipoMaquinaRepository, TipoMaquinaRepository>();
builder.Services.AddScoped<IEquipoRepository, EquipoRepository>();
builder.Services.AddScoped<ITipoMantenimientoRepository, TipoMantenimientoRepository>();
builder.Services.AddScoped<IMantenimientoRepository, MantenimientoRepository>();
builder.Services.AddScoped<IItemMantenimientoRepository, ItemMantenimientoRepository>();
builder.Services.AddScoped<IChecklistMantenimientoRepository, ChecklistMantenimientoRepository>();
builder.Services.AddScoped<IHistoricoRepository, HistoricoRepository>();

var app = builder.Build();

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseHttpsRedirection();

// Usar CORS
app.UseCors("AllowAll");

app.UseAuthorization();

app.MapControllers();

app.Run();
