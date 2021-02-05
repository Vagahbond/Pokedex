package com.dedistonks.pokedex.adapters.item

import com.dedistonks.pokedex.adapters.ReversableAdapter
import com.dedistonks.pokedex.api.PokeAPI
import com.dedistonks.pokedex.models.Item

class PokemonItemAdapter : ReversableAdapter<Item, Item> {
    override fun adapt(source: Item): Item {
        return Item(
            source.id,
            source.name,
            source.category,
            source.effects,
            source.spriteUrl,
            source.description
        )
    }

    override fun reverseAdapt(source: Item): Item {
        return Item(
            source.id,
            source.name,
            source.category,
            source.effects,
            source.spriteUrl,
            source.description
        )
    }
}