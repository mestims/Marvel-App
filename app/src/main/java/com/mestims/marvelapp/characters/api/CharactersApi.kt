package com.mestims.marvelapp.characters.api

import com.mestims.marvelapp.characters.api.response.CharacterResponseDTO
import com.mestims.marvelapp.characters.api.response.DataWrapperResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface CharactersApi {

    @GET("/v1/public/characters")
    suspend fun getCharacters(@QueryMap queries: Map<String, String>): DataWrapperResponse<CharacterResponseDTO>

    @GET("/v1/public/characters/{characterId}")
    suspend fun getCharacterDetail(@Path("characterId") id: String): DataWrapperResponse<CharacterResponseDTO>

}