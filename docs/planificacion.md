# Planificación del Proyecto Maintix

## Resumen

- **Duración total:** 134 horas
- **Fecha inicio:** 21 de septiembre de 2025
- **Fecha finalización:** 05 de diciembre de 2025

---

## Fases del Proyecto

### FASE 1: Análisis y Diseño - 8 horas (Completado)
**Fechas:** 21/09/2025 - 30/09/2025

- Definición de requisitos
- Diseño de arquitectura del sistema
- Diseño de base de datos (modelo ER)
- Documentación inicial (anteproxecto)

### FASE 2: Infraestructura - 12 horas (Completado)
**Fechas:** 01/10/2025 - 18/10/2025

- Instalación Windows Server 2025
- Instalación y configuración SQL Server 2022
- Instalación y configuración IIS
- Configuración del entorno de desarrollo

### FASE 3: Base de Datos - 8 horas (Completado)
**Fechas:** 19/10/2025 - 01/11/2025

- Creación de scripts SQL (estructura de tablas)
- Datos de prueba iniciales
- Pruebas de integridad y relaciones
- Configuración de conexión remota a SQL Server

### FASE 4: Backend API REST - 40 horas (Completado)
**Fechas:** 01/11/2025 - 15/11/2025

- Desarrollo completo API REST en ASP.NET Core 8.0
- Arquitectura MVC + Service Layer (Models, Repository, Services, Controllers)
- Implementación de 15 controllers con endpoints CRUD
- Sistema de alertas automáticas
- Gestión masiva de mantenimientos
- Asignación de operarios a mantenimientos
- Autenticación JWT con roles (admin/operario)
- Control de acceso con [Authorize]
- Swagger UI con autenticación JWT
- DTOs para transferencia de datos
- Testing con Swagger UI
- Configuración de CORS y JWT

### FASE 5: Aplicación Android - 62 horas (Completado)
**Fechas:** 06/11/2025 - 01/12/2025

- Setup proyecto Android Studio (6h)
- Integración con API y conexión (6h)
- Sistema de login y autenticación JWT (4h)
- Dashboards por rol (Admin/Técnico) (8h)
- Gestión completa de mantenimientos (10h)
- Checklist interactivo con imágenes (6h)
- Gestión de equipos y actualización de horas (6h)
- Generación automática de mantenimientos (2h)
- Histórico de mantenimientos (4h)
- Pull-to-refresh en listas (2h)
- Modo claro/oscuro (4h)
- Pantalla de usuario con logout (2h)
- Navegación contextual y contador de pendientes (2h)

### FASE 6: Documentación Final - 4 horas
**Fechas:** 02/12/2025 - 15/12/2025

- Memoria técnica completa
- Manual de usuario
- Manual de instalación
- README del repositorio

---

## Hitos del Proyecto

| Hito | Fecha | Estado |
|------|-------|--------|
| Diseño completo | 30/09/2025 | ✅ |
| Infraestructura operativa | 18/10/2025 | ✅ |
| Base de datos funcional | 01/11/2025 | ✅ |
| API REST completa | 15/11/2025 | ✅ |
| App móvil funcional | 01/12/2025 | ✅ |
| Documentación completa | 05/12/2025 | ⏳ |

---

## Progreso Actual

**Horas completadas:** 130 de 134 horas (97%)

- ✅ Fase 1: Análisis y Diseño - 8h
- ✅ Fase 2: Infraestructura - 12h  
- ✅ Fase 3: Base de Datos - 8h
- ✅ Fase 4: Backend API REST - 40h
- ✅ Fase 5: Aplicación Android - 62h
- ⏳ Fase 6: Documentación Final - 0/4h

**Fase actual:** Documentación final  
**Próximo hito:** Entrega final (15/12/2025)

---

## Recursos Necesarios

- ✅ VPS OVH Cloud con Windows Server 2025
- ✅ SQL Server 2022 Developer
- ✅ IIS 10.0 configurado
- ✅ Visual Studio 2022
- ✅ Android Studio
- ✅ Dispositivo Android para pruebas
- ✅ Git y GitHub

---

## Riesgos Identificados

| Riesgo | Mitigación | Estado |
|--------|------------|--------|
| Problemas de conexión API-App | Testing temprano de integración | ✅ |
| Tiempo insuficiente | Priorizar funcionalidades core | ✅ |
| Bugs de última hora | Dejar margen en cada fase | ✅ |
| Complejidad Android | Simplificar UI inicial | ✅ |

---