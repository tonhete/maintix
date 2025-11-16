package com.tonhete.maintixapp.data.models
import com.google.gson.annotations.SerializedName

data class Mantenimiento(
    @SerializedName("id") val id: Int,
    @SerializedName("equipoId") val equipoId: Int,
    @SerializedName("tipoMantenimientoId") val tipoMantenimientoId: Int,
    @SerializedName("operarioAsignadoId") val operarioAsignadoId: Int?,
    @SerializedName("fechaInicio") val fechaInicio: String?,
    @SerializedName("fechaFin") val fechaFin: String?,
    @SerializedName("estado") val estado: String,
    @SerializedName("progresoChecklist") val progresoChecklist: Float


)