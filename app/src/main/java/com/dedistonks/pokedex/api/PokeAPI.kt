package com.dedistonks.pokedex.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient
import me.sargunvohra.lib.pokekotlin.model.Item
import me.sargunvohra.lib.pokekotlin.model.NamedApiResource
import me.sargunvohra.lib.pokekotlin.model.Pokemon

class PokeAPI {
    private val api = PokeApiClient()

    private fun thread(callback: () -> Unit) {
        GlobalScope.launch(Dispatchers.IO) { callback() }
    }

    fun getItems(offset: Int = 0, limit: Int = 5, callback: (List<NamedApiResource>) -> Unit) {
        thread {
            val items = api.getItemList(offset, limit)
            callback(items.results)
        }
    }

    fun getItem(id: Int, callback: (Item) -> Unit) {
        thread {
            val item = api.getItem(id)
            callback(item)
        }
    }

    fun getPokemons(offset: Int = 0, limit: Int = 5, callback: (List<NamedApiResource>) -> Unit) {
        thread {
            val items = api.getPokemonList(offset, limit)
            callback(items.results)
        }
    }

    fun getPokemon(id: Int, callback: (Pokemon) -> Unit) {
        thread {
            val pokemon = api.getPokemon(id)
            callback(pokemon)
        }
    }
}
