package com.dedistonks.pokedex.services

import android.content.Context
import android.util.Log
import com.dedistonks.pokedex.Adapters.Pokemon.NamedApiResourceAdapter
import com.dedistonks.pokedex.Adapters.Pokemon.PokemonDtoAdapter
import com.dedistonks.pokedex.Adapters.Pokemon.PokemonEntityAdapter
import com.dedistonks.pokedex.api.PokeAPI
import com.dedistonks.pokedex.services.storage.AppDatabase
import me.sargunvohra.lib.pokekotlin.model.NamedApiResource
import kotlin.concurrent.thread

class DataService {
    private val pokemonEntityAdapter = PokemonEntityAdapter();
    private val api = PokeAPI()
    private val connectionService = ConnectionService()
    private val pokemonDtoAdapter = PokemonDtoAdapter()
    private val namedApiResourceAdapter = NamedApiResourceAdapter()

    fun nukePokemons(context: Context) {
        val pokemonDao = AppDatabase.getInstance(context).pokemonDao()
        pokemonDao.nukePokemons()
    }

    fun getPokemons(
        offset: Int = 0,
        limit: Int = 5,
        context: Context,
        callback: (List<NamedApiResource>) -> Unit
    ) {
        if (connectionService.hasUserInternet(context)) {
            getPokemonsFromApi(offset, limit, context, callback)
        } else {
            getPokemonsFromDatabase(offset, limit, context, callback)

        }
    }

    private fun getPokemonsFromDatabase(
        offset: Int,
        limit: Int,
        context: Context,
        callback: (List<NamedApiResource>) -> Unit
    ) {
        thread {
            val pokemonDao = AppDatabase.getInstance(context).pokemonDao()
            callback(
                pokemonDao.loadAllByIds((offset..limit).toList())
                    .map(namedApiResourceAdapter::adapt)
            )
        }
    }

    fun getPokemonDTO(id: Int, context: Context, callback: (PokeAPI.PokemonDTO?) -> Unit) {

        if (connectionService.hasUserInternet(context)) {
            getPokemonDTOFromApi(id, context, callback)
        } else {
            getPokemonDTOFromDatabase(id, context, callback)
        }
    }


    private fun getPokemonDTOFromApi(
        id: Int,
        context: Context,
        callback: (PokeAPI.PokemonDTO) -> Unit
    ) {
        api.getPokemon(id) {
            insertOnInexistantPokemon(id, context)
            callback(it)
        }
    }

    private fun getPokemonDTOFromDatabase(
        id: Int,
        context: Context,
        callback: (PokeAPI.PokemonDTO?) -> Unit
    ) {
        thread {
            val pokemonDao = AppDatabase.getInstance(context).pokemonDao()

            val pokemon = pokemonDao.loadAllByIds(arrayListOf(id))
            if (!pokemon.isEmpty()) {
                callback(pokemonDtoAdapter.adapt(pokemon[0]))
            }

            callback(null)
        }

    }

    private fun getPokemonsFromApi(
        offset: Int = 0,
        limit: Int = 5,
        context: Context,
        callback: (List<NamedApiResource>) -> Unit
    ): Unit {
        api.getPokemons(offset, limit) { pokemons ->
            pokemons.forEach { pokemon ->
                insertOnInexistantPokemon(pokemon.id, context)
            }
            callback(pokemons)
        }
    }

    private fun insertOnInexistantPokemon(id: Int, context: Context) {
        val pokemonVerification = AppDatabase.getInstance(context).pokemonDao()
            .loadAllByIds(arrayListOf(id))

        if (pokemonVerification.isEmpty()) {
            api.getPokemon(id) { pokemonDTO ->
                AppDatabase.getInstance(context).pokemonDao()
                    .insertAll(pokemonEntityAdapter.adapt(pokemonDTO))
            }
        }
    }
}
