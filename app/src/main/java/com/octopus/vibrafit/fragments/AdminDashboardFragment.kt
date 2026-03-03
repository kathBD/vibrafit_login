package com.octopus.vibrafit.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.octopus.vibrafit.R
import com.octopus.vibrafit.databinding.FragmentAdminDashboardBinding
import com.octopus.vibrafit.utils.SessionManager

class AdminDashboardFragment : Fragment() {

    private var _binding: FragmentAdminDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ✅ Mostrar nombre del admin
        val sessionManager = SessionManager(requireContext())
        val nombre = sessionManager.getUserData()[SessionManager.KEY_NOMBRE]
                as? String ?: "Administrador"
        binding.tvWelcomeAdmin.text = "Bienvenido, $nombre"

        binding.btnGestionUsuarios.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AdminUsuariosFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}