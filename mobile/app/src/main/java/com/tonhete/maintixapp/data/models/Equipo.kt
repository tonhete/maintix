package com.tonhete.maintixapp.data.models

import com.google.gson.annotations.SerializedName

data class Equipo(
    @SerializedName("id")
    val id: Int,

    @SerializedName("tipoMaquinariaId")
    val tipoMaquinariaId: Int,

    @SerializedName("fechaFabricacion")
    val fechaFabricacion: String?,

    @SerializedName("numeroSerie")
    val numeroSerie: String?,

    @SerializedName("horasActuales")
    val horasActuales: Int?,

    @SerializedName("tipoMaquinaria")
    val tipoMaquinaria: TipoMaquinaria?
)

data class TipoMaquinaria(
    @SerializedName("id")
    val id: Int,

    @SerializedName("descripcion")
    val descripcion: String
)