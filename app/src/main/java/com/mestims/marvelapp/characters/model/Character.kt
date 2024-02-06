package com.mestims.marvelapp.characters.model

data class Character(
    var id: Int? = null,
    var name: String? = null,
    var description: String? = null,
    var thumbnail: String? = null,
    var resourceURI: String? = null,
    var isExpanded: Boolean = false,
    var isFavorite: Boolean = false
)