package com.octopus.vibrafit.network

import com.octopus.vibrafit.models.LoginRequest
import com.octopus.vibrafit.models.LoginResponse
import com.octopus.vibrafit.models.Usuario
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("api/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("api/auth/logout")
    suspend fun logout(): Response<Void>

    // Leer todos los usuarios
    @GET("api/usuarios")
    suspend fun getUsuarios(): Response<List<Usuario>>

    // Crear usuario
    @POST("api/usuarios")
    suspend fun createUsuario(@Body usuario: Usuario): Response<Usuario>

    // Actualizar usuario
    @PUT("api/usuarios/{id}")
    suspend fun updateUsuario(@Path("id") id: Long, @Body usuario: Usuario): Response<Usuario>

    // Eliminar usuario
    @DELETE("api/usuarios/{id}")
    suspend fun deleteUsuario(@Path("id") id: Long): Response<Void>
}

