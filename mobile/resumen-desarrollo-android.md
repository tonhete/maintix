# Resumen Desarrollo Android - Aplicación Maintix

**Fecha:** 01/11/2025  
**Plataforma:** Android (Kotlin + Jetpack Compose)  
**IDE:** Android Studio

---

## Trabajo Realizado

### 1. Configuración Inicial del Proyecto

- Creación del proyecto Android con **Jetpack Compose**
- Configuración de dependencias básicas:
  - Navigation Compose (2.8.4)
  - ViewModel Compose (2.9.4)
- Estructura de carpetas organizada:
  ```
  maintixapp/
  ├── ui/
  │   ├── screens/      # Pantallas de la aplicación
  │   ├── navigation/   # Sistema de navegación
  │   └── components/   # Componentes reutilizables
  └── data/
      ├── models/       # Modelos de datos
      └── AppState.kt   # Estado global de la aplicación
  ```

---

### 2. Pantallas Implementadas - Flujo Operario

Se ha desarrollado el flujo completo de un operario desde el login hasta la finalización de un mantenimiento:

#### **LoginScreen.kt**
- Pantalla de autenticación con campos de usuario y contraseña
- Validación básica de entrada
- Navegación al dashboard tras el login

#### **DashboardScreen.kt**
- Pantalla principal con listado de mantenimientos pendientes
- Visualización en formato Card con información básica:
  - ID del mantenimiento
  - Nombre de la máquina
  - Tipo de mantenimiento
- Lista scrollable implementada con `LazyColumn`
- Navegación al detalle al seleccionar un mantenimiento

#### **DetalleMantenimientoScreen.kt**
- Visualización de información completa del mantenimiento seleccionado
- Secciones informativas:
  - Información General (máquina, tipo, fecha, responsable)
  - Localización (planta, zona, línea)
- Botón "INICIAR MANTENIMIENTO" para comenzar el checklist
- Botón de navegación para volver atrás

#### **ChecklistScreen.kt**
- Pantalla de ejecución del mantenimiento
- Progress indicator visual (barra de progreso + contador numérico)
- Lista de items del checklist con checkbox
- Cada item puede marcarse como completado
- Botón "FINALIZAR MANTENIMIENTO" (solo habilitado cuando todos los items están completados)
- Control de estado local de los items

---

### 3. Sistema de Navegación

#### **NavGraph.kt**
- Implementación completa del sistema de navegación con **Navigation Compose**
- Definición de rutas:
  - `login` → Pantalla inicial
  - `dashboard` → Listado de mantenimientos
  - `detalle/{mantenimientoId}` → Detalle con parámetro dinámico
  - `checklist/{mantenimientoId}` → Checklist con parámetro dinámico
  - `perfil` → Perfil del usuario
- Paso de parámetros entre pantallas mediante argumentos de navegación
- Control del back stack para navegación coherente

---

### 4. Bottom Navigation Bar

#### **MainScaffold (BottomNavBar.kt)**
- Barra de navegación inferior fija con 3 pestañas:
  1. **Inicio** - Acceso al dashboard de mantenimientos
  2. **En curso / Máquinas** - Navegación inteligente:
     - Si hay un mantenimiento en curso → va directamente al checklist activo
     - Si no hay mantenimiento en curso → va al dashboard
  3. **Perfil** - Acceso al perfil del usuario
- Badge con contador en el icono de Inicio (muestra número de mantenimientos pendientes)
- Indicador visual del tab activo
- Presente en todas las pantallas principales (Dashboard, Perfil)
- No presente en pantallas de flujo secundario (Detalle, Checklist cuando está en progreso)

---

### 5. Gestión de Estado

#### **AppState.kt**
- Clase para gestión del estado global de la aplicación
- Control del mantenimiento actualmente en curso:
  - `mantenimientoEnCurso`: guarda el ID del mantenimiento que se está ejecutando
  - `iniciarMantenimiento(id)`: marca un mantenimiento como en curso
  - `finalizarMantenimiento()`: limpia el estado al completar
