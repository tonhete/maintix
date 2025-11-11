package com.tonhete.maintixapp.data.models

import com.google.gson.annotations.SerializedName

data class ItemMantenimiento(
    @SerializedName("id")
    val id: Int,

    @SerializedName("tipoMaquinaId")
    val tipoMaquinaId: Int,

    @SerializedName("tipoMantenimientoId")
    val tipoMantenimientoId: Int,

    @SerializedName("descripcion")
    val descripcion: String,

    @SerializedName("orden")
    val orden: Int
)