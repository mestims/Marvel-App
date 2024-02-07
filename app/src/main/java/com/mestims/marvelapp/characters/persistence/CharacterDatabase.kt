package com.mestims.marvelapp.characters.persistence

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CharacterEntity::class, RemoteKeyEntity::class], version = 1)
abstract class CharacterDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao

    abstract fun remoteKeyDao(): RemoteKeyDao
}