package com.mestims.marvelapp.characters.persistence

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CharacterDao {

    @Query("SELECT * FROM characterEntity")
    suspend fun getAll(): List<CharacterEntity>

    @Query("SELECT * FROM characterEntity WHERE isFavorite= :isFavorite ORDER BY name ASC")
    fun getFavorites(isFavorite: Boolean): PagingSource<Int, CharacterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<CharacterEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(characters: CharacterEntity)

    @Query("SELECT * FROM characterEntity ORDER BY name ASC")
    fun pagingSource(): PagingSource<Int, CharacterEntity>


    @Query("DELETE FROM characterEntity WHERE id = :id ")
    suspend fun delete(id: Int?)

    @Query("DELETE FROM characterEntity")
    suspend fun clearAll()

}