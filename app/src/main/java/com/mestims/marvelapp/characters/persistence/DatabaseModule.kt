package com.mestims.marvelapp.characters.persistence

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {

    single<CharacterDao> {
        Room.databaseBuilder(
            androidContext(),
            CharacterDatabase::class.java, "character-database"
        ).build().characterDao()
    }
}