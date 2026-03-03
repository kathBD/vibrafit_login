package com.octopus.vibrafit.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.octopus.vibrafit.R
import com.octopus.vibrafit.databinding.FragmentProfileBinding
import com.octopus.vibrafit.utils.SessionManager

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sessionManager = SessionManager(requireContext())
        val userData = sessionManager.getUserData()

        // Mostrar información básica
        binding.tvNombre.text = userData[SessionManager.KEY_NOMBRE] as? String ?: ""
        binding.tvCorreo.text = userData[SessionManager.KEY_CORREO] as? String ?: ""
        binding.tvRol.text    = "Rol: ${userData[SessionManager.KEY_ROL] as? String ?: ""}"

        // Mostrar botón de gestión de usuarios solo para ADMIN
        val rol = userData[SessionManager.KEY_ROL]?.toString()?.uppercase()
        if (rol == "ADMIN") {
            binding.btnGestionUsuarios.visibility = View.VISIBLE
            binding.btnGestionUsuarios.setOnClickListener {
                val adminFragment = com.octopus.vibrafit.fragments.AdminUsuariosFragment()
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, adminFragment)
                    .addToBackStack(null)
                    .commit()
            }
        } else {
            binding.btnGestionUsuarios.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}