package com.sahak7an.t3.core.database

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "t3-db"
        ).fallbackToDestructiveMigration().build()
    }
    single { get<AppDatabase>().favoritesDao() }
    single { get<AppDatabase>().coursesDao() }
    single { get<AppDatabase>().hiddenDao() }
}


