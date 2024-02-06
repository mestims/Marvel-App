package com.mestims.marvelapp

import android.app.Application
import com.mestims.data_core.dataCoreModule
import com.mestims.marvelapp.characters.di.charactersModule
import com.mestims.marvelapp.characters.persistence.databaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(dataCoreModule, charactersModule, databaseModule)
        }
    }
}