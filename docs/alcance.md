# Alcance del Proyecto Maintix

## Dentro del Alcance

### Backend (✅ Implementado)
- ✅ **Sistema de autenticación JWT con roles** (admin/operario)
- ✅ **Alertas automáticas por horas de funcionamiento** (tipos A/B/C)
- ✅ **Generación masiva de mantenimientos** desde alertas
- ✅ **Asignación de operarios** a mantenimientos específicos
- ✅ **Gestión de estados** (pendiente_asignacion → pendiente → finalizado)
- ✅ **Checklist automático** según tipo de máquina y mantenimiento
- ✅ **Actualización de horas** de funcionamiento de equipos
- ✅ **Reseteo automático de contadores** al finalizar mantenimiento
- ✅ **Items de mantenimiento enriquecidos** (descripción detallada, herramientas, imágenes)
- ✅ API REST completa con 40+ endpoints

### Mobile (🔨 En Desarrollo)
- ✅ Aplicación móvil Android para gestión de mantenimientos
- ✅ Sistema de login con JWT
- ✅ Consulta de equipos y maquinaria
- ✅ Visualización de alertas
- ✅ Mantenimientos asignados al operario
- ✅ Checklist interactivo de pasos a seguir
- ✅ Registro histórico de mantenimientos
- ✅ Consulta de histórico por equipo
- 🔨 Despieces y documentación técnica por equipo
- 🔨 Sistema de adjuntar fotografías en mantenimientos
- 🔨 Internacionalización (ES/EN/EU)
- ⏳ Refinamiento UI/UX completo

## Fuera del Alcance

- Gestión completa de equipos desde la app (registro y modificación). Se podrán consultar pero no crear ni editar desde la aplicación móvil
- Notificaciones push del sistema operativo
- Generación de informes PDF
- Sistema de códigos QR
- Versión iOS
- Panel web de administración
- Chat entre usuarios
- Integración con sistemas externos

## Justificación

El alcance permite completar un sistema funcional en el plazo establecido. Cubre el objetivo principal: digitalizar el control de mantenimientos que actualmente se gestiona con Excel.

Las funcionalidades excluidas no impiden que la aplicación sea completamente funcional para registrar, asignar y consultar mantenimientos.