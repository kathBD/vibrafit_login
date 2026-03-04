package com.octopus.vibrafit.activities

import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.octopus.vibrafit.databinding.ActivityLoginBinding
import com.octopus.vibrafit.utils.SessionManager
import com.octopus.vibrafit.viewmodels.LoginViewModel
import com.octopus.vibrafit.viewmodels.LoginViewModelFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private var passwordVisible = false

    private val viewModel: LoginViewModel by viewModels {
        LoginViewModelFactory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = android.graphics.Color.BLACK
        window.navigationBarColor = android.graphics.Color.BLACK

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sessionManager = SessionManager(this)
        if (sessionManager.isLoggedIn()) {
            navigateToMain()
            return
        }

        setupTogglePassword()
        setupObservers()

        binding.btnLogin.setOnClickListener {
            val correo = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (correo.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.login(correo, password)
        }
    }

    private fun setupTogglePassword() {
        binding.ivTogglePassword.setOnClickListener {
            passwordVisible = !passwordVisible
            if (passwordVisible) {
                binding.etPassword.transformationMethod = null
                binding.ivTogglePassword.text = "🙈"
            } else {
                binding.etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.ivTogglePassword.text = "👁️"
            }
            binding.etPassword.setSelection(binding.etPassword.text.length)
        }
    }

    private fun setupObservers() {
        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.btnLogin.isEnabled = !isLoading
        }

        // ✅ loginResult es Result<Boolean>, no Result<LoginResponse>
        viewModel.loginResult.observe(this) { result ->
            result.onSuccess {
                // El ViewModel ya guardó la sesión, solo navegamos
                Toast.makeText(this, "¡Bienvenido a VibraFit!", Toast.LENGTH_SHORT).show()
                navigateToMain()
            }
            result.onFailure { error ->
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}