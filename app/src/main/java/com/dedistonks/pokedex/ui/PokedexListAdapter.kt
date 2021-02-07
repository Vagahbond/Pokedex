package com.dedistonks.pokedex.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.dedistonks.pokedex.R
import com.dedistonks.pokedex.models.ListAPIResource
import com.dedistonks.pokedex.ui.pokemons.PokemonDetailFragment
import com.dedistonks.pokedex.ui.pokemons.PokemonDetailActivity
import com.dedistonks.pokedex.ui.pokemons.PokemonListActivity

// TODO: could implement separators logic here
@OptIn(ExperimentalPagingApi::class)
class PokedexListAdapter(private var twoPane: Boolean, private var parentActivity: AppCompatActivity) : PagingDataAdapter<ListAPIResource, ViewHolder>(RESOURCE_COMPARATOR) {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d(this.javaClass.name, "view holder created")
        return PokedexListViewHolder.create(parent, parentActivity, twoPane)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.pokedex_list_content
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).let {
            Log.d(this.javaClass.name, "adapting : $it")
            (holder as PokedexListViewHolder).bind(it)
        }
    }

    companion object {
        private val RESOURCE_COMPARATOR = object : DiffUtil.ItemCallback<ListAPIResource>() {
            override fun areItemsTheSame(
                oldItem: ListAPIResource,
                newItem: ListAPIResource
            ): Boolean {
                return oldItem.id == newItem.id && oldItem::class == newItem::class
            }

            override fun areContentsTheSame(
                oldItem: ListAPIResource,
                newItem: ListAPIResource
            ): Boolean {
                return newItem.id == oldItem.id && newItem.name.equals(oldItem.name)
            }
        }
    }



}