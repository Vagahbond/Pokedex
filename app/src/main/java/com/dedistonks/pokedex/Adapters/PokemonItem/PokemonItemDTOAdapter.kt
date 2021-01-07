package com.dedistonks.pokedex.Adapters.PokemonItem

import com.dedistonks.pokedex.Adapters.Adapter
import com.dedistonks.pokedex.api.PokeAPI
import me.sargunvohra.lib.pokekotlin.model.Item

class PokemonItemDTOAdapter : Adapter<Item, PokeAPI.ItemDTO> {
    override fun adapt(source: Item): PokeAPI.ItemDTO {
        return PokeAPI.ItemDTO(
                id = source.id,
                name = if(source.names.filter { name -> name.language.name == "en" }.isNotEmpty())
                    source.names.filter { name -> name.language.name == "en" }[0].name
                    else source.name,
                category = source.category.name,
                effects = source.effectEntries.map { effectEntry -> effectEntry.shortEffect },
                description = source.flavorTextEntries[0].text,
                spriteUrl = source.sprites.default
            )
    }
}