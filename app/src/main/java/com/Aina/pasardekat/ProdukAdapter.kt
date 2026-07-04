package com.Aina.pasardekat

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class ProdukAdapter(

    private val listProduk: ArrayList<ProdukEntity>

) : RecyclerView.Adapter<ProdukAdapter.ViewHolder>() {

    class ViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        val imgProduk: ImageView =
            itemView.findViewById(R.id.imgProduk)

        val txtNamaProduk: TextView =
            itemView.findViewById(R.id.txtNamaProduk)

        val txtNamaToko: TextView =
            itemView.findViewById(R.id.txtNamaToko)

        val txtHarga: TextView =
            itemView.findViewById(R.id.txtHarga)

        val txtStok: TextView =
            itemView.findViewById(R.id.txtStok)

        val btnTambah: Button =
            itemView.findViewById(R.id.btnTambah)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_produk,
                parent,
                false
            )

        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {

        val produk = listProduk[position]

        holder.txtNamaProduk.text = produk.namaProduk

        holder.txtNamaToko.text = produk.namaToko

        holder.txtHarga.text =
            "${RupiahHelper.format(produk.harga)} / ${
                produk.satuanHarga.replace("Per ", "")
            }"

        holder.txtStok.text =
            "Stok : ${produk.stok} ${
                produk.satuanStok.replace("Per ", "")
            }"

        if (produk.fotoProduk.isNotEmpty()) {

            try {

                val uri = Uri.parse(produk.fotoProduk)

                holder.imgProduk.setImageURI(null)
                holder.imgProduk.setImageURI(uri)

            } catch (e: Exception) {

                holder.imgProduk.setImageResource(
                    android.R.drawable.ic_menu_gallery
                )

            }

        } else {

            holder.imgProduk.setImageResource(
                android.R.drawable.ic_menu_gallery
            )

        }

        holder.btnTambah.setOnClickListener {

            val sudahAda = DataKeranjang.listKeranjang.any {

                it.namaProduk == produk.namaProduk

            }

            if (sudahAda) {

                Toast.makeText(
                    holder.itemView.context,
                    "Produk sudah ada di keranjang",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener

            }

            DataKeranjang.listKeranjang.add(

                Keranjang(
                    namaProduk = produk.namaProduk,
                    namaToko = produk.namaToko,
                    harga = produk.harga,
                    jumlah = 1
                )

            )

            Toast.makeText(
                holder.itemView.context,
                "${produk.namaProduk} berhasil ditambahkan ke keranjang",
                Toast.LENGTH_SHORT
            ).show()

            if (holder.itemView.context is DashboardPembeliActivity) {

                (holder.itemView.context as DashboardPembeliActivity)
                    .updateBadge()

            }

        }

    }

    override fun getItemCount(): Int {

        return listProduk.size

    }

}