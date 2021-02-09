package com.dedistonks.pokedex.api

import android.util.Log
import com.dedistonks.pokedex.adapters.item.ItemAdapter
import com.dedistonks.pokedex.adapters.item.ItemNamedApiResourceAdapter
import com.dedistonks.pokedex.adapters.pokemon.PokemonAdapter
import com.dedistonks.pokedex.adapters.pokemon.PokemonNamedApiResourceAdapter
import com.dedistonks.pokedex.models.*
import kotlinx.coroutines.*
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient
import me.sargunvohra.lib.pokekotlin.model.ChainLink
import me.sargunvohra.lib.pokekotlin.model.EvolutionChain
import me.sargunvohra.lib.pokekotlin.model.PokemonSpecies


class PokeAPI {
    private val api = PokeApiClient()


    suspend fun getItems(offset: Int = 0, limit: Int = 5) = withContext(Dispatchers.IO) {
        api.getItemList(offset, limit).results.map { ItemNamedApiResourceAdapter.adapt(it) }
    }

    suspend fun getItem(id: Int): Item = withContext(Dispatchers.IO) {
            ItemAdapter.adapt(api.getItem(id))
    }

    suspend fun getPokemons(offset: Int = 0, limit: Int = 5) = withContext(Dispatchers.IO) {
        api.getPokemonList(offset, limit).results.map { PokemonNamedApiResourceAdapter.adapt(it) }
    }

    suspend fun getPokemon(id: Int): Pokemon = withContext(Dispatchers.IO) {

        val pokemon = api.getPokemon(id)
        val species = api.getPokemonSpecies(pokemon.species.id)

        val evolutionChain = extractEvolutionChain(api.getEvolutionChain(species.evolutionChain.id).chain).map { api.getPokemon(it.id) }

        Log.d(this.javaClass.name, "Evolutions found for  ${pokemon.name} are ${evolutionChain.size}.")

        PokemonAdapter.adapt(pokemon, evolutionChain, species)
    }

    private fun extractEvolutionChain(chain: ChainLink, ): List<me.sargunvohra.lib.pokekotlin.model.Pokemon> {
        Log.d(this.javaClass.name, "extracting ${chain.species.name}.")
        val pokemonList = listOf(api.getPokemon(chain.species.id)).toMutableList()

        val evolutions = chain.evolvesTo


        evolutions.forEach {
            pokemonList += extractEvolutionChain(it)

        }


        return pokemonList
    }
}
