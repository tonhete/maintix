package com.tonhete.maintixapp.data.models

import com.google.gson.annotations.SerializedName

// ✅ Este modelo coincide con MantenimientoConChecklistDto.cs
data class ChecklistResponse(
    @SerializedName("mantenimientoId") val mantenimientoId: Int,
    @SerializedName("equipoId") val equipoId: Int,
    @SerializedName("numeroSerie") val numeroSerie: String?,
    @SerializedName("tipoMantenimiento") val tipoMantenimiento: String?,
    @SerializedName("fechaInicio") val fechaInicio: String,
    @SerializedName("estado") val estado: String,
    @SerializedName("items") val items: List<ChecklistItem>
)

// ✅ Este modelo coincide con ChecklistItemDto.cs
data class ChecklistItem(
    @SerializedName("checklistId") val checklistId: Int,
    @SerializedName("itemId") val itemId: Int,
    @SerializedName("descripcion") val descripcion: String,
    @SerializedName("descripcionDetallada") val descripcionDetallada: String?,  // NUEVO
    @SerializedName("herramientas") val herramientas: String?,                  // NUEVO
    @SerializedName("imagenUrl") val imagenUrl: String?,                        // NUEVO
    @SerializedName("orden") val orden: Int,
    @SerializedName("completado") val completado: Boolean,
    @SerializedName("observaciones") val observaciones: String?
)

// ✅ Modelo para actualizar UN item (PUT individual)
data class ActualizarItemDto(
    @SerializedName("mantenimientoId") val mantenimientoId: Int,
    @SerializedName("itemId") val itemId: Int,
    @SerializedName("completado") val completado: Boolean,
    @SerializedName("observaciones") val observaciones: String?
)

// ✅ Modelo para actualizar TODO el checklist (PUT masivo)
data class ActualizarChecklistDto(
    @SerializedName("items") val items: List<ActualizarItemChecklistDto>
)

data class ActualizarItemChecklistDto(
    @SerializedName("checklistId") val checklistId: Int,
    @SerializedName("completado") val completado: Boolean,
    @SerializedName("observaciones") val observaciones: String?
)

// ✅ Modelo para finalizar mantenimiento
data class FinalizarMantenimientoDto(
    @SerializedName("usuarioId") val usuarioId: Int,
    @SerializedName("incidencias") val incidencias: String?
)