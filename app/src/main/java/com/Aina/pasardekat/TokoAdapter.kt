package com.Aina.pasardekat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TokoAdapter(
    private val listToko: List<String>
) : RecyclerView.Adapter<TokoAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val txtNamaToko: TextView =
            itemView.findViewById(R.id.txtNamaToko)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val view =
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_toko,
                    parent,
                    false
                )

        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {

        holder.txtNamaToko.text =
            listToko[position]
    }

    override fun getItemCount(): Int {

        return listToko.size
    }
}