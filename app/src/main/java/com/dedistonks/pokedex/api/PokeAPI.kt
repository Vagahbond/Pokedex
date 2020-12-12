package com.dedistonks.pokedex.api

import com.dedistonks.pokedex.utils.Thread
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient
import me.sargunvohra.lib.pokekotlin.model.Item
import me.sargunvohra.lib.pokekotlin.model.NamedApiResource
import java.util.*

class PokeAPI {
    private val api = PokeApiClient()

    fun getItems(offset: Int = 0, limit: Int = 5, callback: (List<NamedApiResource>) -> Unit) {
        Thread.thread {
            val items = api.getItemList(offset, limit)
            callback(items.results)
        }
    }

    fun getItem(id: Int, callback: (Item) -> Unit) {
        Thread.thread {
            val item = api.getItem(id)
            callback(item)
        }
    }

    data class PokemonSpritesDTO(
        val back: String?,
        val front: String?,
        val backShiny: String?,
        val frontShiny: String?,
    )

    data class PokemonEvolutionDTO(val name: String, val sprites: PokemonSpritesDTO)

    data class PokemonDTO(
        val id: Int,
        val name: String,
        val description: String?,
        val height: Int,
        val abilities: List<String>,
        val types: List<String>,
        val sprites: PokemonSpritesDTO,
        val evolutions: List<PokemonEvolutionDTO>,
        val games: List<String>,
    )

    fun getPokemons(offset: Int = 0, limit: Int = 5, callback: (List<NamedApiResource>) -> Unit) {
        Thread.thread {
            val items = api.getPokemonList(offset, limit)
            callback(items.results)
        }
    }

    fun getPokemon(id: Int, callback: (PokemonDTO) -> Unit) {
        Thread.thread {
            val pokemon = api.getPokemon(id)
            val pokedex = api.getPokedex(id)
            val evolutions = api.getEvolutionChain(id)

            val findDescription =
                { language: String -> pokedex.descriptions.find { description -> description.language.name == language }?.description }

            val dto = PokemonDTO(
                id = pokemon.id,
                name = pokemon.name,
                description = findDescription(Locale.getDefault().language)
                    ?: findDescription("en"),
                height = pokemon.height,
                abilities = pokemon.abilities.map { ability -> ability.ability.name },
                types = pokemon.types.map { type -> type.type.name },
                sprites = PokemonSpritesDTO(
                    back = pokemon.sprites.backDefault,
                    front = pokemon.sprites.frontDefault,
                    backShiny = pokemon.sprites.backShiny,
                    frontShiny = pokemon.sprites.frontShiny,
                ),
                evolutions = evolutions.chain.evolvesTo.map { to ->
                    val pokemon = api.getPokemon(to.species.id)

                    PokemonEvolutionDTO(
                        name = pokemon.name,
                        sprites = PokemonSpritesDTO(
                            back = pokemon.sprites.backDefault,
                            front = pokemon.sprites.frontDefault,
                            backShiny = pokemon.sprites.backShiny,
                            frontShiny = pokemon.sprites.frontShiny,
                        ),
                    )
                },
                games = pokemon.gameIndices.map { game -> game.version.name },
            )

            callback(dto)
        }
    }
}
