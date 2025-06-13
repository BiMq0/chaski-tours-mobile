package com.example.proyecto

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class CategoriaAdapter(
    private val lst_categorias: List<ItemCategoria>,
    private val lst_imagenes: List<imagenes>
) : RecyclerView.Adapter<CategoriaAdapter.CategoriaHolder>() {
    class CategoriaHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imagen: ImageView = itemView.findViewById(R.id.imagen_categoria)
        val nombre: TextView = itemView.findViewById(R.id.tv_nombre_categoria)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriaHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_categoria, parent, false)
        return CategoriaHolder(view)
    }

    override fun getItemCount(): Int = lst_imagenes.size

    override fun onBindViewHolder(holder: CategoriaHolder, position: Int) {
        val categoria = lst_categorias[position]
        val imagen = lst_imagenes[position]

        holder.nombre.text = categoria.nombre_categoria
        Picasso.get()
            .load(imagen.urlimagen)
            .into(holder.imagen)
    }
}

