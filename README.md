# Sistema de Gestión de Mantenimiento Programado de Maquinaria Industrial - Maintix

## Descripción

Aplicación móvil para la gestión integral del mantenimiento programado de maquinaria en instalaciones industriales. Permite registrar, asignar y controlar mantenimientos basados en horas de uso o tiempo transcurrido, con sistema de alertas y registro histórico completo.

## Estado del Proyecto

🚀 **Versión 0.4.0 - Android funcional con gestión completa**

- ✅ Diseño de arquitectura
- ✅ Modelo de base de datos (11 tablas)
- ✅ Infraestructura configurada (VPS OVH, SQL Server 2022, IIS)
- ✅ **Backend API REST CRUD básico** (12 controllers)
- ✅ **Sistema de alertas automáticas**
- ✅ **Gestión masiva de mantenimientos**
- ✅ **Asignación de operarios**
- ✅ **Autenticación JWT con roles**
- ✅ **Servicios de lógica de negocio**
- ✅ Base de datos poblada con datos de prueba
- ✅ **🔥 Aplicación Android funcional** (NUEVO)
  - ✅ Login y autenticación JWT
  - ✅ Dashboards por rol (Admin/Técnico)
  - ✅ Gestión completa de mantenimientos
  - ✅ Checklist interactivo con imágenes
  - ✅ Consulta y actualización de equipos
  - ✅ Historial de mantenimientos por equipo
  - ✅ Visualización de información de proveedores
- 🔨 **Refinamiento UI/UX y funcionalidades adicionales** (en progreso)

## Tecnologías

### Backend
- **Framework:** ASP.NET Core 8.0 Web API
- **ORM:** Entity Framework Core
- **Patrón:** Repository + Service Layer
- **Autenticación:** JWT Bearer Tokens
- **Documentación:** Swagger UI
- **Base de datos:** SQL Server 2022 Developer

