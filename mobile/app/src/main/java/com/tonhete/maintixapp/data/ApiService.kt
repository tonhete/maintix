package com.tonhete.maintixapp.data

import com.tonhete.maintixapp.data.models.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    // ========== MANTENIMIENTOS ==========
    @GET("api/Mantenimiento")
    suspend fun getMantenimientos(): Response<List<Mantenimiento>>

    @GET("api/Mantenimiento/{id}")
    suspend fun getMantenimiento(@Path("id") id: Int): Response<Mantenimiento>

    @PUT("api/Mantenimiento/{id}")
    suspend fun updateMantenimiento(
        @Path("id") id: Int,
        @Body mantenimiento: Mantenimiento
    ): Response<Mantenimiento>

    // ========== EQUIPOS ==========
    @GET("api/Equipo/{id}")
    suspend fun getEquipo(@Path("id") id: Int): Response<Equipo>

    // ========== CHECKLIST - ENDPOINT CORRECTO ==========
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
}