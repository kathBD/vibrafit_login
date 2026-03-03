package com.octopus.vibrafit.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAdminUsuariosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel = ViewModelProvider(this)[UsuarioViewModel::class.java]

        adapter = UsuarioAdapter(emptyList(),
            onEdit = { usuario ->
                // Navegar a editar
            },
            onDelete = { usuario ->
                viewModel.eliminarUsuario(usuario.usuarioId)
            }
        )

        binding.recyclerUsuarios.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerUsuarios.adapter = adapter

        viewModel.usuarios.observe(viewLifecycleOwner) {
            adapter.actualizarLista(it)
        }

        viewModel.fetchUsuarios()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}