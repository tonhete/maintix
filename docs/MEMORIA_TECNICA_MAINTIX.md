# MEMORIA TÉCNICA - MAINTIX

Sistema de Gestión de Mantenimiento Industrial

---

## 1. ARQUITECTURA DEL SISTEMA

### Visión general

El sistema sigue una arquitectura cliente-servidor de tres capas:

```
┌─────────────────────┐
│   App Android       │  ← Cliente móvil (Kotlin + Jetpack Compose)
│   (Jetpack Compose) │
└─────────┬───────────┘
          │ HTTP/REST + JWT
          ▼
┌─────────────────────┐
│   API REST          │  ← Servidor de aplicaciones (ASP.NET Core 8.0)
│   (ASP.NET Core)    │
└─────────┬───────────┘
          │ Entity Framework Core
          ▼
┌─────────────────────┐
│   SQL Server 2022   │  ← Base de datos relacional
└─────────────────────┘
```

### Patrón arquitectónico backend

El backend implementa el patrón Repository + Service Layer:

```
Controllers (15)
      │
      ▼
Services (lógica de negocio)
      │
      ▼
Repositories (acceso a datos)
      │
      ▼
DbContext (Entity Framework Core)
      │
      ▼
SQL Server 2022
```

---

## 2. STACK TECNOLÓGICO

### Backend

| Componente | Tecnología | Versión |
|------------|------------|---------|
| Framework | ASP.NET Core | 8.0 |
| ORM | Entity Framework Core | 8.0 |
| Base de datos | SQL Server | 2022 Developer |
| Autenticación | JWT Bearer | - |
| Documentación API | Swagger/OpenAPI | - |
| Servidor web | IIS | 10.0 |
| Sistema operativo | Windows Server | 2025 |

### Frontend móvil

| Componente | Tecnología | Versión |
|------------|------------|---------|
| Lenguaje | Kotlin | 1.9+ |
| UI Framework | Jetpack Compose | - |
| Design System | Material Design 3 | - |
| Cliente HTTP | Retrofit | 2.9.0 |
| Serialización JSON | Gson | 2.10.1 |
| Carga de imágenes | Coil | 2.5.0 |
| Navegación | Navigation Compose | 2.8.4 |

### Infraestructura

| Componente | Proveedor | Especificaciones |
|------------|-----------|------------------|
| VPS | OVH Cloud | 4 vCores, 8GB RAM, 75GB SSD NVMe |
| Sistema operativo | Windows Server 2025 | - |
| Servidor web | IIS 10.0 | - |
| Base de datos | SQL Server 2022 | Developer Edition |

---

## 3. BASE DE DATOS

### Modelo entidad-relación

La base de datos consta de 11 tablas relacionadas:

**Tablas principales:**
- tipo_usuario: Roles del sistema (administrador, técnico)
- usuarios: Credenciales y datos de usuarios
- proveedores: Información de proveedores de equipos
- tipo_maquinaria: Categorías de maquinaria con umbrales de mantenimiento
- equipo: Equipos/máquinas con horas de funcionamiento
- tipos_mantenimiento: Clasificación A, B, C según complejidad
- mantenimientos: Registro de mantenimientos programados
- items_mantenimiento: Tareas predefinidas por tipo de máquina y mantenimiento
- checklist_mantenimiento: Estado de cada tarea en un mantenimiento concreto
- historico: Registro histórico de mantenimientos finalizados
- alertas: Alertas generadas por superación de umbrales

### Relaciones principales

- Un equipo pertenece a un tipo_maquinaria
- Un tipo_maquinaria tiene un proveedor asociado
- Un mantenimiento pertenece a un equipo y tiene un tipo_mantenimiento
- Un mantenimiento tiene múltiples items en su checklist
- Un usuario puede tener múltiples mantenimientos asignados

### Diagrama ER

El diagrama entidad-relación se encuentra en: `/database/diagrams/`

---

## 4. API REST

### Endpoints principales

La API expone 15 controllers con operaciones CRUD completas:

**Autenticación:**
- POST /api/Auth/login → Autenticación y generación de token JWT

**Gestión de usuarios:**
- GET/POST/PUT/DELETE /api/Usuario
- GET/POST/PUT/DELETE /api/TipoUsuario

