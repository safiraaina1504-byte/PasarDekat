package com.Aina.pasardekat

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ProdukDao {

    @Insert
    suspend fun insert(
        produk: ProdukEntity
    )

    @Update
    suspend fun update(
        produk: ProdukEntity
    )

    @Delete
    suspend fun delete(
        produk: ProdukEntity
    )

    @Query(
        "SELECT * FROM produk"
    )
    suspend fun getAllProduk()
            : List<ProdukEntity>

    @Query(
        "SELECT * FROM produk WHERE namaToko = :namaToko"
    )
    suspend fun getProdukByToko(
        namaToko: String
    ): List<ProdukEntity>

    @Query(
        "SELECT * FROM produk WHERE id = :id LIMIT 1"
    )
    suspend fun getProdukById(
        id: Int
    ): ProdukEntity?

    @Query(
        "SELECT DISTINCT namaToko FROM produk"
    )
    suspend fun getAllToko()
            : List<String>
}