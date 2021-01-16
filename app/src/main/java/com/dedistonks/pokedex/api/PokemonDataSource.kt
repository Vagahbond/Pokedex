package com.dedistonks.pokedex.api

import android.util.Log
import androidx.paging.PositionalDataSource
import me.sargunvohra.lib.pokekotlin.model.NamedApiResource

class PokemonDataSource : PositionalDataSource<NamedApiResource>() {
    private val pokeApi : PokeAPI = PokeAPI()

    override fun loadInitial(
        params: LoadInitialParams,
        callback: LoadInitialCallback<NamedApiResource>
    ) {
        Log.d("check", "went through the datasource")
        pokeApi.getPokemons(params.requestedStartPosition, params.pageSize) { callback.onResult(it, params.requestedStartPosition, params.pageSize) }
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<NamedApiResource>) {
        pokeApi.getPokemons(params.startPosition, params.loadSize) { callback.onResult(it) }
    }


}