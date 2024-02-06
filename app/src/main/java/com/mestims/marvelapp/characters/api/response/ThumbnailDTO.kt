package com.mestims.marvelapp.characters.api.response

import com.google.gson.annotations.SerializedName


data class ThumbnailDTO(
    @SerializedName("path") var path: String? = null,
    @SerializedName("extension") var extension: String? = null
)

fun ThumbnailDTO.toUrl() = "$path.$extension"
