package com.octopus.vibrafit.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.octopus.vibrafit.adapters.UsuarioAdapter
import com.octopus.vibrafit.databinding.FragmentAdminUsuariosBinding
import com.octopus.vibrafit.viewmodels.UsuarioViewModel

class AdminUsuariosFragment : Fragment() {

    private var _binding: FragmentAdminUsuariosBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: UsuarioViewModel
    private lateinit var adapter: UsuarioAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminUsuariosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[UsuarioViewModel::class.java]

        adapter = UsuarioAdapter(
            emptyList(),
            onEdit = { usuario ->
                // próximamente
            },
            onDelete = { usuario ->
                viewModel.eliminarUsuario(usuario.usuarioId)
            }
        )

        binding.recyclerUsuarios.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(false)
            isNestedScrollingEnabled = false
            adapter = this@AdminUsuariosFragment.adapter
        }

        // Observar lista de usuarios
        viewModel.usuarios.observe(viewLifecycleOwner) { lista ->
            adapter.actualizarLista(lista)
        }

        // Observar errores
        viewModel.errorMessage.observe(viewLifecycleOwner) { mensaje ->
            if (!mensaje.isNullOrEmpty()) {
                Toast.makeText(requireContext(), mensaje, Toast.LENGTH_LONG).show()
            }
        }

        // Observar loading
        viewModel.isLoading.observe(viewLifecycleOwner) { cargando ->
            binding.btnNuevoUsuario.isEnabled = !cargando
        }

        // Botón nuevo usuario
        binding.btnNuevoUsuario.setOnClickListener {
            Toast.makeText(requireContext(), "Próximamente: Crear usuario", Toast.LENGTH_SHORT).show()
        }

        viewModel.fetchUsuarios()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}