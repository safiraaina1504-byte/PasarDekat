package com.Aina.pasardekat

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {

    @Insert
    suspend fun insert(
        user: UserEntity
    )

    @Query(
        "SELECT * FROM user WHERE email=:email AND password=:password LIMIT 1"
    )
    suspend fun login(
        email: String,
        password: String
    ): UserEntity?

    @Query(
        "SELECT * FROM user WHERE id=:id LIMIT 1"
    )
    suspend fun getUser(
        id: Int
    ): UserEntity?

    @Query(
        "SELECT * FROM user WHERE email=:email LIMIT 1"
    )
    suspend fun getUserByEmail(
        email: String
    ): UserEntity?
}