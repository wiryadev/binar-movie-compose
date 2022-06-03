package com.wiryadev.binar_movie_compose.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wiryadev.binar_movie_compose.data.local.entity.UserEntity

@Database(
    entities = [
        UserEntity::class,
    ],
    version = 2,
)
abstract class BinarMovieDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

}