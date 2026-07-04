package com.Aina.pasardekat

import androidx.room.*

@Dao
interface PesananDao {

    @Insert
    suspend fun insert(pesanan: PesananEntity)

    @Query("SELECT * FROM pesanan")
    suspend fun getAllPesanan(): List<PesananEntity>
}