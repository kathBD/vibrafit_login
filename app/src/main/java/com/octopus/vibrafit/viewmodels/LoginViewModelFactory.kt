package com.octopus.vibrafit.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class LoginViewModelFactory(private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            // Le pasamos la aplicación al ViewModel para que Retrofit funcione
            return LoginViewModel(application) as T
        }
        throw IllegalArgumentException("Clase ViewModel desconocida")
    }
}