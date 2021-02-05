package com.dedistonks.pokedex.adapters.pokemon

import com.dedistonks.pokedex.adapters.Adapter
import com.dedistonks.pokedex.models.Pokemon
import me.sargunvohra.lib.pokekotlin.model.NamedApiResource

class PokemonNamedApiResourceAdapter : Adapter<Pokemon, NamedApiResource> {
    override fun adapt(source: Pokemon): NamedApiResource {
        return NamedApiResource(source.name, "pokemon", source.id)
    }


}