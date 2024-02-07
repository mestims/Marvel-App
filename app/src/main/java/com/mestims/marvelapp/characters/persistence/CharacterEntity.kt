package com.mestims.marvelapp.characters.persistence

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mestims.marvelapp.characters.model.Character

@Entity
data class CharacterEntity(
    @PrimaryKey var id: Int? = null,
    @ColumnInfo(name = "name") var name: String? = null,
    @ColumnInfo(name = "description") var description: String? = null,
    @ColumnInfo(name = "thumbnail") var thumbnail: String? = null,
    @ColumnInfo(name = "isFavorite") var isFavorite: Boolean? = null
) {
    fun toModel() = Character(
        id = id,
        name = name,
        description = description,
        thumbnail = thumbnail,
        isFavorite = isFavorite ?: false
    )
}

fun Character?.toEntity() = CharacterEntity(
    this?.id, this?.name, this?.description, this?.thumbnail, this?.isFavorite
)