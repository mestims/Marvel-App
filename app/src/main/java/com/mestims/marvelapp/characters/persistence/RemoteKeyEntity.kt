package com.mestims.marvelapp.characters.persistence

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeyEntity(
    @PrimaryKey val label: String,
    @ColumnInfo(name = "nextKey") val nextKey: Int?,
    @ColumnInfo(name = "query") val query: String
)