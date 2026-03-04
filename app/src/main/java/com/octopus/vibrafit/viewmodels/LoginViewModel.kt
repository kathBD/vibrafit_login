package com.octopus.vibrafit.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.octopus.vibrafit.models.LoginRequest
import com.octopus.vibrafit.network.RetrofitClient
import com.octopus.vibrafit.utils.SessionManager
import kotlinx.coroutines.launch
import java.io.IOException

// ✅ Usamos AndroidViewModel para tener acceso al contexto (getApplication())
class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val sessionManager = SessionManager(application)

    private val _loginResult = MutableLiveData<Result<Boolean>>()
    val loginResult: LiveData<Result<Boolean>> = _loginResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun login(correo: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // ✅ Cambio clave: usamos getInstance(getApplication()) en lugar de .instance
                val apiService = RetrofitClient.getInstance(getApplication())
                val response = apiService.login(LoginRequest(correo, password))

                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    loginResponse?.let {
                        // 1. Guardamos el Token (usando tu método saveAuthToken)
                        sessionManager.saveAuthToken(it.token ?: "")

                        // 2. Guardamos los datos del usuario
                        // Convertimos it.rol a String para que coincida con tu SessionManager
                        sessionManager.saveUserData(
                            it.usuarioId,
                            it.correo ?: "",
                            it.nombre ?: "",
                            it.rol?.toString() ?: "USUARIO"
                        )

                        _loginResult.value = Result.success(true)
                    }
                } else {
                    _loginResult.value = Result.failure(Exception("Credenciales incorrectas"))
                }
            } catch (e: IOException) {
                _loginResult.value = Result.failure(Exception("Error de red: Verifique su conexión"))
            } catch (e: Exception) {
                _loginResult.value = Result.failure(Exception("Error: ${e.message}"))
            } finally {
                _isLoading.value = false
            }
        }
    }
}