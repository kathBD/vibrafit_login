package com.octopus.vibrafit.models

data class Usuario(
    val usuarioId: Long,
    val correo: String,
    val nombre: String,
    val telefono: String?,
    val sexo: String?,
    val peso: Double?,
    val estatura: Double?,
    val activo: Boolean,
    val especialidad: String?,
    val horarioInicio: String?,
    val horarioFin: String?,
    val fechaNacimiento: String?,
    val rol: String,
    val objetivo: String?,
    val estadoFisico: String?
)

