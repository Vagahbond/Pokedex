package com.dedistonks.pokedex.Adapters.Pokemon

import com.dedistonks.pokedex.Adapters.Adapter
import com.dedistonks.pokedex.api.PokeAPI
import com.dedistonks.pokedex.services.storage.entities.Pokemon

class PokemonDtoAdapter : Adapter<Pokemon, PokeAPI.PokemonDTO> {
    override fun adapt(source: Pokemon): PokeAPI.PokemonDTO {

        return PokeAPI.PokemonDTO(
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