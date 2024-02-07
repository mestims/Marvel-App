package com.mestims.marvelapp.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.mestims.marvelapp.characters.model.Character
import com.mestims.marvelapp.characters.persistence.CharacterDao
import com.mestims.marvelapp.characters.persistence.toEntity
import com.mestims.marvelapp.characters.usecase.GetCharactersPagingUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val DELAY_SEARCH_TIME = 500L

private const val PAGE_SIZE = 20

private const val DEBOUNCE_TIME = 300L

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class CharactersViewModel(
    private val getCharactersPagingUseCase: GetCharactersPagingUseCase,
    private val dao: CharacterDao
) : ViewModel() {

    private val _query = MutableStateFlow("")
    private var favoriteMode = false
    private var coroutineJob: Job? = null

    val pagingDataFlow = _query.flatMapLatest {
        _query.debounce(DEBOUNCE_TIME)
            .flatMapLatest { query ->
                if (favoriteMode) {
                    getCharactersPagingUseCase(pagingConfig = PagingConfig(pageSize = PAGE_SIZE))
                } else {
                    getCharactersPagingUseCase(
                        query = query,
                        pagingConfig = PagingConfig(pageSize = PAGE_SIZE)
                    )
                }

            }
            .cachedIn(viewModelScope)
    }

    fun search(newText: String?) {
        coroutineJob?.cancel()
        coroutineJob = viewModelScope.launch {
            delay(DELAY_SEARCH_TIME)
            _query.update {
                newText.orEmpty()
            }
        }
    }

    fun onFavoriteClick(character: Character) {
        viewModelScope.launch {
            character.id?.let {
                dao.insert(character.toEntity())
            }
        }
    }

    fun setMode(favoriteMode: Boolean?) {
        this.favoriteMode = favoriteMode ?: false
    }
}