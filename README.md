# Sistema de Gestión de Mantenimiento Programado de Maquinaria Industrial - Maintix

## Descripción

Aplicación móvil para la gestión integral del mantenimiento programado de maquinaria en instalaciones industriales. Permite registrar, asignar y controlar mantenimientos basados en horas de uso o tiempo transcurrido, con sistema de alertas y registro histórico completo.

## Estado del Proyecto

🚀 **Versión 0.3.0 - Backend con lógica de negocio + Android en desarrollo**

- ✅ Diseño de arquitectura
- ✅ Modelo de base de datos (11 tablas)
- ✅ Infraestructura configurada (Windows Server 2022, SQL Server 2022, IIS)
- ✅ **Backend API REST CRUD básico** (11 controllers)
- ✅ **🔥 Sistema de alertas automáticas** (NUEVO)
- ✅ **🔥 Gestión masiva de mantenimientos** (NUEVO)
- ✅ **🔥 Asignación de operarios** (NUEVO)
- ✅ **🔥 Autenticación JWT con roles** (NUEVO)
- ✅ **🔥 Servicios de lógica de negocio** (NUEVO)
- ✅ Base de datos poblada con datos de prueba
- 🔨 **Aplicación Android en desarrollo** (primeras pantallas funcionando)

## Tecnologías

### Backend
- **Framework:** ASP.NET Core 8.0 Web API
- **ORM:** Entity Framework Core
- **Patrón:** Repository + Service Layer
- **Autenticación:** JWT Bearer Tokens
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
│       ├── Controllers/          # 12 controllers REST 🔥
│       │   ├── AuthController.cs             # 🔥 NUEVO - Login JWT
│       │   ├── MantenimientoServiceController.cs # 🔥 NUEVO - Lógica de negocio
│       │   ├── TipoUsuariosController.cs
│       │   ├── UsuariosController.cs
│       │   ├── ProveedoresController.cs
│       │   ├── TipoMaquinariaController.cs
│       │   ├── TiposMaquinaController.cs
│       │   ├── EquipoController.cs           # 🔥 ACTUALIZADO
│       │   ├── TiposMantenimientoController.cs
│       │   ├── MantenimientoController.cs
│       │   ├── ItemMantenimientoController.cs
│       │   ├── ChecklistMantenimientoController.cs
│       │   └── HistoricoController.cs
│       ├── Models/               # 11 entidades actualizadas
│       │   ├── TipoUsuario.cs
│       │   ├── Usuario.cs
│       │   ├── Proveedor.cs
│       │   ├── TipoMaquinaria.cs
│       │   ├── TipoMaquina.cs
│       │   ├── Equipo.cs                    # 🔥 Contadores A/B/C
│       │   ├── TipoMantenimiento.cs
│       │   ├── Mantenimiento.cs             # 🔥 OperarioAsignadoId
│       │   ├── ItemMantenimiento.cs         # 🔥 Campos adicionales
│       │   ├── ChecklistMantenimiento.cs
│       │   └── Historico.cs
│       ├── DTOs/                 # 🔥 NUEVO - Data Transfer Objects
│       │   ├── LoginDto.cs
│       │   ├── LoginResponseDto.cs
│       │   ├── CrearMantenimientoDto.cs
│       │   ├── CrearMantenimientoMasivoDto.cs
│       │   ├── AsignarOperarioDto.cs
│       │   ├── AlertaMantenimientoDto.cs
│       │   ├── MantenimientoConChecklistDto.cs
│       │   ├── ActualizarChecklistDto.cs
│       │   ├── ActualizarHorasDto.cs
│       │   ├── FinalizarMantenimientoDto.cs
│       │   └── ChecklistItemDto.cs
│       ├── Services/             # 🔥 NUEVO - Lógica de negocio
│       │   ├── IAuthService.cs
│       │   ├── AuthService.cs
│       │   ├── IMantenimientoService.cs
│       │   └── MantenimientoService.cs
│       ├── Data/
│       │   └── MaintixDbContext.cs          # DbContext EF Core
│       ├── Repositories/        # Pattern Repository
│       │   ├── IEquipoRepository.cs
│       │   ├── EquipoRepository.cs          # 🔥 Include relations
│       │   ├── IMantenimientoRepository.cs
│       │   ├── MantenimientoRepository.cs   # 🔥 Include relations
│       │   ├── IItemMantenimientoRepository.cs
│       │   ├── ItemMantenimientoRepository.cs
│       │   ├── IChecklistMantenimientoRepository.cs
│       │   └── ChecklistMantenimientoRepository.cs
│       ├── Program.cs           # 🔥 JWT configurado
│       └── appsettings.json    # 🔥 JWT settings
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

