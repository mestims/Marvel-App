package com.mestims.marvelapp.characters

import com.mestims.marvelapp.characters.model.Character
import com.mestims.marvelapp.characters.persistence.CharacterDao
import com.mestims.marvelapp.characters.persistence.CharacterRemoteMediator
import com.mestims.marvelapp.characters.persistence.toEntity
import com.mestims.marvelapp.characters.usecase.GetCharactersPagingUseCaseImpl
import com.mestims.marvelapp.characters.util.MainCoroutineRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class CharactersViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private val useCaseMock: GetCharactersPagingUseCaseImpl = mockk(relaxed = true)
    private val daoMock: CharacterDao = mockk(relaxed = true)

    private val viewModel = CharactersViewModel(useCaseMock, daoMock)

    @Test
    fun `when characters is passed to onFavoriteClick, it should be saved`() = runTest {
        coEvery { daoMock.insert(any()) } returns Unit
        val character = Character(1, "name", "desc", "thumb", "resId", false, true)

        viewModel.onFavoriteClick(character)

        coVerify { daoMock.insert(character.toEntity()) }


    }

}