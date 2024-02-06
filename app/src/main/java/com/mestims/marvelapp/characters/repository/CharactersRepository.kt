package com.mestims.marvelapp.characters.repository

import com.mestims.marvelapp.characters.model.Character
import com.mestims.marvelapp.characters.paging.CharactersPagingSource

interface CharactersRepository {

    fun getCharacters(query: String): CharactersPagingSource

    suspend fun getCharacterDetail(id: String): Character?

    suspend fun getPersistedCharacters() : List<Character>


}