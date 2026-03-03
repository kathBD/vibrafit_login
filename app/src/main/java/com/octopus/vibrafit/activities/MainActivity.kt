package com.octopus.vibrafit.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.octopus.vibrafit.R
import com.octopus.vibrafit.databinding.ActivityMainBinding
import com.octopus.vibrafit.fragments.AdminDashboardFragment
import com.octopus.vibrafit.fragments.HomeFragment
import com.octopus.vibrafit.fragments.ProfileFragment
import com.octopus.vibrafit.fragments.SettingsFragment
import com.octopus.vibrafit.utils.SessionManager

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sessionManager: SessionManager

    private val homeFragment = HomeFragment()
    private val profileFragment = ProfileFragment()
    private val settingsFragment = SettingsFragment()
    private val adminFragment = AdminDashboardFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = android.graphics.Color.BLACK
        window.navigationBarColor = android.graphics.Color.BLACK

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        if (!sessionManager.isLoggedIn()) {
            navigateToLogin()
            return
        }

        setupToolbar()
        setupBottomNavigation()
    }

    private fun esAdmin(): Boolean {
        val rol = sessionManager.getUserData()[SessionManager.KEY_ROL]
            ?.toString()?.uppercase() ?: ""
        // ✅ El rol viene como "ROLE_ADMINISTRADOR" o "ADMINISTRADOR"
        return rol.contains("ADMINISTRADOR")
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = if (esAdmin()) "Panel Admin" else "VibraFit"
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    loadFragment(if (esAdmin()) adminFragment else homeFragment)
                    supportActionBar?.title = if (esAdmin()) "Panel Admin" else "Inicio"
                    true
                }
                R.id.navigation_profile -> {
                    loadFragment(profileFragment)
                    supportActionBar?.title = "Mi Perfil"
                    true
                }
                R.id.navigation_settings -> {
                    loadFragment(settingsFragment)
                    supportActionBar?.title = "Configuración"
                    true
                }
                else -> false
            }
        }

        // ✅ Cargar fragmento inicial según rol
        if (esAdmin()) {
            loadFragment(adminFragment)
            supportActionBar?.title = "Panel Admin"
        } else {
            loadFragment(homeFragment)
            supportActionBar?.title = "Inicio"
        }

        binding.bottomNavigation.selectedItemId = R.id.navigation_home
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}