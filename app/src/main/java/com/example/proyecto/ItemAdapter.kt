package com.example.proyecto

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.example.proyecto.databinding.ActivityMapBoxBinding
import com.squareup.picasso.Picasso

class ItemAdapter(private val lst_sitios:List<ItemSitio>, private val lst_imagenes:List<imagenes> ) :RecyclerView.Adapter<ItemAdapter.ItemHolder>() {

    class ItemHolder(item: View):RecyclerView.ViewHolder (item) {
        var imagen:ImageView = item.findViewById(R.id.imagen_sitio)
        var nombre:TextView = item.findViewById(R.id.tv_nombre)
        var desc_conceptual_sitio:TextView = item.findViewById(R.id.tv_descripcion)
        var btn_ver:Button=item.findViewById(R.id.btn_versitio)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val cardInflado =LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false)
        return ItemHolder(cardInflado)
    }

    override fun getItemCount(): Int {
        return lst_sitios.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = lst_sitios[position]
        val imagen = lst_imagenes[position]


        Picasso.get()
            .load(imagen.urlimagen)
            .into(holder.imagen)


        holder.nombre.text = item.nombre
        holder.desc_conceptual_sitio.text = item.desc_conceptual_sitio

        holder.btn_ver.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, MapBoxActivity::class.java)
            intent.putExtra("id_ubicacion",item.id_ubicacion)

            context.startActivity(intent)
        }


    }

}