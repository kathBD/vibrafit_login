package com.octopus.vibrafit.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.octopus.vibrafit.models.Usuario
import com.octopus.vibrafit.network.RetrofitClient
import kotlinx.coroutines.launch

class UsuarioViewModel : ViewModel() {
    val usuarios = MutableLiveData<List<Usuario>>()
    val operationSuccess = MutableLiveData<Boolean>()

    fun fetchUsuarios() {
        viewModelScope.launch {
            val response = RetrofitClient.instance.getUsuarios()
            if (response.isSuccessful) usuarios.value = response.body()
        }
    }

    fun eliminarUsuario(id: Long) {
        viewModelScope.launch {
            val response = RetrofitClient.instance.deleteUsuario(id)
            operationSuccess.value = response.isSuccessful
            if (response.isSuccessful) fetchUsuarios() // Recargar lista
        }
    }
}