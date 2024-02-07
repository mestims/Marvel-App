package com.mestims.marvelapp.characters

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import com.mestims.design_system.extensions.openShare
import com.mestims.design_system.extensions.viewBinding
import com.mestims.marvelapp.R
import com.mestims.marvelapp.characters.model.Character
import com.mestims.marvelapp.databinding.FragmentCharacterBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class CharactersFragment : Fragment(R.layout.fragment_character) {

    companion object {

        private const val FAVORITE_MODE_ARG = "FAVORITE_MODE_ARG"
        fun newInstance(favoriteMode: Boolean): CharactersFragment {
            val fragment = CharactersFragment()
            val bundle = bundleOf(FAVORITE_MODE_ARG to favoriteMode)

            fragment.arguments = bundle

            return fragment
        }
    }

    private val binding: FragmentCharacterBinding by viewBinding()
    private val viewModel: CharactersViewModel by viewModel()

    private val charactersAdapter = CharactersAdapter(::onFavoriteClick, ::onShareImageClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupScreenMode()
        setupMenu()
        setupAdapter()
        collectCharacters()
        collectListState()
    }

    private fun setupScreenMode() {
        val favoriteMode = arguments?.getBoolean(FAVORITE_MODE_ARG)
        viewModel.setMode(favoriteMode)
    }

    private fun setupMenu() {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_search, menu)
                val searchItem = menu.findItem(R.id.search)
                val searchView = searchItem.actionView as SearchView
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        viewModel.search(newText)
                        return true
                    }
                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.search -> {
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner)
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

    private fun collectListState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                charactersAdapter.loadStateFlow.collectLatest { state ->

                    binding.tvEmpty.isVisible =
                        state.append is LoadState.NotLoading && state.append.endOfPaginationReached && charactersAdapter.itemCount < 1

                    when (state.refresh) {
                        is LoadState.Loading -> {

                        }

                        is LoadState.NotLoading -> {

                        }

                        is LoadState.Error -> {

                        }

                    }
                }
            }
        }
    }

    private fun onFavoriteClick(character: Character) {
        viewModel.onFavoriteClick(character)
    }

    private fun onShareImageClick(file: File) {
        openShare(file)
    }
}




