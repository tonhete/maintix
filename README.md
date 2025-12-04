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
  - ✅ Documentacion final

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

### Entidades CRUD Estándar
Operaciones completas para:
```
/api/Equipo
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
- Almacenamiento token JWT
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
- Material Design 3
- Imagen de fondo en login
- Navegación por pestañas según contexto
- Modales con bordes y sombreado
- Pantalla de usuario con logout

## Documentación

- [Memoria técnica](docs/memoria_tecnica_maintix.md)
- [Manual de usuario](docs/manual_usuario_maintix.md)
- [Planificación del proyecto](docs/planificacion.md)
- [Alcance del proyecto](docs/alcance.md)
- [Presupuesto](docs/presupuesto.md)
- [Diagrama base de datos](docs/diagrama_bbdd.png)

## Instalación y Ejecución

### Backend API
```bash
# Clonar el repositorio
git clone https://github.com/tonhete/maintix

# Navegar al proyecto backend
cd Maintix_API

# Restaurar paquetes NuGet
dotnet restore

# Configurar conexión a base de datos en appsettings.json

# Ejecutar la aplicación
dotnet run
```

### Android App
1. Abrir el proyecto en Android Studio
2. Sincronizar dependencias de Gradle
3. Configurar la URL de la API en `RetrofitClient.kt`
4. Ejecutar en emulador o dispositivo físico

## Autor

**Antonio Martín**  
Ciclo Formativo de Grado Superior - Desarrollo de Aplicaciones Multiplataforma  
I.E.S. Fernando Wirtz Suárez - A Coruña  
Curso: 2025-2026

## Licencia

Proyecto académico - Todos los derechos reservados