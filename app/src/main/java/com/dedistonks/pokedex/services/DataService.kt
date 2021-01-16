package com.dedistonks.pokedex.services

import android.content.Context
import com.dedistonks.pokedex.Adapters.Pokemon.PokemonAdapter
import com.dedistonks.pokedex.Adapters.Pokemon.PokemonNamedApiResourceAdapter
import com.dedistonks.pokedex.Adapters.PokemonItem.ItemNamedApiRessourceAdapter
import com.dedistonks.pokedex.Adapters.PokemonItem.PokemonItemAdapter
import com.dedistonks.pokedex.api.PokeAPI
import com.dedistonks.pokedex.services.storage.AppDatabase
import me.sargunvohra.lib.pokekotlin.model.NamedApiResource
import kotlin.concurrent.thread

class DataService {
    private val api = PokeAPI()

    private val connectionService = ConnectionService()

    private val pokemonItemAdapter = PokemonItemAdapter()
    private val itemApiResourceAdapter = ItemNamedApiRessourceAdapter()

    private val pokemonAdapter = PokemonAdapter()
    private val pokemonNamedApiResourceAdapter = PokemonNamedApiResourceAdapter()

    fun nukePokemons(context: Context) {
        val pokemonDao = AppDatabase.getInstance(context).pokemonDao()
        pokemonDao.nukePokemons()
    }

    fun nukesPokemonItems(context: Context) {
        val pokemonItemDao = AppDatabase.getInstance(context).pokemonItemDao()
        pokemonItemDao.nukePokemonItems()
    }

    fun getPokemons(
        offset: Int = 0,
        limit: Int = 5,
        context: Context,
        callback: (List<NamedApiResource>) -> Unit
    ) {
        if (connectionService.hasInternetAccess(context)) {
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
                    .map(pokemonNamedApiResourceAdapter::adapt)
            )
        }
    }

    fun getPokemonDTO(id: Int, context: Context, callback: (PokeAPI.PokemonDTO?) -> Unit) {
        if (connectionService.hasInternetAccess(context)) {
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
                callback(pokemonAdapter.reverseAdapt(pokemon[0]))
            }

            callback(null)
        }

    }

    private fun getPokemonsFromApi(
        offset: Int = 0,
        limit: Int = 5,
        context: Context,
        callback: (List<NamedApiResource>) -> Unit
    ) {
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
                    .insertAll(pokemonAdapter.adapt(pokemonDTO))
            }
        }
    }

    fun getItems(
        offset: Int = 0,
        limit: Int = 5,
        context: Context,
        callback: (List<NamedApiResource>) -> Unit
    ) {
        if (connectionService.hasInternetAccess(context)) {
            getItemsFromApi(offset, limit, context, callback)
        } else {
            getItemsFromDatabase(offset, limit, context, callback)
        }
    }

    private fun getItemsFromDatabase(
        offset: Int,
        limit: Int,
        context: Context,
        callback: (List<NamedApiResource>) -> Unit
    ) {
        thread {
            callback(
                AppDatabase.getInstance(context).pokemonItemDao()
                    .loadAllByIds((offset..limit).toList())
                    .map(itemApiResourceAdapter::adapt)
            )
        }
    }

    private fun getItemsFromApi(
        offset: Int,
        limit: Int,
        context: Context,
        callback: (List<NamedApiResource>) -> Unit
    ) {
        api.getItems(offset, limit) { pokemonItems ->
            pokemonItems.forEach { pokemonItem ->
                insertOnInexistantItem(pokemonItem.id, context)
                callback(pokemonItems)
            }

        }
    }

    private fun insertOnInexistantItem(id: Int, context: Context) {
        val pokemonItemDao = AppDatabase.getInstance(context).pokemonItemDao()
        val pokemonItemVerification = pokemonItemDao.loadAllByIds(listOf(id))

        if (pokemonItemVerification.isEmpty()) {
            api.getItem(id) { pokemonItemDTO ->
                pokemonItemDao.insertAll(pokemonItemAdapter.adapt(pokemonItemDTO))
            }
        }
    }

    fun getItemDTO(id: Int, context: Context, callback: (PokeAPI.PokemonItemDTO?) -> Unit) {
        if (this.connectionService.hasInternetAccess(context)) {
            getItemDtoFromApi(id, context, callback);
        } else {
            getItemDtoFromDatabase(id, context, callback)
        }
    }

    private fun getItemDtoFromDatabase(
        id: Int,
        context: Context,
        callback: (PokeAPI.PokemonItemDTO?) -> Unit
    ) {
        thread {
            val pokemonItemDao = AppDatabase.getInstance(context).pokemonItemDao()
            val pokemonItem = pokemonItemDao.loadAllByIds(arrayListOf(id))
            if (!pokemonItem.isEmpty()) {
                callback(
                    PokeAPI.PokemonItemDTO(
                        pokemonItem[0].id,
                        pokemonItem[0].name,
                        pokemonItem[0].category,
                        pokemonItem[0].effects,
                        pokemonItem[0].spriteUrl,
                        pokemonItem[0].description,
                    )
                )
            } else {
                callback(null)
            }
        }
    }

    private fun getItemDtoFromApi(
        id: Int,
        context: Context,
        callback: (PokeAPI.PokemonItemDTO) -> Unit
    ) {
        api.getItem(id) { pokemonItemDTO ->
            insertOnInexistantItem(id, context)
            callback(pokemonItemDTO)
        }
    }
}
