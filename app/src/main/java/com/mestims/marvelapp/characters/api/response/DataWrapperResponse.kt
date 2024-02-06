package com.mestims.marvelapp.characters.api.response

import com.google.gson.annotations.SerializedName

data class DataWrapperResponse<T>(
    @SerializedName("copyright") val copyRight: String,
    @SerializedName("data") val data: DataResponse<T>
)