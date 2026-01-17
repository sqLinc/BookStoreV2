package com.example.bookstorev2.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.bookstorev2.data.local.room.entity.UserDbEntity
import com.example.bookstorev2.data.local.room.dto.UserDto

@Dao
interface UserDao{

    @Insert(entity = UserDbEntity::class)
    fun insertNewUser(user: UserDbEntity)

    @Query("SELECT * FROM user")
    fun getAllUsers() : List<UserDto>

    @Query("DELETE FROM user WHERE id = :userId")
    fun deleteUserById(userId: Long)

}