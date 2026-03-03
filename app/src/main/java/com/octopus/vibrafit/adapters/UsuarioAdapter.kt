package com.octopus.vibrafit.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.octopus.vibrafit.databinding.ItemUsuarioBinding
import com.octopus.vibrafit.models.Usuario

class UsuarioAdapter(
    private var lista: List<Usuario>,
    private val onEdit: (Usuario) -> Unit,
    private val onDelete: (Usuario) -> Unit
) : RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder>() {

    inner class UsuarioViewHolder(val binding: ItemUsuarioBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioViewHolder {
        val binding = ItemUsuarioBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return UsuarioViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {
        val usuario = lista[position]

        holder.binding.tvNombreUsuario.text = usuario.nombre
        holder.binding.tvCorreoUsuario.text = usuario.correo
        holder.binding.tvRolUsuario.text = usuario.rol

        holder.binding.btnEditar.setOnClickListener { onEdit(usuario) }
        holder.binding.btnEliminar.setOnClickListener { onDelete(usuario) }
    }

    override fun getItemCount() = lista.size

    fun actualizarLista(nuevaLista: List<Usuario>) {
        lista = nuevaLista
        notifyDataSetChanged()
    }
}