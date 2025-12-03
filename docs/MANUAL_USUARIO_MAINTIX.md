# MANUAL DE USUARIO - MAINTIX
## Sistema de Gestión de Mantenimiento Industrial

---

## 1. INTRODUCCIÓN

Maintix es una aplicación móvil Android que permite gestionar el mantenimiento preventivo de equipos industriales basándose en horas de funcionamiento. Permite asignar tareas a técnicos, realizar mantenimientos con checklist interactivo y consultar históricos.

---

## 2. PRIMEROS PASOS

### 2.1 Acceso al Sistema
1. Abre la aplicación Maintix en tu dispositivo Android
2. Introduce tu **email** y **contraseña**
3. Pulsa **"Iniciar Sesión"**

**Roles disponibles:**
- **Administrador** (tipo 1): Gestión completa del sistema
- **Técnico** (tipo 2): Ejecución de mantenimientos asignados

---

## 3. PANEL DE TÉCNICO

### 3.1 Dashboard - Mis Mantenimientos
Al entrar como técnico verás:
- **Lista de mantenimientos** asignados a ti
- Agrupados por estado: **Pendientes**, **En Progreso**, **Finalizados**
- **Badge naranja** con el número de mantenimientos pendientes

**Colores de las cards:**
- **Azul**: Sin empezar
- **Naranja**: En progreso  
- **Verde**: Finalizado

### 3.2 Actualizar Lista
**Desliza hacia abajo** en cualquier momento para actualizar los datos desde el servidor (pull-to-refresh).

### 3.3 Iniciar un Mantenimiento
1. Pulsa sobre una card de mantenimiento pendiente
2. Se muestra el **detalle del mantenimiento**:
   - Nombre del equipo
   - Tipo de mantenimiento
   - Estado actual
   - Porcentaje de progreso
3. Pulsa **"Ver Checklist"**

### 3.4 Trabajar con el Checklist
En la pantalla del checklist podrás:

**Ver items:**
- Cada item muestra su descripción corta
- Items completados aparecen marcados con ✓

**Marcar items como completados:**
- Pulsa el **checkbox** a la izquierda de cada item
- El estado se guarda temporalmente

**Ver detalles de un item:**
1. Pulsa sobre el item
2. Se abre un **modal** con:
   - Descripción detallada
   - Herramientas necesarias
   - Imagen de referencia (si está disponible)
3. Pulsa **"Cerrar"** para volver

**Guardar progreso:**
- Pulsa **"Guardar Progreso"** en la parte inferior
- Se guarda tu avance sin finalizar el mantenimiento
- Puedes cerrar la aplicación y retomar después

**Retomar mantenimiento:**
- Si tienes un mantenimiento en curso, aparece un botón **"En curso"** en la navegación inferior
- Pulsa para volver directamente al checklist

**Finalizar mantenimiento:**
1. Marca todos los items necesarios
2. Pulsa **"Finalizar Mantenimiento"**
3. Confirma en el diálogo
4. El sistema:
   - Cambia el estado a **Finalizado**
   - Resetea automáticamente los contadores del equipo
   - Registra el mantenimiento en el histórico

### 3.5 Navegación Inferior
- **Dashboard**: Ver todos tus mantenimientos
- **En curso** (aparece dinámicamente): Volver al mantenimiento activo
- **Usuario**: Ver perfil y cerrar sesión

---

## 4. PANEL DE ADMINISTRADOR

### 4.1 Dashboard - Todos los Mantenimientos
Al entrar como administrador verás:
- **Sin asignar**: Mantenimientos creados sin técnico
- **Pendientes**: Mantenimientos asignados pero no finalizados
- **Finalizados**: Mantenimientos completados

Cada card muestra:
- Nombre del equipo
- Tipo de mantenimiento
- Estado
- Técnico asignado (si lo tiene)
- **Porcentaje de progreso** del checklist

### 4.2 Asignar Técnico a un Mantenimiento
1. Pulsa sobre un mantenimiento **sin asignar**
2. En el detalle, pulsa **"Asignar Técnico"**
3. Se abre un **menú desplegable** con la lista de técnicos
4. Selecciona el técnico
5. Pulsa **"Asignar"**
6. El mantenimiento pasa a **Pendientes**

### 4.3 Ver Checklist (Solo lectura)
1. Pulsa sobre cualquier mantenimiento
2. Pulsa **"Ver Checklist"**
3. Verás el checklist en modo **solo lectura**:
   - Items marcados y pendientes
   - Progreso del técnico
   - NO puedes modificar nada

