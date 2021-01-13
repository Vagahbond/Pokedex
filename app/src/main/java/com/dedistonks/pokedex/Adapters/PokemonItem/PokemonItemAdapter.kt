package com.dedistonks.pokedex.Adapters.PokemonItem

import com.dedistonks.pokedex.Adapters.Adapter
import com.dedistonks.pokedex.api.PokeAPI
import com.dedistonks.pokedex.services.storage.entities.PokemonItem

class PokemonItemAdapter : Adapter<PokeAPI.PokemonItemDTO, PokemonItem> {
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
}