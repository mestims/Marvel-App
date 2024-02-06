package com.mestims.marvelapp.characters.api.response

import com.google.gson.annotations.SerializedName
import com.mestims.marvelapp.characters.model.Character


data class CharacterResponseDTO(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("thumbnail") var thumbnail: ThumbnailDTO? = null,
    @SerializedName("resourceURI") var resourceURI: String? = null,
)

fun CharacterResponseDTO.toModel() = Character(
    id = id,
    name = name,
    description = description,
    resourceURI = resourceURI,
    thumbnail = thumbnail?.toUrl()
)
