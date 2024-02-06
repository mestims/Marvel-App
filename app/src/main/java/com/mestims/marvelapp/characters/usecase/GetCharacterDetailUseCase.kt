package com.mestims.marvelapp.characters.usecase

import com.mestims.marvelapp.characters.model.Character


interface GetCharacterDetailUseCase {

    suspend operator fun invoke(id: String): Character?
}