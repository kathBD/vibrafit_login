package com.octopus.vibrafit.utils


import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

class SessionManager(context: Context) {

    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    private val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        "session_prefs",
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    companion object {
        const val KEY_TOKEN = "token"
        const val KEY_USUARIO_ID = "usuario_id"
        const val KEY_CORREO = "correo"
        const val KEY_NOMBRE = "nombre"
        const val KEY_ROL = "rol"
        const val KEY_IS_LOGGED_IN = "is_logged_in"
    }

    fun saveAuthToken(token: String) {
        sharedPreferences.edit().putString(KEY_TOKEN, token).apply()
    }

    fun getAuthToken(): String? {
        return sharedPreferences.getString(KEY_TOKEN, null)
    }

    fun saveUserData(usuarioId: Long, correo: String, nombre: String, rol: String) {
        val editor = sharedPreferences.edit()
        editor.putLong(KEY_USUARIO_ID, usuarioId)
        editor.putString(KEY_CORREO, correo)
        editor.putString(KEY_NOMBRE, nombre)
        editor.putString(KEY_ROL, rol)
        editor.putBoolean(KEY_IS_LOGGED_IN, true)
        editor.apply()
    }

    fun getUserData(): Map<String, Any?> {
        return mapOf(
            KEY_USUARIO_ID to sharedPreferences.getLong(KEY_USUARIO_ID, -1),
            KEY_CORREO to sharedPreferences.getString(KEY_CORREO, null),
            KEY_NOMBRE to sharedPreferences.getString(KEY_NOMBRE, null),
            KEY_ROL to sharedPreferences.getString(KEY_ROL, null),
            KEY_IS_LOGGED_IN to sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
        )
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun logout() {
        sharedPreferences.edit().clear().apply()
    }
}