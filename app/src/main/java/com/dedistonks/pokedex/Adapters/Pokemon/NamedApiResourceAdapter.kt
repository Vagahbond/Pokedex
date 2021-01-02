package com.dedistonks.pokedex.Adapters.Pokemon

import com.dedistonks.pokedex.Adapters.Adapter
import com.dedistonks.pokedex.services.storage.entities.Pokemon
import me.sargunvohra.lib.pokekotlin.model.NamedApiResource

class NamedApiResourceAdapter : Adapter<Pokemon, NamedApiResource> {
    override fun adapt(source: Pokemon): NamedApiResource {
        return NamedApiResource(source.name, "pokemon", source.id)
    }


}