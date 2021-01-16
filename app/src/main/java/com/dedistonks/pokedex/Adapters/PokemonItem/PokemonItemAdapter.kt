package com.dedistonks.pokedex.Adapters.PokemonItem

import com.dedistonks.pokedex.Adapters.Adapter
import com.dedistonks.pokedex.Adapters.ReversableAdapter
import com.dedistonks.pokedex.api.PokeAPI
import com.dedistonks.pokedex.services.storage.entities.PokemonItem

class PokemonItemAdapter : ReversableAdapter<PokeAPI.PokemonItemDTO, PokemonItem> {
    override fun adapt(source: PokeAPI.PokemonItemDTO): PokemonItem {
        return PokemonItem(
            source.id,
            source.name,
            source.category,
            source.effects,
            source.spriteUrl,
            source.description
        )
    }

    override fun reverseAdapt(source: PokemonItem): PokeAPI.PokemonItemDTO {
        return PokeAPI.PokemonItemDTO(
            source.id,
            source.name,
            source.category,
            source.effects,
            source.spriteUrl,
            source.description
        )
    }
}