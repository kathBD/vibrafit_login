package com.octopus.vibrafit.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.octopus.vibrafit.databinding.FragmentHomeBinding
import com.octopus.vibrafit.utils.SessionManager

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sessionManager = SessionManager(requireContext())
        val userData = sessionManager.getUserData()
        val nombre = userData[SessionManager.KEY_NOMBRE] as? String ?: ""
        val correo = userData[SessionManager.KEY_CORREO] as? String ?: ""

        binding.tvGreeting.text  = "Hola $nombre,"
        binding.tvUserName.text  = nombre
        binding.tvUserEmail.text = correo
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
