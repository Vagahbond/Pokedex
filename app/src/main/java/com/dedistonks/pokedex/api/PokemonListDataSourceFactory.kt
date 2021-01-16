package com.dedistonks.pokedex.api

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import me.sargunvohra.lib.pokekotlin.model.NamedApiResource

class PokemonListDataSourceFactory : DataSource.Factory<Int, NamedApiResource>() {

    val sourceLiveData = MutableLiveData<PokemonDataSource>()
    var latestSource: PokemonDataSource =  PokemonDataSource()
    override fun create(): DataSource<Int, NamedApiResource> {
        Log.d("check", "went through the datasource factory")
        latestSource = PokemonDataSource()
        sourceLiveData.postValue(latestSource)
        return latestSource
    }

}