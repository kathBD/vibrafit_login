package com.octopus.vibrafit.activities

import android.content.Intent
import android.os.Bundle
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.octopus.vibrafit.R
import com.octopus.vibrafit.databinding.ActivityMainBinding
import com.octopus.vibrafit.fragments.AdminUsuariosFragment // Asegúrate de que este sea el nombre de tu fragmento de lista
import com.octopus.vibrafit.fragments.HomeFragment
import com.octopus.vibrafit.fragments.ProfileFragment
import com.octopus.vibrafit.fragments.SettingsFragment
import com.octopus.vibrafit.utils.SessionManager

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sessionManager: SessionManager

    // Instancias de Fragmentos
    private val homeFragment = HomeFragment()
    private val profileFragment = ProfileFragment()
    private val settingsFragment = SettingsFragment()
    private val adminUsuariosFragment = AdminUsuariosFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Estética de barras de sistema
        window.statusBarColor = Color.BLACK
        window.navigationBarColor = Color.BLACK

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        // 1. Verificación de Seguridad
        if (!sessionManager.isLoggedIn()) {
            navigateToLogin()
            return
        }

        // 2. Configuración de UI
        setupToolbar()
        setupBottomNavigation()
    }

    /**
     * Verifica si el usuario actual tiene rol de Administrador
     */
    private fun esAdmin(): Boolean {
        val userData = sessionManager.getUserData()
        val rol = userData[SessionManager.KEY_ROL]?.toString()?.uppercase() ?: ""
        // Soporta tanto "ADMINISTRADOR" como "ROLE_ADMINISTRADOR" que viene de Spring Boot
        return rol.contains("ADMINISTRADOR")
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        actualizarTituloToolbar(R.id.navigation_home) // Título inicial
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            val fragmentParaCargar = when (menuItem.itemId) {
                R.id.navigation_home -> {
                    if (esAdmin()) adminUsuariosFragment else homeFragment
                }
                R.id.navigation_profile -> profileFragment
                R.id.navigation_settings -> settingsFragment
                else -> null
            }

            fragmentParaCargar?.let {
                loadFragment(it)
                actualizarTituloToolbar(menuItem.itemId)
                true
            } ?: false
        }

        // ✅ CARGA INICIAL: Esto dispara el listener de arriba automáticamente
        binding.bottomNavigation.selectedItemId = R.id.navigation_home
    }

    private fun actualizarTituloToolbar(itemId: Int) {
        val titulo = when (itemId) {
            R.id.navigation_home -> if (esAdmin()) "Panel Administrador" else "VibraFit - Inicio"
            R.id.navigation_profile -> "Mi Perfil"
            R.id.navigation_settings -> "Configuración"
            else -> "VibraFit"
        }
        supportActionBar?.title = titulo
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out) // Animación suave
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        // Limpiar el stack de actividades para que no pueda volver atrás
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}