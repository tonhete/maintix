package com.tonhete.maintixapp.data.models

import com.google.gson.annotations.SerializedName

data class Historico(
    @SerializedName("id") val id: Int,
    @SerializedName("equipoId") val equipoId: Int,
    @SerializedName("horasMaquina") val horasMaquina: Int,
    @SerializedName("clase") val clase: String,
    @SerializedName("operario") val operario: String,
    @SerializedName("incidencias") val incidencias: String?,
    @SerializedName("finalizado") val finalizado: Boolean,
    @SerializedName("fechaMantenimiento") val fechaMantenimiento: String,
    @SerializedName("fechaFinalizacion") val fechaFinalizacion: String,
    @SerializedName("equipoNumeroSerie") val equipoNumeroSerie: String,
    @SerializedName("tipoMantenimientoNombre") val tipoMantenimientoNombre: String
)