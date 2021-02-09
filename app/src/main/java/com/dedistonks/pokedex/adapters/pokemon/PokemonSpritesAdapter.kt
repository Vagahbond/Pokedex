package com.dedistonks.pokedex.adapters.pokemon

import com.dedistonks.pokedex.adapters.Adapter
import me.sargunvohra.lib.pokekotlin.model.PokemonSprites

object PokemonSpritesAdapter: Adapter<PokemonSprites, com.dedistonks.pokedex.models.PokemonSprites>{
    override fun adapt(source: PokemonSprites): com.dedistonks.pokedex.models.PokemonSprites {
        return com.dedistonks.pokedex.models.PokemonSprites(
            back = source.backDefault,
            front = source.frontDefault,
            backShiny = source.backShiny,
            frontShiny = source.frontShiny,)
    }
}