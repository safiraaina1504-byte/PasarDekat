package com.Aina.pasardekat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class KeranjangAdapter(

    private val listKeranjang: ArrayList<Keranjang>

) : RecyclerView.Adapter<KeranjangAdapter.ViewHolder>() {

    class ViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        val txtNamaProduk: TextView =
            itemView.findViewById(
                R.id.txtNamaProduk
            )

        val txtHarga: TextView =
            itemView.findViewById(
                R.id.txtHarga
            )

        val txtJumlah: TextView =
            itemView.findViewById(
                R.id.txtJumlah
            )

        val btnTambah: Button =
            itemView.findViewById(
                R.id.btnTambah
            )

        val btnKurang: Button =
            itemView.findViewById(
                R.id.btnKurang
            )

        val btnHapus: Button =
            itemView.findViewById(
                R.id.btnHapus
            )
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val view =
            LayoutInflater.from(
                parent.context
            ).inflate(
                R.layout.item_keranjang,
                parent,
                false
            )

        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {

        val item =
            listKeranjang[position]

        holder.txtNamaProduk.text =
            item.namaProduk

        holder.txtHarga.text =
            "Rp ${item.harga}"

        holder.txtJumlah.text =
            "Jumlah : ${item.jumlah}"

        holder.btnTambah.setOnClickListener {

            item.jumlah++

            notifyItemChanged(position)
        }

        holder.btnKurang.setOnClickListener {

            if (item.jumlah > 1) {

                item.jumlah--

                notifyItemChanged(position)
            }
        }

        holder.btnHapus.setOnClickListener {

            listKeranjang.removeAt(position)

            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {

        return listKeranjang.size
    }
}