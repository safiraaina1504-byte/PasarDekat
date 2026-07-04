package com.Aina.pasardekat

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pendapatan")
data class PendapatanEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val namaToko : String,
    val totalPendapatan: Int
)