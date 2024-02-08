package com.mestims.marvelapp.characters.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.filter
import com.mestims.marvelapp.characters.persistence.CharacterDao
import com.mestims.marvelapp.characters.persistence.CharacterRemoteMediator
import kotlinx.coroutines.flow.filter

class CharactersRepositoryImpl(
    private val dao: CharacterDao,
    private val mediator: CharacterRemoteMediator
) : CharactersRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getCharacters(query: String, pagingConfig: PagingConfig) = Pager(
        config = pagingConfig,
        remoteMediator = mediator.apply { this.query = query }
    ) {
        dao.pagingSource("$query%")
    }.flow

    override fun getFavoriteCharacters() = Pager(
        config = PagingConfig(20)
    ) { dao.getFavorites(true) }
        .flow

}