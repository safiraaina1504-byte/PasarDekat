package com.Aina.pasardekat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NotifikasiAdapter(
    private val list: ArrayList<NotifikasiEntity>,
    private val listener: NotifikasiListener
) : RecyclerView.Adapter<NotifikasiAdapter.ViewHolder>() {

    interface NotifikasiListener {

        fun onProses(
            notifikasi: NotifikasiEntity
        )

        fun onSelesai(
            notifikasi: NotifikasiEntity
        )
    }

    class ViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView) {

        val txtPembeli =
            itemView.findViewById<TextView>(
                R.id.txtPembeli
            )

        val txtProduk =
            itemView.findViewById<TextView>(
                R.id.txtProduk
            )

        val txtJumlah =
            itemView.findViewById<TextView>(
                R.id.txtJumlah
            )

        val txtTotal =
            itemView.findViewById<TextView>(
                R.id.txtTotal
            )

        val txtStatus =
            itemView.findViewById<TextView>(
                R.id.txtStatus
            )

        val btnProses =
            itemView.findViewById<Button>(
                R.id.btnProses
            )

        val btnSelesai =
            itemView.findViewById<Button>(
                R.id.btnSelesai
            )
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val view =
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_notifikasi,
                    parent,
                    false
                )

        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {

        val item = list[position]

        holder.txtPembeli.text =
            item.namaPembeli

        holder.txtProduk.text =
            item.namaProduk

        holder.txtJumlah.text =
            "Jumlah : ${item.jumlah}"

        holder.txtTotal.text =
            "Total : Rp ${item.totalHarga}"

        holder.txtStatus.text =
            item.status

        holder.btnProses.setOnClickListener {

            listener.onProses(item)
        }

        holder.btnSelesai.setOnClickListener {

            listener.onSelesai(item)
        }
    }

    override fun getItemCount(): Int {

        return list.size
    }
}