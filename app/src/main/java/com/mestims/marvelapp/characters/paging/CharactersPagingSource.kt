package com.mestims.marvelapp.characters.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mestims.marvelapp.characters.api.response.CharacterResponseDTO
import com.mestims.marvelapp.characters.api.response.DataWrapperResponse
import com.mestims.marvelapp.characters.api.response.toModel
import com.mestims.marvelapp.characters.model.Character

class CharactersPagingSource(
    private val query: String,
    private val fetchCharacters: suspend (Map<String, String>) -> DataWrapperResponse<CharacterResponseDTO>?,
) : PagingSource<Int, Character>() {

    companion object {
        private const val START_VALUE = 0
        private const val LIMIT = 20
        private const val OFFSET = "offset"
        private const val NAME_STARTS_WITH = "nameStartsWith"
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        return try {
            val offset = params.key ?: START_VALUE
            val queries = createQueries(offset)

            val response = fetchCharacters(queries) ?: throw IllegalStateException()
            val responseOffset = response.data.offset
            val total = response.data.total

            LoadResult.Page(
                data = response.data.results.map { it.toModel() },
                prevKey = null,
                nextKey = if (responseOffset < total) {
                    responseOffset + LIMIT
                } else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    private fun createQueries(offset: Int) = buildMap {
        put(OFFSET, offset.toString())
        if (query.isNotEmpty()) put(NAME_STARTS_WITH, query)
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(LIMIT) ?: anchorPage?.nextKey?.minus(LIMIT)
        }
    }
}