### 🔐 Autenticación (NUEVO)
```
POST /api/Auth/login
```
Login con email y contraseña → Devuelve token JWT

---

### ⚡ Gestión de Mantenimientos (NUEVO)
```
GET  /api/MantenimientoService/alertas/todas
GET  /api/MantenimientoService/equipo/{equipoId}/alertas
POST /api/MantenimientoService/crear-con-checklist
POST /api/MantenimientoService/crear-masivo
PUT  /api/MantenimientoService/{id}/asignar-operario
GET  /api/MantenimientoService/{id}/checklist
PUT  /api/MantenimientoService/{id}/actualizar-checklist
POST /api/MantenimientoService/{id}/finalizar
POST /api/MantenimientoService/equipo/{equipoId}/actualizar-horas
```

**Funcionalidades:**
- ⚠️ Obtener alertas de equipos que necesitan mantenimiento
- 🔧 Crear mantenimientos automáticamente con checklist
- 📋 Crear múltiples mantenimientos desde alertas
- 👤 Asignar operarios a mantenimientos
- ✅ Actualizar progreso del checklist
- 🏁 Finalizar mantenimiento (resetea contadores)
- ⏱️ Actualizar horas de funcionamiento

---

### 🔧 Equipos
```
GET    /api/Equipo/resumen          # 🔥 NUEVO - Lista: id, nombre, horas
GET    /api/Equipo                  # Con relaciones completas
GET    /api/Equipo/{id}
POST   /api/Equipo
PUT    /api/Equipo/{id}
DELETE /api/Equipo/{id}
```

---

### 📋 Entidades CRUD Estándar
Operaciones completas para:
```
/api/TipoUsuario
/api/Usuario
/api/Proveedor
/api/TipoMaquinaria
/api/TipoMaquina
/api/TipoMantenimiento
/api/Mantenimiento
/api/ItemMantenimiento
/api/ChecklistMantenimiento
/api/Historico
```

**Operaciones:** GET (todos), GET (por id), POST, PUT, DELETE

---

### 🔒 Protección de Endpoints
Los endpoints de alertas y gestión requieren autenticación JWT.

**Header requerido:**
```
Authorization: Bearer {token}
```

**Documentación interactiva:** Swagger UI en `/swagger`

## Base de Datos

**11 tablas principales:**

1. **tipo_usuario** - Tipos de usuario del sistema
2. **usuarios** - Usuarios administradores y operarios
3. **proveedores** - Proveedores de maquinaria y repuestos
4. **tipo_maquinaria** - Categorías con umbrales de mantenimiento A/B/C
5. **tipos_maquina** - Tipos específicos de máquinas
6. **equipo** - Equipos con contadores de horas (A, B, C) 🔥 ACTUALIZADO
7. **tipos_mantenimiento** - Tipos de mantenimiento (A, B, C)
8. **mantenimientos** - Registros con estados y operario asignado 🔥 ACTUALIZADO
9. **items_mantenimiento** - Items con descripción detallada, herramientas e imágenes 🔥 ACTUALIZADO
10. **checklist_mantenimiento** - Estado de items en cada mantenimiento
11. **historico** - Histórico de mantenimientos realizados

### 🔥 Campos Nuevos Añadidos:
- **mantenimientos.operario_asignado_id** - Operario responsable del mantenimiento
- **items_mantenimiento.descripcion_detallada** - Instrucciones completas
- **items_mantenimiento.herramientas** - Herramientas necesarias
- **items_mantenimiento.imagen_url** - Imagen de referencia del procedimiento

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

1. ~~📱 Desarrollo de la aplicación Android~~ 🔨 **EN PROGRESO**
2. ~~🔐 Implementación de autenticación JWT~~ ✅ **COMPLETADO**
3. 🔗 Integración API con aplicación móvil
4. 🧪 Testing end-to-end
5. 🚀 Despliegue en IIS
6. 📊 Dashboard de estadísticas (opcional)
7. 🔔 Sistema de notificaciones push (opcional)

## Autor

**Antonio Martín**  
Ciclo Formativo de Grado Superior - Desarrollo de Aplicaciones Multiplataforma  
I.E.S. Fernando Wirtz Suárez - A Coruña  
Curso: 2025-2026

## Licencia

Proyecto académico - Todos los derechos reservados
