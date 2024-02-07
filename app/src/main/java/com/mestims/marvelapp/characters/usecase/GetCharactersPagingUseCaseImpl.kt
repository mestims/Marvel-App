package com.mestims.marvelapp.characters.usecase

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

    override suspend fun invoke(
        query: String,
        pagingConfig: PagingConfig
    ): Flow<PagingData<Character>> =
        repository.getCharacters(query, pagingConfig).map { it.map { entity -> entity.toModel() } }


    override suspend fun invoke(pagingConfig: PagingConfig): Flow<PagingData<Character>> =
        repository.getFavoriteCharacters().map { it.map { entity -> entity.toModel() } }

}