**Gestión de equipos:**
- GET/POST/PUT/DELETE /api/Equipo
- GET/POST/PUT/DELETE /api/TipoMaquinaria
- GET/POST/PUT/DELETE /api/Proveedor

**Gestión de mantenimientos:**
- GET/POST/PUT/DELETE /api/Mantenimiento
- GET/POST/PUT/DELETE /api/TiposMantenimiento
- GET/POST/PUT/DELETE /api/ItemsMantenimiento
- GET/POST/PUT/DELETE /api/ChecklistMantenimiento

**Servicios de negocio:**
- GET /api/MantenimientoService/{id}/checklist → Obtener checklist completo
- POST /api/MantenimientoService/{id}/finalizar → Finalizar mantenimiento
- GET /api/Alerta → Consultar alertas activas
- POST /api/Alerta/generar-mantenimientos → Generar mantenimientos desde alertas

**Histórico:**
- GET /api/Historico
- GET /api/Historico/equipo/{equipoId} → Histórico por equipo

### Autenticación JWT

Todos los endpoints (excepto login) requieren token JWT válido en la cabecera:

```
Authorization: Bearer <token>
```

El token incluye:
- usuarioId
- tipoUsuarioId (rol)
- email
- Expiración: 24 horas

### Documentación interactiva

La API incluye Swagger UI accesible en: `http://<servidor>:5000/swagger`

---

## 5. APLICACIÓN ANDROID

## Estructura del Proyecto
```
maintix/
├── README.md
├── docs/
│   ├── alcance.md
│   ├── planificacion.md
│   ├── presupuesto.md
│   ├── DIAGRAMA_BBDD.png
│   ├── MANUAL_USUARIO_MAINTIX.md
│   └── MEMORIA_TECNICA_MAINTIX.md
├── database/
│   ├── diagrams/
│   │   └── esquema_inicial.png
│   └── scripts/
│       └── 01-crear-tablas.sql
├── Maintix_API/
│   ├── Program.cs
│   ├── appsettings.json
│   ├── Maintix_API.csproj
│   ├── Controllers/
│   │   ├── AuthController.cs
│   │   ├── EquipoController.cs
│   │   ├── MantenimientoController.cs
│   │   ├── MantenimientoServiceController.cs
│   │   ├── UsuarioController.cs
│   │   └── ... (15 controllers)
│   ├── Data/
│   │   └── MaintixDbContext.cs
│   ├── DTOs/
│   │   ├── LoginDto.cs
│   │   ├── AlertaEquipoDto.cs
│   │   └── ... (9 DTOs)
│   ├── Models/
│   │   ├── Usuario.cs
│   │   ├── Equipo.cs
│   │   ├── Mantenimiento.cs
│   │   └── ... (11 modelos)
│   ├── Repositories/
│   │   └── ... (interfaces e implementaciones)
│   └── Services/
│       ├── AuthService.cs
│       └── MantenimientoService.cs
└── mobile/
    ├── build.gradle.kts
    ├── settings.gradle.kts
    └── app/
        └── src/main/
            ├── AndroidManifest.xml
            ├── java/com/tonhete/maintixapp/
            │   ├── MainActivity.kt
            │   ├── data/
            │   │   ├── ApiService.kt
            │   │   ├── AppState.kt
            │   │   ├── RetrofitClient.kt
            │   │   └── models/
            │   │       ├── Auth.kt
            │   │       ├── CheckListMantenimiento.kt
            │   │       ├── Equipo.kt
            │   │       ├── Historico.kt
            │   │       ├── ItemMantenimento.kt
            │   │       ├── Mantenimento.kt
            │   │       └── Usuario.kt
            │   └── ui/
            │       ├── components/
            │       │   ├── BottomNavBar.kt
            │       │   ├── BottomNavBarAdmin.kt
            │       │   ├── ItemDetalleModal.kt
            │       │   └── MaintixButton.kt
            │       ├── navigation/
            │       │   └── NavGraph.kt
            │       ├── screens/
            │       │   ├── ChecklistScreen.kt
            │       │   ├── DashboardAdminScreen.kt
            │       │   ├── DashboardScreen.kt
            │       │   ├── DetalleEquipoScreen.kt
            │       │   ├── DetalleMantenimientoScreen.kt
            │       │   ├── LoginScreen.kt
            │       │   ├── MaquinasScreen.kt
            │       │   └── UsuarioScreen.kt
            │       └── theme/
            │           ├── Color.kt
            │           ├── Theme.kt
            │           └── Type.kt
            └── res/
                ├── drawable/
                ├── font/
                ├── mipmap-hdpi/
                ├── mipmap-mdpi/
                ├── mipmap-xhdpi/
                ├── mipmap-xxhdpi/
                ├── mipmap-xxxhdpi/
                ├── values/
                └── xml/
```

