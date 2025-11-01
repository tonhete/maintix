# Sistema de Gestión de Mantenimiento Programado de Maquinaria Industrial - Maintix

## Descripción

Aplicación móvil para la gestión integral del mantenimiento programado de maquinaria en instalaciones industriales. Permite registrar, asignar y controlar mantenimientos basados en horas de uso o tiempo transcurrido, con sistema de alertas y registro histórico completo.

## Estado del Proyecto

🚀 **Versión 0.2.0 - Backend completado**

- ✅ Diseño de arquitectura
- ✅ Modelo de base de datos (11 tablas)
- ✅ Infraestructura configurada (Windows Server 2022, SQL Server 2022, IIS)
- ✅ **Backend API REST completado** (11 controllers, CRUD completo)
- ✅ Base de datos poblada con datos de prueba
- ⏳ Aplicación Android pendiente

## Tecnologías

### Backend
- **Framework:** ASP.NET Core 8.0 Web API
- **ORM:** Entity Framework Core
- **Patrón:** Repository Pattern
- **Documentación:** Swagger UI
- **Base de datos:** SQL Server 2022 Developer

### Infraestructura
- **Servidor:** Windows Server 2022 Standard (VM en VirtualBox)
- **Web Server:** IIS 10.0
- **Conexión:** TCP/IP en red local (192.168.1.138)

### Frontend (Pendiente)
- **Plataforma:** Android
- **IDE:** Android Studio
- **Lenguaje:** Java/Kotlin

### Control de versiones
- **Git** con repositorio en GitHub

## Estructura del Proyecto
```
maintix/
├── docs/                          # Documentación del proyecto
│   ├── planificacion.md          # Planificación y cronograma
│   ├── alcance.md                # Alcance del proyecto
│   └── presupuesto.md            # Presupuesto detallado
│
├── database/                      # Base de datos
│   ├── scripts/
│   │   ├── 01-crear-tablas.sql   # Script creación de tablas
│   │   └── 02-datos-prueba.sql   # Datos de prueba
│   └── diagrams/                 # Diagramas ER
│
├── backend/                       # API REST en .NET
│   └── MaintixAPI/
│       ├── Controllers/          # 11 controllers REST
│       │   ├── TipoUsuariosController.cs
│       │   ├── UsuariosController.cs
│       │   ├── ProveedoresController.cs
│       │   ├── TipoMaquinariaController.cs
│       │   ├── TiposMaquinaController.cs
│       │   ├── EquiposController.cs
│       │   ├── TiposMantenimientoController.cs
│       │   ├── MantenimientosController.cs
│       │   ├── ItemsMantenimientoController.cs
│       │   ├── ChecklistMantenimientoController.cs
│       │   └── HistoricosController.cs
│       ├── Models/               # 11 entidades del dominio
│       │   ├── TipoUsuario.cs
│       │   ├── Usuario.cs
│       │   ├── Proveedor.cs
│       │   ├── TipoMaquinaria.cs
│       │   ├── TipoMaquina.cs
│       │   ├── Equipo.cs
│       │   ├── TipoMantenimiento.cs
│       │   ├── Mantenimiento.cs
│       │   ├── ItemMantenimiento.cs
│       │   ├── ChecklistMantenimiento.cs
│       │   └── Historico.cs
│       ├── Data/
│       │   └── MaintixContext.cs # DbContext EF Core
│       ├── Repositories/
│       │   └── Repository.cs     # Repositorio genérico
│       ├── Program.cs
│       └── appsettings.json
│
├── mobile/                        # Aplicación Android (Pendiente)
│   └── MaintixApp/
│       ├── app/
│       ├── gradle/
│       └── build.gradle
│
├── infrastructure/                # Configuración de servidor
│   └── configuracion-servidor.md # Guía de instalación
│
├── CHANGELOG.md                   # Historial de cambios
├── README.md                      # Este archivo
└── .gitignore                    # Archivos ignorados por Git
```

## Endpoints de la API

La API proporciona operaciones CRUD completas para todas las entidades:

- `GET /api/{entidad}` - Listar todos
- `GET /api/{entidad}/{id}` - Obtener por ID
- `POST /api/{entidad}` - Crear nuevo
- `PUT /api/{entidad}/{id}` - Actualizar existente
- `DELETE /api/{entidad}/{id}` - Eliminar

**Documentación interactiva:** Swagger UI disponible en `/swagger`

## Base de Datos

**11 tablas principales:**

1. **tipo_usuario** - Tipos de usuario del sistema
2. **usuarios** - Usuarios administradores y operarios
3. **proveedores** - Proveedores de maquinaria y repuestos
4. **tipo_maquinaria** - Categorías de maquinaria
5. **tipos_maquina** - Tipos específicos de máquinas
6. **equipo** - Equipos y maquinaria del inventario
7. **tipos_mantenimiento** - Tipos de mantenimiento (A, B, C)
8. **mantenimientos** - Registros de mantenimientos programados
9. **items_mantenimiento** - Items del checklist por tipo de mantenimiento
10. **checklist_mantenimiento** - Estado de items en cada mantenimiento
11. **historico** - Histórico de mantenimientos realizados

## Documentación

- [Planificación del proyecto](docs/planificacion.md)
- [Alcance del proyecto](docs/alcance.md)
- [Presupuesto](docs/presupuesto.md)
- [Historial de cambios](CHANGELOG.md)
- [Configuración del servidor](infrastructure/configuracion-servidor.md)

## Instalación y Ejecución

### Backend API
```bash
# Clonar el repositorio
git clone [URL_REPOSITORIO]

# Navegar al proyecto backend
cd backend/MaintixAPI

# Restaurar paquetes NuGet
dotnet restore

# Ejecutar la aplicación
dotnet run
```

## Próximos Pasos

1. 📱 Desarrollo de la aplicación Android
2. 🔐 Implementación de autenticación JWT
3. 🔗 Integración API con aplicación móvil
4. 🧪 Testing end-to-end
5. 🚀 Despliegue en IIS

## Autor

**Antonio Martín**  
Ciclo Formativo de Grado Superior - Desarrollo de Aplicaciones Multiplataforma  
I.E.S. Fernando Wirtz Suárez - A Coruña  
Curso: 2025-2026

## Licencia

Proyecto académico - Todos los derechos reservados