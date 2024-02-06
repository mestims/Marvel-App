package com.mestims.marvelapp.characters.di

import com.mestims.marvelapp.characters.CharactersViewModel
import com.mestims.marvelapp.characters.api.CharactersApi
import com.mestims.marvelapp.characters.repository.CharactersRepository
import com.mestims.marvelapp.characters.repository.CharactersRepositoryImpl
import com.mestims.marvelapp.characters.usecase.GetCharacterDetailUseCase
import com.mestims.marvelapp.characters.usecase.GetCharacterDetailUseCaseImpl
import com.mestims.marvelapp.characters.usecase.GetCharactersPagingUseCase
import com.mestims.marvelapp.characters.usecase.GetCharactersPagingUseCaseImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val charactersModule = module {
    factory<CharactersApi> { get<Retrofit>().create(CharactersApi::class.java) }

    factory<CharactersRepository> { CharactersRepositoryImpl(get(), get()) }

    factory<GetCharacterDetailUseCase> { GetCharacterDetailUseCaseImpl(get()) }

    factory<GetCharactersPagingUseCase> { GetCharactersPagingUseCaseImpl(get()) }

    viewModel<CharactersViewModel> { CharactersViewModel(get(), get()) }
}