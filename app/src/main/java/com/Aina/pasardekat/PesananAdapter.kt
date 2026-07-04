package com.Aina.pasardekat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PesananAdapter(
    private val listPesanan: ArrayList<PesananEntity>
) : RecyclerView.Adapter<PesananAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val txtNamaProduk: TextView =
            itemView.findViewById(R.id.txtNamaProduk)

        val txtJumlah: TextView =
            itemView.findViewById(R.id.txtJumlah)

        val txtTotal: TextView =
            itemView.findViewById(R.id.txtTotal)

        val txtStatus: TextView =
            itemView.findViewById(R.id.txtStatus)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_pesanan,
                parent,
                false
            )

        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {

        val pesanan = listPesanan[position]

        holder.txtNamaProduk.text =
            pesanan.namaProduk

        holder.txtJumlah.text =
            "Jumlah : ${pesanan.jumlah}"

        holder.txtTotal.text =
            "Total : Rp ${pesanan.totalHarga}"

        holder.txtStatus.text =
            "Status : ${pesanan.status}"
    }

    override fun getItemCount(): Int {
        return listPesanan.size
    }
}