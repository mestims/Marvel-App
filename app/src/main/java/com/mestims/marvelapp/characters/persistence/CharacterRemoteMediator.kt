package com.mestims.marvelapp.characters.persistence

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.mestims.marvelapp.characters.api.CharactersApi
import com.mestims.marvelapp.characters.api.response.CharacterResponseDTO
import com.mestims.marvelapp.characters.api.response.toEntity
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class CharacterRemoteMediator(
    private val database: CharacterDatabase,
    private val networkService: CharactersApi
) : RemoteMediator<Int, CharacterEntity>() {

    private val characterDao = database.characterDao()
    private val remoteKeyDao = database.remoteKeyDao()
    var query = ""

    companion object {
        private const val START_VALUE = 0
        private const val LIMIT = 40
        private const val OFFSET = "offset"
        private const val NAME_STARTS_WITH = "nameStartsWith"
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)

                LoadType.APPEND -> {
                    val nextKey = remoteKeyDao.remoteKeyByLabel("character-key")?.nextKey

                    nextKey ?: 0
                }
            }


            val queries = createQueries(loadKey)

            val response = networkService.getCharacters(queries)

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    characterDao.clearAll()
                }
                remoteKeyDao.insertOrReplace(RemoteKeyEntity("character-key", loadKey + 20))
                characterDao.insertAll(response.data.results.map(CharacterResponseDTO::toEntity))
            }
            MediatorResult.Success(
                endOfPaginationReached = response.data.results.isEmpty()
            )


        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    override suspend fun initialize(): InitializeAction {
//        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)
        val hasData = characterDao.getAll().isNotEmpty()
        return if (hasData) {
            // Cached data is up-to-date, so there is no need to re-fetch
            // from the network.
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            // Need to refresh cached data from network; returning
            // LAUNCH_INITIAL_REFRESH here will also block RemoteMediator's
            // APPEND and PREPEND from running until REFRESH succeeds.
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    private fun createQueries(offset: Int) = buildMap {
        put(OFFSET, offset.toString())
        if (query.isNotEmpty()) put(NAME_STARTS_WITH, query)
    }

    private fun getRefreshKey(state: PagingState<Int, CharacterEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(LIMIT) ?: anchorPage?.nextKey?.minus(
                LIMIT
            )
        }
    }
}