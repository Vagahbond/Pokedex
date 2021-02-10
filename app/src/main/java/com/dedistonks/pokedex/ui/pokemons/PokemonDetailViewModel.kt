package com.dedistonks.pokedex.ui.pokemons

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dedistonks.pokedex.data.PokeAPIRepository
import com.dedistonks.pokedex.data.ResourceMediatorResponse
import com.dedistonks.pokedex.models.Item
import com.dedistonks.pokedex.models.Pokemon

class PokemonDetailViewModel(
        private val repository: PokeAPIRepository,
) : ViewModel() {

    private var selectedIndex: Int = 0

    private var error = MutableLiveData<String>()

    var currentPokemonResult =  MutableLiveData<Pokemon>()


    suspend fun loadPokemon(index: Int): Unit {
        Log.d(this.javaClass.name, "Loading item ${index}.")
        val lastResult = currentPokemonResult.value

        if (selectedIndex == index && lastResult != null) {
            return
        }

        selectedIndex = index

        when (val newResult  = repository.getPokemon(index)) {
            is ResourceMediatorResponse.Success -> {
                currentPokemonResult.value = newResult.data as Pokemon
                error.value = null
            }
            is ResourceMediatorResponse.Error -> {
                currentPokemonResult.value = null
                error.value = newResult.errorString
            }
        }
    }




}