### Navegación

La aplicación implementa navegación diferenciada por rol:

**Rol Administrador:**
- Dashboard → Equipos, Mantenimientos, Alertas, Usuarios
- Bottom Navigation Bar

**Rol Técnico:**
- Dashboard → Mis Mantenimientos → Checklist
- Bottom Navigation Bar
- Botón contextual "En curso" cuando hay mantenimiento activo

### Gestión de estado

- Token JWT almacenado en SharedPreferences
- Estado global mediante objeto singleton AppState
- Estados de UI con mutableStateOf de Compose

### Temas

La aplicación soporta modo claro y oscuro con tema personalizado:
- Color primario: #FF7A00 
- Adaptación automática al tema del sistema

---

## 6. CONFIGURACIÓN DEL SERVIDOR

### IIS

La API está desplegada en IIS 10.0 con la siguiente configuración:
- Application Pool: .NET CLR v4.0, Integrated Pipeline
- Puerto: 5000
- Archivos estáticos habilitados para servir imágenes

### SQL Server

- Autenticación mixta (Windows + SQL Server)
- Puerto TCP: 1433
- Firewall configurado para acceso remoto

### Firewall

Puertos abiertos:
- 5000: API REST
- 1433: SQL Server
- 3389: RDP (administración)

---

## 7. SEGURIDAD

### Autenticación

- JWT con firma HMAC-SHA256
- Expiración de tokens: 24 horas
- Refresh token no implementado (fuera de alcance)

### Autorización

- Control de acceso basado en roles (RBAC)
- Atributo [Authorize] en todos los endpoints protegidos
- Roles: Administrador (1), Técnico (2)

### Comunicación

- HTTP en desarrollo (IP privada del VPS)
- HTTPS recomendado para producción

### Almacenamiento

- Contraseñas hasheadas con BCrypt
- Token JWT en SharedPreferences (sin cifrado)

---

## 8. FLUJOS DE TRABAJO

### Flujo de mantenimiento preventivo

1. El sistema detecta que un equipo supera el umbral de horas configurado
2. Se genera una alerta automática
3. El administrador revisa alertas y genera mantenimientos
4. El administrador asigna un técnico al mantenimiento
5. El técnico ve el mantenimiento en su dashboard
6. El técnico inicia el mantenimiento y completa el checklist
7. El técnico finaliza el mantenimiento con observaciones
8. El sistema resetea contadores del equipo
9. Se registra en el histórico

### Estados de un mantenimiento

```
pendiente_asignacion → pendiente → finalizado
```

---

## 9. REPOSITORIO

**URL:** https://github.com/tonhete/maintix

### Estructura del repositorio

```
maintix/
├── backend/
│   └── MaintixAPI/          # Proyecto ASP.NET Core
├── mobile/
│   └── MaintixApp/          # Proyecto Android Studio
├── database/
│   ├── scripts/             # Scripts SQL
│   └── diagrams/            # Diagramas ER
├── docs/
│   ├── README.md
│   ├── CHANGELOG.md
│   ├── planificacion.md
│   ├── alcance.md
│   └── presupuesto.md
└── README.md
```

---

## 10. DESPLIEGUE

### Requisitos para despliegue

**Servidor:**
- Windows Server 2019 o superior
- IIS 10.0 con módulo ASP.NET Core
- SQL Server 2019 o superior
- .NET 8.0 Runtime

**Cliente Android:**
- Android 7.0 (API 24) o superior
- Conexión a internet

### Pasos de despliegue backend

1. Publicar proyecto desde Visual Studio (Release)
2. Copiar archivos a directorio del sitio IIS
3. Configurar Application Pool
4. Configurar cadena de conexión en appsettings.json
5. Reiniciar sitio IIS

### Distribución Android

1. Generar APK firmado desde Android Studio
2. Distribuir manualmente o mediante tienda interna

---

**FIN DE LA MEMORIA TÉCNICA**
