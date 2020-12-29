package com.dedistonks.pokedex.Adapters.Pokemon

import android.util.Log
import com.dedistonks.pokedex.Adapters.Adapter
import com.dedistonks.pokedex.api.PokeAPI
import com.dedistonks.pokedex.services.storage.entities.Pokemon

class PokemonEntityAdapter : Adapter<PokeAPI.PokemonDTO, Pokemon> {
    override fun adapt(source: PokeAPI.PokemonDTO): Pokemon {
        Log.d("doggo", source.toString())
        return Pokemon(source.id, source.name, source.description, source.height)
    }

}