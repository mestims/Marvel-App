package com.mestims.marvelapp.characters.repository

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mestims.marvelapp.characters.persistence.CharacterEntity
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {

    fun getCharacters(query: String, pagingConfig: PagingConfig): Flow<PagingData<CharacterEntity>>
    fun getFavoriteCharacters(): Flow<PagingData<CharacterEntity>>
}