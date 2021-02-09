package com.dedistonks.pokedex.ui.named_resource_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.dedistonks.pokedex.R
import com.dedistonks.pokedex.databinding.ListLoadStateFooterViewItemBinding


class PokedexListLoadStateViewHolder(
        private val binding: ListLoadStateFooterViewItemBinding,
        retry: ()-> Unit
): RecyclerView.ViewHolder(binding.root) {


    init {
        binding.retryButton.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.errorMsg.text = loadState.error.localizedMessage
        }
        binding.progressBar.isVisible = loadState is LoadState.Loading
        binding.retryButton.isVisible = loadState !is LoadState.Loading
        binding.errorMsg.isVisible = loadState !is LoadState.Loading
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): PokedexListLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_load_state_footer_view_item, parent, false)
            val binding = ListLoadStateFooterViewItemBinding.bind(view)
            return PokedexListLoadStateViewHolder(binding, retry)
        }
    }
}