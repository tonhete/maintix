# Planificación del Proyecto Maintix

## Resumen

- **Duración total:** 134 horas
- **Fecha inicio:** 21 de septiembre de 2025
- **Fecha finalización:** 15 de diciembre de 2025

---

## Fases del Proyecto

### FASE 1: Análisis y Diseño - 8 horas ✅
**Fechas:** 21/09/2025 - 30/09/2025

- Definición de requisitos
- Diseño de arquitectura del sistema
- Diseño de base de datos (modelo ER)
- Documentación inicial (anteproxecto)

### FASE 2: Infraestructura - 12 horas ✅
**Fechas:** 01/10/2025 - 18/10/2025

- Instalación Windows Server 2022
- Instalación y configuración SQL Server 2022
- Instalación y configuración IIS
- Configuración del entorno de desarrollo

### FASE 3: Base de Datos - 8 horas ✅
**Fechas:** 19/10/2025 - 01/11/2025

- Creación de scripts SQL (estructura de tablas)
- Datos de prueba iniciales
- Pruebas de integridad y relaciones
- Configuración de conexión remota a SQL Server

### FASE 4: Backend API REST - 40 horas ✅
**Fechas:** 01/11/2025 - 15/11/2025

- Desarrollo completo API REST en ASP.NET Core 8.0
- Arquitectura MVC + Service Layer (Models, Repository, Services, Controllers)
- Implementación de 12 controllers con endpoints CRUD
- **🔥 Sistema de alertas automáticas** (NUEVO)
- **🔥 Gestión masiva de mantenimientos** (NUEVO)
- **🔥 Asignación de operarios a mantenimentos** (NUEVO)
- **🔥 Autenticación JWT con roles (admin/operario)** (NUEVO)
- **🔥 DTOs para transferencia de datos** (NUEVO)
- Testing con Swagger UI
- Configuración de CORS y JWT

### FASE 5: Aplicación Android - 62 horas 🔨
**Fechas:** 06/11/2025 - 10/12/2025

- ✅ Setup proyecto Android Studio
- ✅ Integración con API y conexión
- ✅ Diseño UI/UX básico de la aplicación
- ✅ Funcionalidades principales (listas, detalle, checklist)
- ✅ Sistema de login y autenticación
- ✅ Gestión de equipos y actualización de horas
- 🔨 Refinamiento UI/UX (10h)
- 🔨 Despieces y documentación técnica (4h)
- 🔨 Sistema de adjuntar fotografías (6h)
- 🔨 Internacionalización ES/EN/EU (4h)
- ⏳ Refactorización y optimización (6h)
- ⏳ Testing en dispositivo físico (4h)

### FASE 6: Documentación Final - 4 horas
**Fechas:** 26/11/2025 - 01/12/2025

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
| App móvil funcional | 10/12/2025 | 🔨 |
| Documentación completa | 15/12/2025 | ⏳ |

---

## Progreso Actual

**Horas completadas:** 98 de 134 horas (73%)

- ✅ Fase 1: Análisis y Diseño - 8h
- ✅ Fase 2: Infraestructura - 12h  
- ✅ Fase 3: Base de Datos - 8h
- ✅ Fase 4: Backend API REST - 40h
- 🔨 Fase 5: Aplicación Android - 30/62h
  - ✅ Setup y arquitectura base - 6h
  - ✅ Autenticación y dashboards - 6h
  - ✅ Gestión de mantenimientos - 10h
  - ✅ Gestión de equipos - 8h
  - 🔨 Refinamiento UI/UX - 0/10h
  - 🔨 Despieces - 0/4h
  - 🔨 Sistema de fotografías - 0/6h
  - 🔨 Internacionalización - 0/4h
  - ⏳ Refactorización - 0/6h
  - ⏳ Testing - 0/4h
- ⏳ Fase 6: Documentación Final - 0/4h

**Fase actual:** Aplicación Android (refinamiento y nuevas funcionalidades)  
**Próximo hito:** App móvil completa (10/12/2025)

---

## Recursos Necesarios

- Máquina virtual VirtualBox con Windows Server 2022 (configurada)
- SQL Server 2022 Developer (instalado y operativo)
- Visual Studio 2022 (instalado)
- Android Studio (pendiente configuración)
- Dispositivo Android para pruebas

---

## Riesgos Identificados

| Riesgo | Mitigación | Estado |
|--------|------------|--------|
| Problemas de conexión API-App | Testing temprano de integración | ⏳ |
| Tiempo insuficiente | Priorizar funcionalidades core | ✅ |
| Bugs de última hora | Dejar margen en cada fase | ⏳ |
| Complejidad Android | Simplificar UI inicial | ⏳ |

---