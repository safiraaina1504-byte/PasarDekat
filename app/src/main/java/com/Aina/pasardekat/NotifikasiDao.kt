package com.Aina.pasardekat

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NotifikasiDao {

    @Insert
    suspend fun insert(
        notifikasi: NotifikasiEntity
    )

    @Update
    suspend fun update(
        notifikasi: NotifikasiEntity
    )

    @Query(
        "SELECT * FROM notifikasi"
    )
    suspend fun getAll(): List<NotifikasiEntity>

    @Query(
        "SELECT * FROM notifikasi WHERE namaToko = :namaToko"
    )
    suspend fun getByToko(
        namaToko: String
    ): List<NotifikasiEntity>
}