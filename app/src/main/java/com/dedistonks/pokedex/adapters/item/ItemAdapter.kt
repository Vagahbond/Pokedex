package com.dedistonks.pokedex.adapters.item

import com.dedistonks.pokedex.adapters.Adapter
import com.dedistonks.pokedex.models.Item

object ItemAdapter : Adapter<me.sargunvohra.lib.pokekotlin.model.Item, Item> {
    override fun adapt(source: me.sargunvohra.lib.pokekotlin.model.Item): Item {
        return Item(
                id = source.id,
                name = if(source.names.filter { name -> name.language.name == "en" }.isNotEmpty())
                    source.names.filter { name -> name.language.name == "en" }[0].name
                    else source.name,
                category = source.category.name,
                effects = source.effectEntries.map { effectEntry -> effectEntry.shortEffect },
                description = source.flavorTextEntries.find { description -> description.language.name == "en" }?.text,
                spriteUrl = source.sprites.default
            )
    }
}