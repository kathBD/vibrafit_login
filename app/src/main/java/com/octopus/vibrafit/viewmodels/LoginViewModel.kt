package com.octopus.vibrafit.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope


import com.octopus.vibrafit.models.LoginRequest
import com.octopus.vibrafit.network.RetrofitClient
import com.octopus.vibrafit.utils.SessionManager
import kotlinx.coroutines.launch
import java.io.IOException

class LoginViewModel(private val sessionManager: SessionManager) : ViewModel() {

    private val _loginResult = MutableLiveData<Result<Boolean>>()
    val loginResult: LiveData<Result<Boolean>> = _loginResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun login(correo: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitClient.instance.login(LoginRequest(correo, password))

                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    loginResponse?.let {
                        sessionManager.saveAuthToken(it.token ?: "")
                        sessionManager.saveUserData(
                            it.usuarioId,
                            it.correo,
                            it.nombre,
                            it.rol
                        )
                        _loginResult.value = Result.success(true)
                    }
                } else {
                    _loginResult.value = Result.failure(
                        Exception("Credenciales incorrectas")
                    )
                }
            } catch (e: IOException) {
                _loginResult.value = Result.failure(
                    Exception("Sin conexión a internet")
                )
            } catch (e: Exception) {
                _loginResult.value = Result.failure(
                    Exception("Error inesperado: ${e.message}")
                )
            } finally {
                _isLoading.value = false
            }
        }
    }
}