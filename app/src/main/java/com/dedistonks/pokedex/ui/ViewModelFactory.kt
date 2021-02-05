package com.dedistonks.pokedex.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dedistonks.pokedex.data.PokeAPIRepository
import com.dedistonks.pokedex.models.ListContentType
import com.dedistonks.pokedex.ui.items.ItemDetailViewModel
import com.dedistonks.pokedex.ui.pokemons.PokemonDetailViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(
        private val repository: PokeAPIRepository,
        private val contentType: ListContentType?
) : ViewModelProvider.Factory
{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        when {
            modelClass.isAssignableFrom(PokedexListViewModel::class.java) -> {

                contentType?: throw IllegalArgumentException(
                        "A known content type must be provided for a list ViewModel."
                )

                @Suppress("UNCHECKED_CAST")
                return PokedexListViewModel(repository, contentType) as T
            }
            modelClass.isAssignableFrom(PokemonDetailViewModel::class.java) -> {

                @Suppress("UNCHECKED_CAST")
                return PokemonDetailViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ItemDetailViewModel::class.java) -> {

                @Suppress("UNCHECKED_CAST")
                return ItemDetailViewModel(repository) as T
            }
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}