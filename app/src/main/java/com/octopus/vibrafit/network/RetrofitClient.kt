package com.octopus.vibrafit.network

import android.content.Context
import com.octopus.vibrafit.utils.SessionManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "http://10.0.2.2:8080/" // Cambia a tu IP si usas celular físico
    private var retrofit: Retrofit? = null

    fun getInstance(context: Context): ApiService {
        // Interceptor para logs (ver qué pasa en el Logcat)
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        // Interceptor de Seguridad: Aquí pegamos el Token
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor { chain ->
                val sessionManager = SessionManager(context)
                val token = sessionManager.getAuthToken()

                val requestBuilder = chain.request().newBuilder()

                // Si el token existe en EncryptedSharedPreferences, lo enviamos
                if (!token.isNullOrEmpty()) {
                    requestBuilder.addHeader("Authorization", "Bearer $token")
                }

                chain.proceed(requestBuilder.build())
            }
            .build()

        // Creamos la instancia de Retrofit
        val newRetrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return newRetrofit.create(ApiService::class.java)
    }
}