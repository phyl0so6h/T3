package com.sahak7an.t3.core.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [FavoriteEntity::class, CourseEntity::class, HiddenEntity::class],
    version = 7,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDao
    abstract fun coursesDao(): CoursesDao
    abstract fun hiddenDao(): HiddenDao
}


