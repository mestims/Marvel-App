package com.mestims.marvelapp.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mestims.marvelapp.databinding.HolderLoadMoreStateBinding

class ListLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<ListLoadStateAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = HolderLoadMoreStateBinding.inflate(inflater, parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        loadState: LoadState
    ) = holder.bind(loadState)

    inner class ViewHolder(
        private val binding: HolderLoadMoreStateBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(loadState: LoadState) = with(binding) {
            progressBarLoadingMore.isVisible = loadState is LoadState.Loading
            textTryAgain.isVisible = loadState is LoadState.Error
            textTryAgain.setOnClickListener { retry() }
        }
    }
}