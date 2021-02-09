package com.dedistonks.pokedex.adapters.pokemon

import com.dedistonks.pokedex.models.Pokemon
import me.sargunvohra.lib.pokekotlin.model.PokemonSpecies
import java.util.*

object PokemonAdapter {

    fun adapt(
        source: me.sargunvohra.lib.pokekotlin.model.Pokemon,
        evolutions: List<me.sargunvohra.lib.pokekotlin.model.Pokemon>,
        species: PokemonSpecies):
            Pokemon {

        return Pokemon(
            id = source.id,
            name = source.name.capitalize(Locale.getDefault()),
            description = species.flavorTextEntries.find { description -> description.language.name == "en" }?.flavorText,
            height = source.height,
            abilities = source.abilities.map { ability -> ability.ability.name },
            types = source.types.map { type -> type.type.name },
            sprites = PokemonSpritesAdapter.adapt(source.sprites),
            evolutions = evolutions.map { PokemonEvolutionAdapter.adapt(it) },
            games = source.gameIndices.map { game -> game.version.name },
        )
    }

}