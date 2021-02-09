package com.dedistonks.pokedex.adapters.pokemon

import com.dedistonks.pokedex.adapters.Adapter
import com.dedistonks.pokedex.models.ListAPIResource
import com.dedistonks.pokedex.models.Pokemon
import me.sargunvohra.lib.pokekotlin.model.Name
import me.sargunvohra.lib.pokekotlin.model.NamedApiResource
import java.util.*

object PokemonNamedApiResourceAdapter : Adapter<NamedApiResource, Pokemon> {
    override fun adapt(source: NamedApiResource): Pokemon {
        return Pokemon(source.id, source.name.capitalize(Locale.getDefault()))
    }


}