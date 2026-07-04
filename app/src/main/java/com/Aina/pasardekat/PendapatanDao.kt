package com.Aina.pasardekat

import androidx.room.*

@Dao
interface PendapatanDao {

    @Insert
    suspend fun insert(
        pendapatan: PendapatanEntity
    )

    @Update
    suspend fun update(
        pendapatan: PendapatanEntity
    )

    @Query(
        "SELECT * FROM pendapatan WHERE namaToko = :namaToko LIMIT 1"
    )
    suspend fun getPendapatanByToko(
        namaToko: String
    ): PendapatanEntity?
}