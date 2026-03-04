package com.octopus.vibrafit.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.octopus.vibrafit.models.Usuario
import com.octopus.vibrafit.network.RetrofitClient
import kotlinx.coroutines.launch
import retrofit2.Response

class UsuarioViewModel(application: Application) : AndroidViewModel(application) {

    // LiveData para la UI
    val usuarios = MutableLiveData<List<Usuario>>()
    val operationSuccess = MutableLiveData<Boolean>()
    val isLoading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()

    /**
     * Obtiene la lista de usuarios desde el servidor
     */
    fun fetchUsuarios() {
        viewModelScope.launch {
            isLoading.postValue(true)
            try {
                // Usamos postValue para asegurar que la UI se entere en cualquier hilo
                val response = RetrofitClient.getInstance(getApplication()).getUsuarios()

                if (response.isSuccessful) {
                    val lista = response.body() ?: emptyList()
                    usuarios.postValue(lista)
                    if (lista.isEmpty()) {
                        errorMessage.postValue("No hay usuarios registrados.")
                    }
                } else {
                    // Aquí capturamos el 401 que vimos en el Logcat
                    val errorMsg = when (response.code()) {
                        401 -> "Sesión expirada. Por favor, inicia sesión de nuevo."
                        403 -> "No tienes permisos para ver esta lista."
                        else -> "Error del servidor: ${response.code()}"
                    }
                    errorMessage.postValue(errorMsg)
                }
            } catch (e: Exception) {
                errorMessage.postValue("Fallo de conexión: Verifica tu internet o IP.")
                e.printStackTrace()
            } finally {
                isLoading.postValue(false)
            }
        }
    }

    /**
     * Elimina un usuario por su ID
     */
    fun eliminarUsuario(id: Long) {
        viewModelScope.launch {
            isLoading.postValue(true)
            try {
                val response = RetrofitClient.getInstance(getApplication()).deleteUsuario(id)

                if (response.isSuccessful) {
                    operationSuccess.postValue(true)
                    fetchUsuarios() // Recarga automática tras eliminar
                } else {
                    errorMessage.postValue("Error al eliminar: Código ${response.code()}")
                    operationSuccess.postValue(false)
                }
            } catch (e: Exception) {
                errorMessage.postValue("Error de red al eliminar usuario.")
                operationSuccess.postValue(false)
            } finally {
                isLoading.postValue(false)
            }
        }
    }
}