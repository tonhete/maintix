package com.tonhete.maintixapp.data

import com.tonhete.maintixapp.data.models.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    // ========== LOGIN ==========
    @POST("api/Auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    // ========== USUARIO ==========
    @GET("api/Usuario/{id}")
    suspend fun getUsuario(@Path("id") id: Int): Response<Usuario>

    // Obtener todos los usuarios (para filtrar técnicos)
    @GET("api/Usuario")
    suspend fun getUsuarios(): Response<List<Usuario>>

    // ========== MANTENIMIENTOS ==========
    @GET("api/Mantenimiento")
    suspend fun getMantenimientos(): Response<List<Mantenimiento>>

    @GET("api/Mantenimiento/tecnico/{tecnicoId}")
    suspend fun getMantenimientosPorTecnico(
        @Path("tecnicoId") tecnicoId: Int
    ): Response<List<Mantenimiento>>

    @GET("api/Mantenimiento/{id}")
    suspend fun getMantenimiento(@Path("id") id: Int): Response<Mantenimiento>

    @PUT("api/Mantenimiento/{id}")
    suspend fun updateMantenimiento(
        @Path("id") id: Int,
        @Body mantenimiento: Mantenimiento
    ): Response<Mantenimiento>

    // Asignar operario a mantenimiento
    @PUT("api/MantenimientoService/{mantenimientoId}/asignar-operario")
    suspend fun asignarOperario(
        @Path("mantenimientoId") mantenimientoId: Int,
        @Body body: AsignarOperarioDto
    ): Response<Unit>

    // ========== EQUIPOS ==========
    @GET("api/Equipo/{id}")
    suspend fun getEquipo(@Path("id") id: Int): Response<Equipo>

    // Obtener todos los equipos
    @GET("api/Equipo")
    suspend fun getEquipos(): Response<List<Equipo>>

    @POST("api/MantenimientoService/equipo/{equipoId}/actualizar-horas")
    suspend fun actualizarHorasEquipo(
        @Path("equipoId") equipoId: Int,
        @Body dto: ActualizarHorasDto
    ): Response<Unit>

    // ========== CHECKLIST ==========
    @GET("api/MantenimientoService/{mantenimientoId}/checklist")
    suspend fun getChecklistMantenimiento(
        @Path("mantenimientoId") mantenimientoId: Int
    ): Response<ChecklistResponse>

    // Actualizar checklist completo (todos los items a la vez)
    @PUT("api/MantenimientoService/{mantenimientoId}/actualizar-checklist")
    suspend fun actualizarChecklist(
        @Path("mantenimientoId") mantenimientoId: Int,
        @Body dto: ActualizarChecklistDto
    ): Response<Unit>

    // Actualizar UN item individual del checklist
    @PUT("api/ChecklistMantenimiento/{id}")
    suspend fun updateChecklistItem(
        @Path("id") id: Int,
        @Body item: ChecklistItem
    ): Response<ChecklistItem>

    // ========== FINALIZAR MANTENIMIENTO ==========
    @POST("api/MantenimientoService/{mantenimientoId}/finalizar")
    suspend fun finalizarMantenimiento(
        @Path("mantenimientoId") mantenimientoId: Int,
        @Body dto: FinalizarMantenimientoDto
    ): Response<Unit>

    // ========== ITEMS MANTENIMIENTO (catálogo) ==========
    @GET("api/ItemMantenimiento")
    suspend fun getItemsMantenimiento(): Response<List<ItemMantenimiento>>

    // Histórico
    @GET("api/Historico/equipo/{equipoId}")
    suspend fun getHistoricoEquipo(@Path("equipoId") equipoId: Int): Response<List<Historico>>

    // Proveedores
    @GET("api/Proveedor/{id}")
    suspend fun getProveedor(@Path("id") id: Int): Response<Proveedor>

    @GET("api/Proveedor")
    suspend fun getProveedores(): Response<List<Proveedor>>



}