package com.Aina.pasardekat

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "produk")
data class ProdukEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val namaProduk: String,

    val harga: Int,

    val stok: Int,

    val satuanHarga: String,

    val satuanStok: String,

    val namaToko: String,

    val fotoProduk: String = ""

)