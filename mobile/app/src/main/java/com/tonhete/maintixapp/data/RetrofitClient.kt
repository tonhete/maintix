package com.tonhete.maintixapp.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Singleton para configurar Retrofit
object RetrofitClient {

    // CAMBIA ESTA IP por la IP de tu VPS
    private const val BASE_URL = "http://57.131.13.44:5000/"

    // Logging interceptor para ver peticiones en Logcat
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Cliente HTTP
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    // Instancia de Retrofit
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Instancia del servicio API
    val apiService: ApiService = retrofit.create(ApiService::class.java)
}