package com.tonhete.maintixapp.data.models
import com.google.gson.annotations.SerializedName

// Modelo que coincide con tu API
data class Mantenimiento(
    @SerializedName("id")
    val id: Int,

    @SerializedName("equipoId")
    val equipoId: Int,

    @SerializedName("tipoMantenimientoId")
    val tipoMantenimientoId: Int,

    @SerializedName("fechaInicio")
    val fechaInicio: String?,

    @SerializedName("fechaFin")
    val fechaFin: String?,

    @SerializedName("estado")
    val estado: String
)