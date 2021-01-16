package com.dedistonks.pokedex.pokemons


import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.toLiveData
import com.dedistonks.pokedex.api.PokemonListDataSourceFactory
import me.sargunvohra.lib.pokekotlin.model.NamedApiResource

class PokemonListViewModel  : ViewModel() {
  // var pokemonsList = PokemonListDataSourceFactory().toLiveData(20, 0)
  var pokemonsList = LivePagedListBuilder(PokemonListDataSourceFactory(), 20).build()
}