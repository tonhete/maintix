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
    @SerializedName("progresoChecklist") val progresoChecklist: Float,
    @SerializedName("numeroSerie") val numeroSerie: String?,
    @SerializedName("maquinaNombre") val maquinaNombre: String?,
    @SerializedName("tipoMantenimiento") val tipoMantenimiento: TipoMantenimientoDto?,
    @SerializedName("tipoMaquinariaInfo") val tipoMaquinariaInfo: tipoMaquinariaInfoDto?

)

data class TipoMantenimientoDto(
    @SerializedName("id") val id: Int,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("descripcion") val descripcion: String?
)

data class AsignarOperarioDto(
    @SerializedName("operarioId") val operarioId: Int
)

data class tipoMaquinariaInfoDto(
    @SerializedName("descripcion") val descripcion: String?
)