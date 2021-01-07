package com.dedistonks.pokedex.Adapters.Pokemon

import com.dedistonks.pokedex.Adapters.Adapter
import com.dedistonks.pokedex.api.PokeAPI
import com.dedistonks.pokedex.services.storage.entities.Pokemon

class PokemonEntityAdapter : Adapter<PokeAPI.PokemonDTO, Pokemon> {
    override fun adapt(source: PokeAPI.PokemonDTO): Pokemon {
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

}