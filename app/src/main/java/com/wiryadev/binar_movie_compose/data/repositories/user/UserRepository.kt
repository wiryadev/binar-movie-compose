package com.wiryadev.binar_movie_compose.data.repositories.user

import com.wiryadev.binar_movie_compose.data.local.entity.UserEntity
import com.wiryadev.binar_movie_compose.data.preference.AuthModel
import com.wiryadev.binar_movie_compose.data.remote.Result
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun register(user: UserEntity)

    suspend fun login(email: String, password: String): Flow<UserEntity>

    suspend fun checkUserExist(email: String): Int

    fun getUserSession(): Flow<AuthModel>

    suspend fun saveUserSession(user: AuthModel)

    suspend fun deleteUserSession()

    fun getUser(email: String): Flow<UserEntity>

    suspend fun updateUser(user: UserEntity): Result<Int>

}
