package com.dedistonks.pokedex.ui.pokemons

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dedistonks.pokedex.data.PokeAPIRepository
import com.dedistonks.pokedex.models.Pokemon

class PokemonDetailViewModel(
        private val repository: PokeAPIRepository,
) : ViewModel() {

    private var selectedIndex: Int = 0

    var currentPokemonResult =  MutableLiveData<Pokemon>()


    suspend fun loadPokemon(index: Int): Pokemon {
        val lastResult = currentPokemonResult.value

        if (selectedIndex == index && lastResult != null) {
            return lastResult
        }

        selectedIndex = index

        val newResult  = repository.getPokemon(index)

        currentPokemonResult.value = newResult

        return newResult
    }
}