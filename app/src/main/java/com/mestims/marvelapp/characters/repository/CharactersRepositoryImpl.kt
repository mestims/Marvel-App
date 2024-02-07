package com.mestims.marvelapp.characters.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.mestims.marvelapp.characters.persistence.CharacterDao
import com.mestims.marvelapp.characters.persistence.CharacterRemoteMediator

class CharactersRepositoryImpl(
    private val dao: CharacterDao,
    private val mediator: CharacterRemoteMediator
) : CharactersRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getCharacters(query: String, pagingConfig: PagingConfig) = Pager(
        config = pagingConfig,
        remoteMediator = mediator.apply { this.query = query }
    ) {
        dao.pagingSource()
    }.flow

    override fun getFavoriteCharacters() = Pager(
        config = PagingConfig(20)
    ) { dao.getFavorites(true) }
        .flow

}