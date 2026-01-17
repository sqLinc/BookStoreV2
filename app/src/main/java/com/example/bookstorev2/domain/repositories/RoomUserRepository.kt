package com.example.bookstorev2.domain.repositories

import com.example.bookstorev2.data.local.room.dao.UserDao
import com.example.bookstorev2.data.local.room.dto.UserDto
import com.example.bookstorev2.data.local.room.entity.UserDbEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomUserRepository(private val userDao: UserDao) {

    suspend fun insertNewUser(userDbEntity: UserDbEntity) {
        withContext(Dispatchers.IO) {
            userDao.insertNewUser(userDbEntity)
        }
    }

    suspend fun getAllUsers() : List<UserDto> {
        return withContext(Dispatchers.IO) {
            return@withContext userDao.getAllUsers()
        }
    }

    suspend fun deleteUserById(userId: Long) {
        withContext(Dispatchers.IO) {
            userDao.deleteUserById(userId)
        }
    }

}