### 4.4 Gestión de Equipos
**Acceso:** Pulsa el icono **"Máquinas"** en la navegación inferior

**Lista de equipos:**
- Muestra todos los equipos del sistema
- Nombre, modelo, horas actuales

**Ver detalle de un equipo:**
1. Pulsa sobre una card de equipo
2. Se muestra:
   - Información completa del equipo
   - Horas de funcionamiento actuales
   - Botón para actualizar horas
   - **Histórico de mantenimientos** (expandible)
   - **Información del proveedor** (expandible)

**Actualizar horas de funcionamiento:**
1. En el detalle del equipo, pulsa **"Actualizar Horas"**
2. Introduce las **nuevas horas** en el modal
3. Pulsa **"Actualizar"**
4. El sistema:
   - Actualiza el contador
   - **Genera automáticamente** nuevos mantenimientos si se superan los umbrales (A, B, C)

**Ver histórico:**
- Pulsa en **"Histórico de mantenimientos"**
- Se despliega la lista con:
  - Fecha de realización
  - Tipo de mantenimiento
  - Técnico que lo realizó
  - Observaciones

**Ver proveedor:**
- Pulsa en **"Información del proveedor"**
- Se muestra:
  - Nombre
  - Contacto
  - Email

### 4.5 Navegación Inferior (Admin)
- **Dashboard**: Ver todos los mantenimientos
- **Máquinas**: Gestión de equipos
- **Usuario**: Ver perfil y cerrar sesión

---

## 5. FUNCIONES COMUNES

### 5.1 Actualizar Datos
**En cualquier pantalla con listas:**
- Desliza hacia abajo (pull-to-refresh)
- Los datos se recargan desde el servidor

### 5.2 Modo Claro/Oscuro
- La aplicación **adapta automáticamente** su tema según la configuración del sistema Android
- No requiere configuración manual

### 5.3 Cerrar Sesión
1. Pulsa el icono **"Usuario"** en la navegación inferior
2. Verás tu información de usuario
3. Pulsa **"Cerrar Sesión"**
4. Vuelves a la pantalla de login

---

## 6. GENERACIÓN AUTOMÁTICA DE MANTENIMIENTOS

### ¿Cuándo se crean automáticamente?
El sistema genera mantenimientos automáticamente cuando un administrador **actualiza las horas de funcionamiento** de un equipo y se superan los umbrales configurados:

- **Tipo A**: Cada X horas (mantenimiento básico)
- **Tipo B**: Cada Y horas (mantenimiento medio)
- **Tipo C**: Cada Z horas (mantenimiento completo)

Estos mantenimientos aparecen en **"Sin asignar"** hasta que un administrador los asigne a un técnico.

---

## 7. PREGUNTAS FRECUENTES

**P: ¿Puedo retomar un mantenimiento después de cerrar la aplicación?**  
R: Sí, si guardaste el progreso, aparecerá el botón "En curso" en la navegación para volver directamente.

**P: ¿Qué pasa si finalizo un mantenimiento por error?**  
R: No se puede deshacer. Contacta con el administrador para revisar el histórico.

**P: ¿Puedo crear equipos desde la aplicación?**  
R: No, la gestión completa de equipos (crear/editar) está fuera del alcance de la aplicación móvil. Solo puedes consultarlos y actualizar sus horas.

**P: ¿Puedo adjuntar fotos en los mantenimientos?**  
R: No, esta funcionalidad está fuera del alcance actual del proyecto.

**P: ¿Recibiré notificaciones de nuevos mantenimientos?**  
R: No, las notificaciones push están fuera del alcance. Debes abrir la aplicación y actualizar la lista.

**P: ¿Por qué no aparecen algunos campos o funcionalidades?**  
R: Algunas funcionalidades como despieces técnicos, generación de informes PDF, o sistemas QR están planificadas para versiones futuras.

---

## 8. CONTACTO Y SOPORTE

**Desarrollador:** Antonio Martín  
**Institución:** I.E.S. Fernando Wirtz Suárez - A Coruña  
**Proyecto académico:** Desarrollo de Aplicaciones Multiplataforma  

Para reportar problemas o sugerencias, contacta con el administrador del sistema.

---

**Versión del manual:** 1.0  
**Fecha:** Diciembre 2025
