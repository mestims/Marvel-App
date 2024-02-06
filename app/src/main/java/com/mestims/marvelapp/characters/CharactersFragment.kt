package com.mestims.marvelapp.characters

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.mestims.design_system.extensions.viewBinding
import com.mestims.marvelapp.R
import com.mestims.marvelapp.characters.model.Character
import com.mestims.marvelapp.databinding.FragmentCharacterBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharactersFragment : Fragment(R.layout.fragment_character) {

    private val binding: FragmentCharacterBinding by viewBinding()
    private val viewModel: CharactersViewModel by viewModel()

    private val charactersAdapter = CharactersAdapter(::onFavoriteClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        collectCharacters()
    }

    private fun setupAdapter() {
        with(binding.rvCharacters) {
            val concatAdapter = charactersAdapter.withLoadStateFooter(
                footer = ListLoadStateAdapter {
                    charactersAdapter.retry()
                }
            )
            adapter = concatAdapter
        }
    }

    private fun collectCharacters() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.pagingDataFlow.collect { pagingData ->
                    charactersAdapter.submitData(pagingData)
                }
            }
        }
    }

    private fun onFavoriteClick(character: Character) {
        viewModel.onFavoriteClick(character)
    }
}

