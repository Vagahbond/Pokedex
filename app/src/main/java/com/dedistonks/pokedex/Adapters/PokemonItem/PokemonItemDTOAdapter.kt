package com.dedistonks.pokedex.Adapters.PokemonItem

import android.widget.Adapter
import com.dedistonks.pokedex.Adapters.Pokemon.NamedApiResourceAdapter
import com.dedistonks.pokedex.api.PokeAPI
import me.sargunvohra.lib.pokekotlin.model.Item
import me.sargunvohra.lib.pokekotlin.model.NamedApiResource

class PokemonItemAdapter : com.dedistonks.pokedex.Adapters.Adapter<Item, PokeAPI.ItemDTO> {
    override fun adapt(source: Item): PokeAPI.ItemDTO {
        return PokeAPI.ItemDTO(
            id = source.id
        )
    }
}