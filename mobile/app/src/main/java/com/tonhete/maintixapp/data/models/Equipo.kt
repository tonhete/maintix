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
    val descripcion: String,

    @SerializedName("proveedorId")
    val proveedorId: Int?
)

data class ActualizarHorasDto(
    @SerializedName("horasNuevas") val horasNuevas: Int
)

data class Proveedor(
    @SerializedName("id") val id: Int,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("tlf") val tlf: String?,
    @SerializedName("direccion") val direccion: String?
)