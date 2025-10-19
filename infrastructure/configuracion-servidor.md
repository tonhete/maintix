# Configuración del Servidor

## Información General

- **Sistema Operativo:** Windows Server 2022 Standard Evaluation
- **Virtualización:** VirtualBox
- **Nombre del servidor:** SRV2022-NET-SQL
- **Grupo de trabajo:** WORKGROUP
- **Usuario:** Administrador
- **Fecha de instalación:** 27/09/2025

---

## Componentes Instalados

### IIS (Internet Information Services)
- **Versión:** 10.0
- **Fecha instalación:** 27/09/2025
- **Características instaladas:**
  - Contenido estático
  - Documento predeterminado
  - Errores HTTP
  - Registro HTTP
  - Herramientas de registro
  - Seguimiento
  - Compresión de contenido estático y dinámico
  - Filtrado de solicitudes
  - Extensibilidad .NET 4.8
  - Extensiones ISAPI
  - Filtros ISAPI
  - Consola de administración de IIS

- **Estado:** Operativo
- **Verificación:** Página de inicio accesible en http://localhost

### SQL Server 2022
- **Edición:** Developer Edition
- **Versión:** SQL Server 2022
- **Fecha instalación:** 28/09/2025
- **Tipo de instalación:** Personalizada
- **Características instaladas:**
  - Motor de base de datos (Database Engine Services)
  - Conectividad cliente

#### Configuración de la instancia
- **Instancia:** Predeterminada (MSSQLSERVER)
- **Modo de autenticación:** Mixto (Windows + SQL Server)
- **Intercalación:** Modern_Spanish_CI_AS
- **Directorio de instalación:** C:\Program Files\Microsoft SQL Server\
- **Directorio de datos:** C:\Program Files\Microsoft SQL Server\MSSQL16.MSSQLSERVER\MSSQL\Data
- **Directorio de backups:** C:\Program Files\Microsoft SQL Server\MSSQL16.MSSQLSERVER\MSSQL\Backup

#### TempDB
- **Archivos:** 4 archivos
- **Tamaño inicial:** 8 MB
- **Crecimiento:** 64 MB
- **Ubicación:** C:\Program Files\Microsoft SQL Server\MSSQL16.MSSQLSERVER\MSSQL\Data

#### Cuentas de servicio
- Motor de base de datos: NT Service\MSSQLSERVER
- SQL Server Agent: NT Service\SQLSERVERAGENT

---

## Acceso y Credenciales

**Nota de seguridad:** Las credenciales se gestionan de forma segura y no se incluyen en este documento público.

---

## Próximos Pasos

1. Despliegue de la API REST en ASP.NET Core en IIS
2. Configuración de la base de datos del proyecto
3. Configuración de permisos y usuarios de base de datos
4. Pruebas de conectividad desde la aplicación móvil