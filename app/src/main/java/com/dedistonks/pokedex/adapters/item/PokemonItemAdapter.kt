package com.dedistonks.pokedex.adapters.item

import com.dedistonks.pokedex.adapters.Adapter
import com.dedistonks.pokedex.models.Item
import java.util.*

object PokemonItemAdapter : Adapter<Item, Item> {
    override fun adapt(source: Item): Item {
        return Item(
            source.id,
            source.name.capitalize(Locale.getDefault()),
            source.category,
            source.effects,
            source.spriteUrl,
            source.description
        )
    }

}