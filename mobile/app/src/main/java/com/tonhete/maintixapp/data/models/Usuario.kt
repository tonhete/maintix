package com.tonhete.maintixapp.data.models
import com.google.gson.annotations.SerializedName

data class Usuario(
    @SerializedName("id") val id: Int,
    @SerializedName("tipoUsuarioId") val tipoUsuarioId: Int,
    @SerializedName("email") val email: String,
    @SerializedName("passwd") val passwd: String?
)