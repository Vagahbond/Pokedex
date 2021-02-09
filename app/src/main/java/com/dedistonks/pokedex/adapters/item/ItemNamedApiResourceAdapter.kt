package com.dedistonks.pokedex.adapters.item

import com.dedistonks.pokedex.adapters.Adapter
import com.dedistonks.pokedex.models.Item
import com.dedistonks.pokedex.models.ListAPIResource
import me.sargunvohra.lib.pokekotlin.model.NamedApiResource
import java.util.*

object ItemNamedApiResourceAdapter : Adapter<NamedApiResource, Item> { // make it so that its used
    override fun adapt(source: NamedApiResource): Item {
        return Item(source.id, source.name.capitalize(Locale.getDefault()) )
    }
}