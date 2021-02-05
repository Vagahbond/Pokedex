package com.dedistonks.pokedex.adapters.item

import com.dedistonks.pokedex.adapters.Adapter
import com.dedistonks.pokedex.models.Item
import com.dedistonks.pokedex.models.ListAPIResource

class ItemNamedApiResourceAdapter : Adapter<me.sargunvohra.lib.pokekotlin.model.Item, ListAPIResource> {
    override fun adapt(source: me.sargunvohra.lib.pokekotlin.model.Item): ListAPIResource {
        return Item(source.id, source.name )
    }
}