package com.mestims.marvelapp.characters.persistence

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {

    single<CharacterDatabase> {
        Room.databaseBuilder(
            androidContext(),
            CharacterDatabase::class.java, "character-database"
        ).build()
    }

    single<CharacterDao> {
        get<CharacterDatabase>().characterDao()
    }

    factory { CharacterRemoteMediator(get(), get()) }
}