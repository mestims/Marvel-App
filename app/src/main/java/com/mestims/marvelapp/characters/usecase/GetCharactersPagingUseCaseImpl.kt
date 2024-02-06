package com.mestims.marvelapp.characters.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.mestims.marvelapp.characters.model.Character
import com.mestims.marvelapp.characters.repository.CharactersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class GetCharactersPagingUseCaseImpl(
    private val repository: CharactersRepository
) : GetCharactersPagingUseCase {

    override suspend fun invoke(query: String, pagingConfig: PagingConfig): Flow<PagingData<Character>> {
        val persistedCharacters = repository.getPersistedCharacters().associateBy { it.id }

        return Pager(config = pagingConfig) {
            repository.getCharacters(query)
        }.flow
            .map { source ->
                source.map { character ->
                    val isPersisted = persistedCharacters[character.id]
                    character.apply { isFavorite = isPersisted != null }
                }
            }
    }
}