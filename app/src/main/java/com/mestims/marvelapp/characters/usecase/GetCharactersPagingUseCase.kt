package com.mestims.marvelapp.characters.usecase

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mestims.marvelapp.characters.model.Character
import kotlinx.coroutines.flow.Flow

interface GetCharactersPagingUseCase {

    suspend operator fun invoke(query: String, pagingConfig: PagingConfig): Flow<PagingData<Character>>
}