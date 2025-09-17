package com.sahak7an.t3

import android.app.Application
import com.sahak7an.t3.core.database.databaseModule
import com.sahak7an.t3.core.network.networkModule
import com.sahak7an.t3.data.dataModule
import com.sahak7an.t3.feature.favorites.di.favoritesModule
import com.sahak7an.t3.feature.home.di.homeModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                networkModule,
                databaseModule,
                dataModule,
                homeModule,
                favoritesModule
            )
        }
    }
}


