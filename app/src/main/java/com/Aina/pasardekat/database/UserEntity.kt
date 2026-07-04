package com.Aina.pasardekat.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val nama: String,

    val email: String,

    val password: String,

    val role: String,

    val namaToko: String = ""
)