### Infraestructura
- **Servidor:** VPS OVH Cloud (4 vCores, 8GB RAM, 75GB NVMe)
- **Sistema Operativo:** Windows Server 2025
- **Web Server:** IIS 10.0
- **Conexión:** IP pública con dominio/SSL (producción)

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
│   │   └── 01-crear-tablas.sql   # Script creación de tablas
│   └── diagrams/                 # Diagramas ER
│       └── esquema_inicial.png
│
├── Maintix_API/                   # API REST en .NET ✅
│   ├── Controllers/              # 15 controllers REST
│   │   ├── AuthController.cs             # Login JWT
│   │   ├── MantenimientoServiceController.cs # Lógica de negocio
│   │   ├── TipoUsuarioController.cs
│   │   ├── UsuarioController.cs
│   │   ├── ProveedorController.cs
│   │   ├── TipoMaquinariaController.cs
│   │   ├── TipoMaquinaController.cs
│   │   ├── EquipoController.cs
│   │   ├── TipoMantenimientoController.cs
│   │   ├── MantenimientoController.cs
│   │   ├── ItemMantenimientoController.cs
│   │   ├── ChecklistMantenimientoController.cs
│   │   ├── HistoricoController.cs
│   │   ├── HealthController.cs
│   │   └── WeatherForecastController.cs
│   │
│   ├── Models/                   # 11 entidades del dominio
│   │   ├── TipoUsuario.cs
│   │   ├── Usuario.cs
│   │   ├── Proveedor.cs
│   │   ├── TipoMaquinaria.cs
│   │   ├── TipoMaquina.cs
│   │   ├── Equipo.cs
│   │   ├── TipoMantenimiento.cs
│   │   ├── Mantenimiento.cs
│   │   ├── ItemMantenimiento.cs
│   │   ├── ChecklistMantenimiento.cs
│   │   └── Historico.cs
│   │
│   ├── DTOs/                     # Data Transfer Objects
│   │   ├── LoginDto.cs
│   │   ├── CrearMantenimientoDto.cs
│   │   ├── MantenimientoConChecklistDto.cs
│   │   ├── ActualizarChecklistDto.cs
│   │   ├── ActualizarHorasDto.cs
│   │   ├── FinalizarMantenimientoDto.cs
│   │   ├── AlertaMantenimientoDto.cs
│   │   ├── AlertaEquipoDto.cs
│   │   ├── ChecklistItemDto.cs
│   │   └── TipoMantenimientoEnum.cs
│   │
│   ├── Services/                 # Lógica de negocio
│   │   ├── IAuthService.cs
│   │   ├── AuthService.cs
│   │   ├── IMantenimientoService.cs
│   │   └── MantenimientoService.cs
│   │
│   ├── Data/
│   │   └── MaintixDbContext.cs   # DbContext EF Core
│   │
│   ├── Repositories/             # Pattern Repository
│   │   ├── IEquipoRepository.cs
│   │   ├── EquipoRepository.cs
│   │   ├── IMantenimientoRepository.cs
│   │   ├── MantenimientoRepository.cs
│   │   ├── IItemMantenimientoRepository.cs
│   │   ├── ItemMantenimientoRepository.cs
│   │   ├── IChecklistMantenimientoRepository.cs
│   │   ├── ChecklistMantenimientoRepository.cs
│   │   ├── IHistoricoRepository.cs
│   │   ├── HistoricoRepository.cs
│   │   ├── IProveedorRepository.cs
│   │   ├── ProveedorRepository.cs
│   │   ├── ITipoMantenimientoRepository.cs
│   │   ├── TipoMantenimientoRepository.cs
│   │   ├── ITipoMaquinaRepository.cs
│   │   ├── TipoMaquinaRepository.cs
│   │   ├── ITipoMaquinariaRepository.cs
│   │   ├── TipoMaquinariaRepository.cs
│   │   ├── ITipoUsuarioRepository.cs
│   │   ├── TipoUsuarioRepository.cs
│   │   ├── IUsuarioRepository.cs
│   │   └── UsuarioRepository.cs
│   │
│   ├── Program.cs                # JWT configurado
│   └── appsettings.json         # JWT settings
│
├── MaintixApp/                    # Aplicación Android ✅
│   ├── app/
│   │   ├── src/main/
│   │   │   ├── java/com/tonhete/maintixapp/
│   │   │   │   ├── MainActivity.kt
│   │   │   │   │
│   │   │   │   ├── data/                # Capa de datos
│   │   │   │   │   ├── ApiService.kt    # Endpoints Retrofit
│   │   │   │   │   ├── RetrofitClient.kt
│   │   │   │   │   ├── AppState.kt      # Estado global
│   │   │   │   │   └── models/          # Modelos de datos
│   │   │   │   │       ├── Auth.kt
│   │   │   │   │       ├── Usuario.kt
│   │   │   │   │       ├── Equipo.kt
│   │   │   │   │       ├── Mantenimiento.kt
│   │   │   │   │       ├── ItemMantenimiento.kt
│   │   │   │   │       ├── CheckListMantenimiento.kt
│   │   │   │   │       └── Historico.kt
│   │   │   │   │
│   │   │   │   └── ui/                  # Interfaz de usuario
│   │   │   │       ├── components/      # Componentes reutilizables
│   │   │   │       │   ├── BottomNavBar.kt
│   │   │   │       │   ├── BottomNavBarAdmin.kt
│   │   │   │       │   └── ItemDetalleModal.kt
│   │   │   │       │
│   │   │   │       ├── navigation/
│   │   │   │       │   └── NavGraph.kt  # Navegación
│   │   │   │       │
│   │   │   │       ├── screens/         # Pantallas principales
│   │   │   │       │   ├── LoginScreen.kt
│   │   │   │       │   ├── DashboardScreen.kt        # Técnico
│   │   │   │       │   ├── DashboardAdminScreen.kt   # Admin
│   │   │   │       │   ├── MaquinasScreen.kt
│   │   │   │       │   ├── DetalleEquipoScreen.kt
│   │   │   │       │   ├── DetalleMantenimientoScreen.kt
│   │   │   │       │   └── ChecklistScreen.kt
│   │   │   │       │
│   │   │   │       └── theme/           # Tema y estilos
│   │   │   │           ├── Color.kt
│   │   │   │           ├── Theme.kt
│   │   │   │           └── Type.kt
│   │   │   │
│   │   │   └── res/
│   │   │       ├── values/
│   │   │       │   ├── strings.xml
│   │   │       │   ├── colors.xml
│   │   │       │   └── themes.xml
│   │   │       └── xml/
│   │   │           └── network_security_config.xml
│   │   │
│   │   └── build.gradle.kts
│   │
│   ├── gradle/
│   │   └── libs.versions.toml
│   ├── build.gradle.kts
│   └── settings.gradle.kts
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
GET    /api/Equipo/resumen          
GET    /api/Equipo                  
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
6. **equipo** - Equipos con contadores de horas (A, B, C) 
7. **tipos_mantenimiento** - Tipos de mantenimiento (A, B, C)
8. **mantenimientos** - Registros con estados y operario asignado 
9. **items_mantenimiento** - Items con descripción detallada, herramientas e imágenes
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

1. 🔨 **Refinamiento UI/UX de la aplicación Android**
2. 🔨 **Despieces y documentación técnica por equipo**
3. 🔨 **Sistema de adjuntar fotografías en mantenimientos**
4. 🔨 **Internacionalización (ES/EN/EU)**
5. 🧪 **Testing y refactorización**
6. 📚 **Documentación final**
7. 🚀 **Despliegue en producción**
8. 📊 Dashboard de estadísticas (opcional)
9. 🔔 Sistema de notificaciones push (opcional)

## Autor

**Antonio Martín**  
Ciclo Formativo de Grado Superior - Desarrollo de Aplicaciones Multiplataforma  
I.E.S. Fernando Wirtz Suárez - A Coruña  
Curso: 2025-2026

## Licencia

Proyecto académico - Todos los derechos reservados