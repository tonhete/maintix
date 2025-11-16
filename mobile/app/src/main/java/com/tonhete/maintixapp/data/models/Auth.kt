package com.tonhete.maintixapp.data.models

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)

data class LoginResponse(
    @SerializedName("token") val token: String,
    @SerializedName("usuarioId") val usuarioId: Int,
    @SerializedName("tipoUsuarioId") val tipoUsuarioId: Int,
    @SerializedName("email") val email: String
)