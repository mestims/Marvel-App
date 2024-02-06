package com.mestims.marvelapp.characters.repository

import com.mestims.marvelapp.characters.api.CharactersApi
import com.mestims.marvelapp.characters.api.response.toModel
import com.mestims.marvelapp.characters.model.Character
import com.mestims.marvelapp.characters.paging.CharactersPagingSource
import com.mestims.marvelapp.characters.persistence.CharacterDao

class CharactersRepositoryImpl(private val api: CharactersApi, private val dao: CharacterDao): CharactersRepository {

    override fun getCharacters(query: String) = CharactersPagingSource(
        query = query,
        fetchCharacters = { queries ->
            api.getCharacters(queries)
        }
    )

    override suspend fun getCharacterDetail(id: String) = api.getCharacterDetail(id)
        .data
        .results
        .firstOrNull()
        ?.toModel()

    override suspend fun getPersistedCharacters(): List<Character> = dao.getAll().map { it.toModel() }

}