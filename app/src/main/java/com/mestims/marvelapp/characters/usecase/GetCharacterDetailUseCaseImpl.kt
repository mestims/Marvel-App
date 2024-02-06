package com.mestims.marvelapp.characters.usecase

import com.mestims.marvelapp.characters.model.Character
import com.mestims.marvelapp.characters.repository.CharactersRepository


internal class GetCharacterDetailUseCaseImpl(
    private val repository: CharactersRepository
) : GetCharacterDetailUseCase {

    override suspend fun invoke(id: String): Character? {
        return repository.getCharacterDetail(id)
    }
}