package com.tonhete.maintixapp.data

import android.content.Context
import android.content.SharedPreferences
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "http://57.131.13.44:5000/"
    private const val PREFS_NAME = "MaintixPrefs"
    private const val KEY_TOKEN = "jwt_token"

    private lateinit var sharedPreferences: SharedPreferences

    // Llamar esto desde Application o MainActivity.onCreate()
    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    // Guardar token después del login
    fun saveToken(token: String) {
        sharedPreferences.edit().putString(KEY_TOKEN, token).apply()
    }

    // Obtener token
    fun getToken(): String? {
        return sharedPreferences.getString(KEY_TOKEN, null)
    }

    // Borrar token (logout)
    fun clearToken() {
        sharedPreferences.edit().remove(KEY_TOKEN).apply()
    }

    // Interceptor que añade el token a todas las peticiones
    private val authInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val token = getToken()

        val newRequest = if (token != null) {
            originalRequest.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else {
            originalRequest
        }

        chain.proceed(newRequest)
    }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)  // Primero el auth
        .addInterceptor(loggingInterceptor)  // Luego el logging
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}