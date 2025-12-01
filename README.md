# Sistema de Gestión de Mantenimiento Programado de Maquinaria Industrial - Maintix

## Descripción

Aplicación móvil para la gestión integral del mantenimiento programado de maquinaria en instalaciones industriales. Permite registrar, asignar y controlar mantenimientos basados en horas de uso o tiempo transcurrido, con sistema de alertas y registro histórico completo.

## Estado del Proyecto

🚀 **Versión 1.0 - Aplicación funcional lista para entrega**

- ✅ Diseño de arquitectura
- ✅ Modelo de base de datos (11 tablas)
- ✅ Infraestructura configurada (VPS OVH, SQL Server 2022, IIS)
- ✅ Backend API REST completo (15 controllers)
- ✅ Sistema de alertas automáticas
- ✅ Generación automática de mantenimientos al actualizar horas
- ✅ Asignación de operarios
- ✅ Autenticación JWT con roles y control de acceso
- ✅ Swagger con autenticación JWT
- ✅ Base de datos poblada con datos de prueba
- ✅ Aplicación Android completa
  - ✅ Login y autenticación JWT
  - ✅ Dashboards por rol (Admin/Técnico)
  - ✅ Gestión completa de mantenimientos
  - ✅ Checklist interactivo con imágenes
  - ✅ Consulta y actualización de equipos
  - ✅ Historial de mantenimientos por equipo
  - ✅ Pull-to-refresh en todas las listas
  - ✅ Modo claro/oscuro según el sistema
  - ✅ Pantalla de usuario con logout
  - ✅ Navegación dinámica según contexto
  - ✅ Contador de mantenimientos pendientes
  - ✅ Paleta de colores corporativa (naranja #FF7A00)
- ⏳ Documentación final

## Tecnologías

### Backend
- **Framework:** ASP.NET Core 8.0 Web API
- **ORM:** Entity Framework Core
- **Patrón:** Repository + Service Layer
- **Autenticación:** JWT Bearer Tokens con [Authorize]
- **Documentación:** Swagger UI con soporte JWT
- **Base de datos:** SQL Server 2022 Developer

### Infraestructura
- **Servidor:** VPS OVH Cloud (4 vCores, 8GB RAM, 75GB NVMe)
- **Sistema Operativo:** Windows Server 2025
- **Web Server:** IIS 10.0
- **Conexión:** IP pública en producción

### Mobile
- **Plataforma:** Android
- **IDE:** Android Studio
- **Lenguaje:** Kotlin
- **Framework UI:** Jetpack Compose
- **Networking:** Retrofit + OkHttp
- **Material Design 3** con tema personalizado

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
│   │   │   │       │   ├── MainScaffold.kt
│   │   │   │       │   ├── MainScaffoldAdmin.kt
│   │   │   │       │   ├── MaintixButton.kt
│   │   │   │       │   └── ItemDetalleModal.kt
│   │   │   │       │
│   │   │   │       ├── navigation/
│   │   │   │       │   └── AppNavigation.kt  # Navegación
│   │   │   │       │
│   │   │   │       ├── screens/         # Pantallas principales
│   │   │   │       │   ├── LoginScreen.kt
│   │   │   │       │   ├── DashboardScreen.kt        # Técnico
│   │   │   │       │   ├── DashboardAdminScreen.kt   # Admin
│   │   │   │       │   ├── MaquinasScreen.kt
│   │   │   │       │   ├── DetalleEquipoScreen.kt
│   │   │   │       │   ├── DetalleMantenimientoScreen.kt
│   │   │   │       │   ├── ChecklistScreen.kt
│   │   │   │       │   └── UsuarioScreen.kt
│   │   │   │       │
│   │   │   │       └── theme/           # Tema y estilos
│   │   │   │           ├── Color.kt
│   │   │   │           ├── Theme.kt
│   │   │   │           └── Type.kt
│   │   │   │
│   │   │   └── res/
│   │   │       ├── drawable/
│   │   │       │   └── login_img2.jpg
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

### Autenticación
```
POST /api/Auth/login
```
Login con email y contraseña → Devuelve token JWT

---

### Gestión de Mantenimientos
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
- Alertas de equipos que necesitan mantenimiento
- Creación automática de mantenimientos con checklist
- Creación masiva de mantenimientos desde alertas
- Asignación de operarios a mantenimientos
- Actualización de progreso del checklist
- Finalización de mantenimiento (resetea contadores)
- Actualización de horas de funcionamiento (genera mantenimientos automáticamente)

---

### Equipos
```
GET    /api/Equipo/resumen          
GET    /api/Equipo                  
GET    /api/Equipo/{id}
POST   /api/Equipo
PUT    /api/Equipo/{id}
DELETE /api/Equipo/{id}
```

---

### Entidades CRUD Estándar
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

### Protección de Endpoints
Los endpoints de gestión requieren autenticación JWT con atributo [Authorize].

**Header requerido:**
```
Authorization: Bearer {token}
```

**Documentación interactiva:** Swagger UI en `/swagger` con soporte para Bearer Token

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

### Campos destacados:
- **mantenimientos.operario_asignado_id** - Operario responsable del mantenimiento
- **items_mantenimiento.descripcion_detallada** - Instrucciones completas
- **items_mantenimiento.herramientas** - Herramientas necesarias
- **items_mantenimiento.imagen_url** - Imagen de referencia del procedimiento

## Funcionalidades Android

### Autenticación
- Login con email y contraseña
- Almacenamiento seguro del token JWT
- Control de acceso por rol (Admin/Técnico)

### Dashboard Técnico
- Lista de mantenimientos asignados
- Filtrado por estado (pendientes/en progreso)
- Navegación contextual (muestra botón si hay mantenimiento en curso)
- Badge con contador de mantenimientos pendientes
- Pull-to-refresh

### Dashboard Administrador
- Visualización de todos los mantenimientos
- Agrupación por estado (sin asignar/pendientes/finalizados)
- Asignación de técnicos a mantenimientos
- Porcentaje de progreso del checklist
- Pull-to-refresh

### Gestión de Equipos
- Lista de equipos con información básica
- Detalle completo del equipo
- Actualización de horas de funcionamiento
- Histórico de mantenimientos por equipo
- Generación automática de mantenimientos al actualizar horas

### Checklist Interactivo
- Lista de items del mantenimiento
- Marcar items como completados
- Visualización de imágenes de referencia
- Instrucciones detalladas por item
- Herramientas necesarias por item
- Modo lectura para administradores
- Progreso en tiempo real

### Interfaz
- Modo claro/oscuro adaptativo al sistema
- Paleta corporativa naranja (#FF7A00)
- Material Design 3
- Imagen de fondo en login
- Navegación por pestañas según contexto
- Modales con bordes y sombreado
- Pantalla de usuario con logout

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
cd Maintix_API

# Restaurar paquetes NuGet
dotnet restore

# Ejecutar la aplicación
dotnet run
```

### Android App
1. Abrir el proyecto en Android Studio
2. Sincronizar dependencias de Gradle
3. Configurar la URL de la API en `RetrofitClient.kt`
4. Ejecutar en emulador o dispositivo físico

## Próximos Pasos

1. 📚 Completar documentación final
2. 🚀 Preparar entrega del proyecto

## Autor

**Antonio Martín**  
Ciclo Formativo de Grado Superior - Desarrollo de Aplicaciones Multiplataforma  
I.E.S. Fernando Wirtz Suárez - A Coruña  
Curso: 2025-2026

## Licencia

Proyecto académico - Todos los derechos reservados