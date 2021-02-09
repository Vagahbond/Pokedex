package com.dedistonks.pokedex.adapters.pokemon

import com.dedistonks.pokedex.adapters.Adapter
import com.dedistonks.pokedex.models.PokemonEvolution
import me.sargunvohra.lib.pokekotlin.model.Pokemon
import java.util.*


object PokemonEvolutionAdapter: Adapter<Pokemon, PokemonEvolution> {
    override fun adapt(source: Pokemon): PokemonEvolution {
        return PokemonEvolution(
            id = source.id,
            name = source.name.capitalize(Locale.getDefault()),
            sprite = source.sprites.frontDefault
        )
    }


}