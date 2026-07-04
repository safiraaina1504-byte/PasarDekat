package com.Aina.pasardekat

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val nama: String,

    val namaToko: String,

    val email: String,

    val password: String,

    val role: String
)