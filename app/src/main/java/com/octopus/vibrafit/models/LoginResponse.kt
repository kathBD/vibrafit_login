package com.octopus.vibrafit.models

data class LoginResponse(
    val usuarioId: Long,
    val correo: String,
    val nombre: String,
    val rol: String,
    val activo: Boolean,
    val token: String? = null
)
