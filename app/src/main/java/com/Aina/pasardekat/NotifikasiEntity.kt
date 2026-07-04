package com.Aina.pasardekat

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notifikasi")
data class NotifikasiEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val namaPembeli: String,
    val namaProduk: String,
    val jumlah: Int,

    val totalHarga: Int,

    val status: String,
    val namaToko: String
)