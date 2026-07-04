package com.Aina.pasardekat

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProdukPenjualAdapter(

    private val listProduk: ArrayList<ProdukEntity>,
    private val onHapusClick: (ProdukEntity) -> Unit

) : RecyclerView.Adapter<ProdukPenjualAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imgProduk: ImageView =
            itemView.findViewById(R.id.imgProduk)

        val txtNamaProduk: TextView =
            itemView.findViewById(R.id.txtNamaProduk)

        val txtHarga: TextView =
            itemView.findViewById(R.id.txtHarga)

        val txtStok: TextView =
            itemView.findViewById(R.id.txtStok)

        val btnEdit: Button =
            itemView.findViewById(R.id.btnEdit)

        val btnHapus: Button =
            itemView.findViewById(R.id.btnHapus)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_produk_penjual,
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

        holder.btnEdit.setOnClickListener {

            val intent = Intent(
                holder.itemView.context,
                EditProdukActivity::class.java
            )

            intent.putExtra(
                "ID_PRODUK",
                produk.id
            )

            intent.putExtra(
                "NAMA",
                produk.namaProduk
            )

            intent.putExtra(
                "HARGA",
                produk.harga
            )

            intent.putExtra(
                "STOK",
                produk.stok
            )

            intent.putExtra(
                "SATUAN_HARGA",
                produk.satuanHarga
            )

            intent.putExtra(
                "SATUAN_STOK",
                produk.satuanStok
            )

            intent.putExtra(
                "FOTO",
                produk.fotoProduk
            )

            holder.itemView.context.startActivity(intent)

        }

        holder.btnHapus.setOnClickListener {

            onHapusClick(produk)

        }

    }

    override fun getItemCount(): Int {

        return listProduk.size

    }

}