- Permite la sincronización entre el Bottom Bar y las pantallas
- Estado observable que actualiza la UI automáticamente

---

### 6. Modelos de Datos Temporales

Se han creado modelos de datos básicos para el desarrollo del flujo:

```kotlin
// MantenimientoItem - Para el listado
data class MantenimientoItem(
    val id: String,
    val maquina: String,
    val tipo: String
)

// CheckItem - Para el checklist
data class CheckItem(
    val id: String,
    val nombre: String,
    val completado: Boolean
)
```

> **Nota:** Estos modelos son temporales y serán reemplazados por los modelos reales conectados al backend.

---

## Flujo de Usuario Implementado

```
1. LoginScreen
   ↓ (Pulsar "Entrar")
2. DashboardScreen [Bottom Bar visible]
   ↓ (Seleccionar mantenimiento)
3. DetalleMantenimientoScreen [Bottom Bar visible]
   ↓ (Pulsar "INICIAR MANTENIMIENTO")
4. ChecklistScreen [Bottom Bar visible]
   - El tab central del Bottom Bar cambia a "En curso"
   - Permite volver al checklist desde cualquier pantalla
   ↓ (Completar todos los items + "FINALIZAR")
5. Regreso a DashboardScreen
   - El mantenimiento desaparece de la lista
   - El tab central vuelve a "Máquinas"
```

---

## Tecnologías y Patrones Utilizados

### Jetpack Compose
- Sistema declarativo de UI
- Composables reutilizables
- Estado reactivo con `remember` y `mutableStateOf`

### Material Design 3
- Componentes siguiendo las guías de Material Design
- `NavigationBar`, `Card`, `Button`, `TextField`, etc.
- Theme personalizable

### Arquitectura
- Separación clara de responsabilidades:
  - `screens/` → Lógica de presentación
  - `navigation/` → Gestión de rutas
  - `components/` → Componentes compartidos
  - `data/` → Modelos y estado
- Patrón de callbacks para comunicación entre componentes

---

## Próximos Pasos

### Integración con Backend
- [ ] Configurar Retrofit para llamadas HTTP
- [ ] Crear modelos de datos que coincidan con la API REST
- [ ] Implementar repositorio para acceso a datos
- [ ] Conectar DashboardScreen con endpoint `/api/mantenimientos`
- [ ] Conectar ChecklistScreen con endpoints de actualización
- [ ] Implementar autenticación JWT

### Mejoras de UI
- [ ] Personalización del Theme (colores, tipografías)
- [ ] Animaciones de transición entre pantallas
- [ ] Estados de carga (loading indicators)
- [ ] Manejo de errores de red
- [ ] Validaciones de formularios

### Funcionalidades Adicionales
- [ ] Pantalla de Perfil de usuario
- [ ] Pantalla de Maquinaria
- [ ] Sistema de observaciones por item
- [ ] Captura y adjunto de fotografías
- [ ] Marcado de incidencias
- [ ] Persistencia local (Room Database)
- [ ] Notificaciones push

---

## Tiempo Estimado

**Trabajo realizado:** ~4-5 horas  
**Progreso:** MVP del flujo operario completado (sin integración backend)

---

## Notas Técnicas

- Todos los componentes están documentados con comentarios explicativos
- Se sigue el principio de composición sobre herencia
- Uso de `suspend functions` preparado para llamadas asíncronas futuras
- Navegación con argumentos implementada correctamente
- Estado compartido entre pantallas mediante callbacks y estado global

---

## Estructura de Archivos Creados

```
app/src/main/java/com/tonhete/maintixapp/
├── ui/
│   ├── screens/
│   │   ├── LoginScreen.kt
│   │   ├── DashboardScreen.kt
│   │   ├── DetalleMantenimientoScreen.kt
│   │   └── ChecklistScreen.kt
│   ├── navigation/
│   │   └── NavGraph.kt
│   └── components/
│       └── BottomNavBar.kt
├── data/
│   └── AppState.kt
└── MainActivity.kt
```

---

**Estado del proyecto:** Flujo operario básico funcional, listo para integración con backend.
