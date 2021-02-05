package com.dedistonks.pokedex.api

import android.util.Log
import com.dedistonks.pokedex.adapters.item.ItemAdapter
import com.dedistonks.pokedex.models.*
import kotlinx.coroutines.*
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient
import me.sargunvohra.lib.pokekotlin.model.NamedApiResourceList

class PokeAPI {
    private val itemAdapter = ItemAdapter()
    private val api = PokeApiClient()


//    suspend fun getItems(offset: Int = 0, limit: Int = 5): List<ListAPIResource> {
//        Log.d(this.javaClass.name, "Fetching $limit items from $offset")
//        val coroutineScope = CoroutineScope(Dispatchers.Main)
//        var res: List<ListAPIResource> = emptyList()
//        coroutineScope.launch {
//
//            withContext(Dispatchers.IO) {
//
//
//                val items: NamedApiResourceList = api.getItemList(offset, limit)
//                res = items.results.map { Item(it.id, it.name) }
//                Log.d(this.javaClass.name, "TEST 1")
//
//
//
//            }
//
//        }
//
//        Log.d(this.javaClass.name, "TEST 2")
//        return res
//
//    }
    suspend fun getItems(offset: Int = 0, limit: Int = 5) = withContext(Dispatchers.IO) {
        api.getItemList(offset, limit).results.map { Item(it.id, it.name) }
    }

    suspend fun getItem(id: Int): Item {
        return withContext(Dispatchers.IO) {
            itemAdapter.adapt(api.getItem(id))
        }
    }


//    data class PokemonSpritesDTO(
//        val back: String?,
//        val front: String?,
//        val backShiny: String?,
//        val frontShiny: String?,
//    )

//    data class PokemonEvolutionDTO(val name: String, val sprites: PokemonSpritesDTO)
//
//    data class PokemonDTO(
//        val id: Int,
//        val name: String,
//        val description: String?,
//        val height: Int,
//        val abilities: List<String?>,
//        val types: List<String?>,
//        val sprites: PokemonSpritesDTO?,
//        val evolutions: List<PokemonEvolutionDTO?>,
//        val games: List<String?>,
//    )

//    data class PokemonItemDTO(
//        val id: Int,
//        val name: String,
//        val category: String,
//        val effects: List<String?>,
//        val spriteUrl: String?,
//        val description: String,
//    )

//    suspend fun getPokemons(offset: Int = 0, limit: Int = 5): List<ListAPIResource> {
//        return withContext(Dispatchers.IO) {
//            val res: List<ListAPIResource> = emptyList()
//            launch {
//                val items = api.getPokemonList(offset, limit)
//                items.results.map { Pokemon(it.id, it.name) }
//            }
//            res
//        }
//
//    }

    suspend fun getPokemons(offset: Int = 0, limit: Int = 5) = withContext(Dispatchers.IO) {
        api.getPokemonList(offset, limit).results.map { Pokemon(it.id, it.name) }
    }

    suspend fun getPokemon(id: Int): Pokemon {

    //TODO use an adapter yo
        return withContext(Dispatchers.IO) {
            val pokemon = api.getPokemon(id)
            val pokedex = api.getPokedex(id)
            val evolutions = api.getEvolutionChain(id)

            Pokemon(
                    id = pokemon.id,
                    name = pokemon.name,
                    description = pokedex.descriptions.find { description -> description.language.name == "en" }!!.description,
                    height = pokemon.height,
                    abilities = pokemon.abilities.map { ability -> ability.ability.name },
                    types = pokemon.types.map { type -> type.type.name },
                    sprites = PokemonSprites(
                            back = pokemon.sprites.backDefault,
                            front = pokemon.sprites.frontDefault,
                            backShiny = pokemon.sprites.backShiny,
                            frontShiny = pokemon.sprites.frontShiny,
                    ),
                    evolutions = evolutions.chain.evolvesTo.map { to ->
                        val pokemonEvolution = api.getPokemon(to.species.id)

                        PokemonEvolution(
                                name = pokemonEvolution.name,
                                sprites = PokemonSprites(
                                        back = pokemonEvolution.sprites.backDefault,
                                        front = pokemonEvolution.sprites.frontDefault,
                                        backShiny = pokemonEvolution.sprites.backShiny,
                                        frontShiny = pokemonEvolution.sprites.frontShiny,
                                ),
                        )
                    },
                    games = pokemon.gameIndices.map { game -> game.version.name },
            )
        }

    }
}
