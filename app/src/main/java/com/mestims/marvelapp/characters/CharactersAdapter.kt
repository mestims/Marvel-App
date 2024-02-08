package com.mestims.marvelapp.characters

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mestims.design_system.R
import com.mestims.design_system.extensions.exportImage
import com.mestims.marvelapp.characters.model.Character
import com.mestims.marvelapp.databinding.HolderProductBinding
import java.io.File


class CharactersAdapter(
    private val onFavoriteClick: (Character) -> Unit,
    private val onShareImageClick: (File) -> Unit
) :
    PagingDataAdapter<Character, CharactersAdapter.ViewHolder>(diffCallback) {

    override fun getItemViewType(position: Int) = CHARACTER_VIEW_TYPE

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it, onFavoriteClick, onShareImageClick)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val holderBinding = HolderProductBinding.inflate(inflater, parent, false)
        return ViewHolder(holderBinding)
    }

    inner class ViewHolder(
        private val binding: HolderProductBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            character: Character,
            onFavoriteClick: (Character) -> Unit,
            onShareImageClick: (File) -> Unit
        ) {

            binding.tvCollapsedCharacterName.text = character.name
            binding.tvExpandedCharacterName.text = character.name
            binding.tvExpandedCharacterDesc.text = character.description
                .takeIf { it?.isNotEmpty() == true } ?: "No description found"

            setupFavoriteButton(binding.ivCollapsedFavorite, onFavoriteClick, character)
            setupFavoriteButton(binding.ivExpandedFavorite, onFavoriteClick, character)

            handleGroupVisibility(character.isExpanded)

            binding.ivExpandedShare.setOnClickListener {
                val image = binding.ivExpandedCharacterImage.exportImage()
                onShareImageClick(image)
            }

            Glide.with(itemView)
                .load(character.thumbnail)
                .fallback(R.drawable.ic_image_error)
                .into(binding.ivCollapsedCharacterImage)

            Glide.with(itemView)
                .load(character.thumbnail)
                .fallback(R.drawable.ic_image_error)
                .into(binding.ivExpandedCharacterImage)


            binding.clCharactersRoot.setOnClickListener {

                character.isExpanded = binding.groupCollapsed.isVisible

                val animations = getAnimations()

                animations.first.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        handleGroupVisibility(character.isExpanded)
                        animations.second.start()
                    }
                })
                animations.first.start()
            }

        }

        private fun setupFavoriteButton(
            view: AppCompatImageView,
            onFavoriteClick: (Character) -> Unit,
            character: Character
        ) {
            view.apply {
                isSelected = character.isFavorite
                setOnClickListener {
                    character.isFavorite = !character.isFavorite
                    setFavoriteViewIcon(character.isFavorite)
                    onFavoriteClick(character)
                }
            }
        }

        private fun setFavoriteViewIcon(isFavorite: Boolean) {
            binding.ivCollapsedFavorite.isSelected = isFavorite
            binding.ivExpandedFavorite.isSelected = isFavorite
        }

        private fun getAnimations() = Pair(getCloseAnimation(), getOpenAnimation())

        private fun getCloseAnimation() = ObjectAnimator
            .ofFloat(binding.cvCharacter, "scaleY", 1f, 0f)
            .apply {
                interpolator = DecelerateInterpolator()
                duration = 250
            }

        private fun getOpenAnimation() = ObjectAnimator
            .ofFloat(binding.cvCharacter, "scaleY", 0f, 1f)
            .apply {
                interpolator = AccelerateDecelerateInterpolator()
                duration = 250
            }

        private fun handleGroupVisibility(isExpanded: Boolean) {
            binding.groupCollapsed.isVisible = !isExpanded
            binding.groupExpanded.isVisible = isExpanded
        }
    }

    companion object {
        const val CHARACTER_VIEW_TYPE = 1

        private val diffCallback = object : DiffUtil.ItemCallback<Character>() {
            override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem == newItem
            }
        }
    }


}

