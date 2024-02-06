package com.mestims.marvelapp.characters.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {

    @Query("SELECT * FROM characterEntity")
    suspend fun getAll(): List<CharacterEntity>

    @Query("SELECT * FROM characterEntity WHERE id = :id")
    fun findById(id: Int): Flow<CharacterEntity?>

    @Insert
    suspend fun insertAll(vararg users: CharacterEntity)

    @Query("DELETE FROM characterEntity WHERE id = :id ")
    suspend fun delete(id: Int?)

}