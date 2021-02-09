package com.dedistonks.pokedex.ui.named_resource_list

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter



class PokedexListLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<PokedexListLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: PokedexListLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): PokedexListLoadStateViewHolder {
        return PokedexListLoadStateViewHolder.create(parent, retry)
    }
}