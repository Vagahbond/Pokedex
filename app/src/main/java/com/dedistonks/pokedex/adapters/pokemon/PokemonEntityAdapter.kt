package com.dedistonks.pokedex.adapters.pokemon

import com.dedistonks.pokedex.adapters.ReversableAdapter
import com.dedistonks.pokedex.api.PokeAPI
import com.dedistonks.pokedex.models.Pokemon

class PokemonAdapter : ReversableAdapter<Pokemon, Pokemon> {
    override fun adapt(source: Pokemon): Pokemon {
        return Pokemon(
            source.id,
            source.name,
            source.description,
            source.height,
            source.abilities,
            source.types,
            source.sprites,
            source.evolutions,
            source.games
        )
    }

    override fun reverseAdapt(source: Pokemon): Pokemon {
        return Pokemon(
            source.id,
            source.name,
            source.description,
            source.height!!,
            source.abilities,
            source.types,
            source.sprites,
            source.evolutions,
            source.games
        )
    }

}