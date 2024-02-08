package com.mestims.marvelapp.characters.util

import com.mestims.marvelapp.characters.api.response.CharacterResponseDTO
import com.mestims.marvelapp.characters.api.response.DataResponse
import com.mestims.marvelapp.characters.api.response.DataWrapperResponse
import com.mestims.marvelapp.characters.model.Character

object CharacterFactory {

    fun createList() = listOf<Character>(
        Character(
            1, "character1", "desc", "thumb1", resourceURI = null
        ),
        Character(
            2, "character2", "desc", "thumb2", resourceURI = null
        ),
        Character(
            3, "character3", "desc", "thumb3", resourceURI = null
        )
    )

    fun createResponseList() = listOf<CharacterResponseDTO>(
        CharacterResponseDTO(
            1, "character1", "desc", null, resourceURI = null
        ),
        CharacterResponseDTO(
            2, "character2", "desc", null, resourceURI = null
        ),
        CharacterResponseDTO(
            3, "character3", "desc", null, resourceURI = null
        )
    )

    fun createApiResponse() = DataWrapperResponse("", DataResponse(0, 20, createResponseList()))

}