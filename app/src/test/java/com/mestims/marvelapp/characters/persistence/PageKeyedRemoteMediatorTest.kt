package com.mestims.marvelapp.characters.persistence

import android.os.Build.VERSION_CODES.Q
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.Room
import androidx.room.withTransaction
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mestims.marvelapp.characters.api.CharactersApi
import com.mestims.marvelapp.characters.util.CharacterFactory
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.GlobalContext.stopKoin
import org.robolectric.annotation.Config
import kotlin.test.assertTrue

@ExperimentalPagingApi
@OptIn(ExperimentalCoroutinesApi::class)
@Config(sdk = [Q])
@RunWith(AndroidJUnit4::class)
class PageKeyedRemoteMediatorTest {

    private val mockCharacters = CharacterFactory.createApiResponse()
    private val mockApi = mockk<CharactersApi>(relaxed = true)

    private val mockDb = Room.databaseBuilder(
        ApplicationProvider.getApplicationContext(),
        CharacterDatabase::class.java, "character-database"
    ).build()

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun refreshLoadReturnsSuccessResultWhenMoreDataIsPresent() = runTest {
        coEvery { mockApi.getCharacters(mapOf()) } returns mockCharacters

        val remoteMediator = CharacterRemoteMediator(mockDb, mockApi)
        val pagingState = PagingState<Int, CharacterEntity>(
            listOf(),
            null,
            PagingConfig(20),
            0
        )
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)

        assertTrue { result is RemoteMediator.MediatorResult.Success }

        mockDb.withTransaction {
            mockDb.clearAllTables()
        }
    }

    @Test
    fun refreshLoadSuccessAndEndOfPaginationWhenNoMoreData() = runTest {
        val remoteMediator = CharacterRemoteMediator(mockDb, mockApi)

        val pagingState = PagingState<Int, CharacterEntity>(
            listOf(),
            null,
            PagingConfig(20),
            0
        )

        val result = remoteMediator.load(LoadType.REFRESH, pagingState)

        assertTrue { result is RemoteMediator.MediatorResult.Success }
        assertTrue { (result as RemoteMediator.MediatorResult.Success).endOfPaginationReached }

        mockDb.withTransaction {
            mockDb.clearAllTables()
        }
    }
}