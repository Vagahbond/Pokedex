package com.dedistonks.pokedex.Adapters.PokemonItem

import com.dedistonks.pokedex.Adapters.Adapter
import com.dedistonks.pokedex.services.storage.entities.PokemonItem
import me.sargunvohra.lib.pokekotlin.model.NamedApiResource

class ItemNamedApiRessourceAdapter : Adapter<PokemonItem, NamedApiResource> {
    override fun adapt(source: PokemonItem): NamedApiResource {
        return NamedApiResource(source.name, "item", source.id)
    }
}