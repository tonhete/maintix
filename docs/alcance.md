# Alcance del Proyecto Maintix

## Dentro del Alcance

### Backend (Completado)
- Autenticación JWT con roles (admin/operario)
- Control de acceso con [Authorize] en endpoints protegidos
- Swagger UI con autenticación JWT
- Alertas automáticas por horas de funcionamiento (tipos A/B/C)
- Generación masiva de mantenimientos desde alertas
- Asignación de operarios a mantenimientos específicos
- Gestión de estados (pendiente_asignacion → pendiente → finalizado)
- Checklist automático según tipo de máquina y mantenimiento
- Actualización de horas de funcionamiento de equipos
- Generación automática de mantenimientos al actualizar horas
- Reseteo automático de contadores al finalizar mantenimiento
- Items de mantenimiento con descripción detallada, herramientas e imágenes
- API REST completa con 40+ endpoints

### Mobile (Completado)
- Aplicación móvil Android para gestión de mantenimientos
- Sistema de login con JWT
- Dashboards diferenciados por rol (Admin/Técnico)
- Consulta de equipos y maquinaria
- Actualización de horas de funcionamiento
- Generación automática de mantenimientos al actualizar horas
- Visualización de alertas
- Mantenimientos asignados al operario
- Checklist interactivo de pasos a seguir
- Registro histórico de mantenimientos
- Consulta de histórico por equipo
- Pull-to-refresh en todas las listas
- Modo claro/oscuro adaptativo al sistema
- Pantalla de usuario con logout
- Navegación contextual (muestra botón de mantenimiento en curso)
- Contador de mantenimientos pendientes
- Paleta de colores corporativa (naranja #FF7A00)
- Material Design 3 con tema personalizado
- Imagen de fondo en login
- Modales con bordes y sombreado

## Fuera del Alcance

- Gestión completa de equipos desde la app (registro y modificación). Se pueden consultar y actualizar horas pero no crear ni editar desde la aplicación móvil
- Notificaciones push del sistema operativo
- Generación de informes PDF
- Sistema de códigos QR
- Versión iOS
- Panel web de administración
- Chat entre usuarios
- Integración con sistemas externos
- Despieces y documentación técnica por equipo
- Sistema de adjuntar fotografías en mantenimientos
- Internacionalización (ES/EN/EU)

## Justificación

El alcance permite completar un sistema funcional en el plazo establecido. Cubre el objetivo principal: digitalizar el control de mantenimientos que actualmente se gestiona con Excel.

Las funcionalidades excluidas no impiden que la aplicación sea completamente funcional para registrar, asignar y consultar mantenimientos. El sistema está preparado para uso en entorno real y cumple con todos los requisitos