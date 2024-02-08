package com.mestims.marvelapp.usecase

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.mestims.marvelapp.characters.persistence.CharacterDao
import com.mestims.marvelapp.characters.persistence.CharacterRemoteMediator
import com.mestims.marvelapp.characters.repository.CharactersRepository
import com.mestims.marvelapp.characters.usecase.GetCharactersPagingUseCaseImpl
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalPagingApi::class)
class GetCharactersPagingUseCaseImplTest {


    private val repoMock: CharactersRepository = mockk()

    private val useCase = GetCharactersPagingUseCaseImpl(repoMock)
    private val remoteMediatorMock: CharacterRemoteMediator = mockk(relaxed = true)
    private val daoMock: CharacterDao = mockk(relaxed = true)


    @Test
    fun `when invoke useCase with query, getCharacters should be called`() = runTest {
        val config = PagingConfig(20)
        coEvery { repoMock.getCharacters(any(), any()) } returns Pager(
            config,
            remoteMediator = remoteMediatorMock
        ) { daoMock.pagingSource("") }.flow

        useCase.invoke(
            query = "",
            pagingConfig = PagingConfig(20)
        ).first()

        verify(exactly = 1) { repoMock.getCharacters("", any()) }
    }

    @Test
    fun `when invoke useCase without query, getFavorites should be called`() = runTest {
        val config = PagingConfig(20)
        coEvery { repoMock.getFavoriteCharacters() } returns Pager(
            config = config
        ) { daoMock.getFavorites(true) }
            .flow

        useCase.invoke(
            pagingConfig = PagingConfig(20)
        ).first()

        verify(exactly = 1) { repoMock.getFavoriteCharacters() }
    }


}