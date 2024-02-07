package com.mestims.marvelapp.characters.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RemoteKeyDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertOrReplace(remoteKey: RemoteKeyEntity)

  @Query("SELECT * FROM remote_keys WHERE label = :query")
  suspend fun remoteKeyByLabel(query: String): RemoteKeyEntity?

  @Query("DELETE FROM remote_keys WHERE label = :query")
  suspend fun deleteByQuery(query: String)
}