package com.dedistonks.pokedex.services.storage.entities

import androidx.room.*
import com.dedistonks.pokedex.api.PokeAPI
import com.google.gson.Gson

@Entity
@TypeConverters(Pokemon.AbilityConverter::class, Pokemon.SpritesConverter::class, Pokemon.EvolutionsConverter::class)
data class Pokemon(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "height") val height: Int,
    @ColumnInfo(name = "abilities") val abilities: List<String?>,
    @ColumnInfo(name = "types") val types: List<String?>,
    @ColumnInfo(name = "sprites") val sprites: PokeAPI.PokemonSpritesDTO?,
    @ColumnInfo(name = "evolutions") val evolutions: List<PokeAPI.PokemonEvolutionDTO?>,
    @ColumnInfo(name = "games") val games: List<String?>,
) {
    class AbilityConverter {
        companion object {
            @JvmStatic
            @TypeConverter
            fun fromAbilities(abilities: List<String>): String = Gson().toJson(abilities)

            @JvmStatic
            @TypeConverter
            fun toAbilities(json: String): List<String> =
                Gson().fromJson(json, Array<String>::class.java).toList()
        }
    }

    class SpritesConverter {
        companion object {
            @JvmStatic
            @TypeConverter
            fun fromSprites(pokemonSpritesDTO: PokeAPI.PokemonSpritesDTO): String = Gson().toJson(pokemonSpritesDTO)

            @JvmStatic
            @TypeConverter
            fun toSprites(json: String): PokeAPI.PokemonSpritesDTO =
                Gson().fromJson(json, PokeAPI.PokemonSpritesDTO::class.java)
        }
    }

    class EvolutionsConverter {
        companion object {
            @JvmStatic
            @TypeConverter
            fun fromEvolutions(evolutions: List<PokeAPI.PokemonEvolutionDTO?>): String = Gson().toJson(evolutions)

            @JvmStatic
            @TypeConverter
            fun toEvolutions(json: String): List<PokeAPI.PokemonEvolutionDTO?> =
                Gson().fromJson(json, Array<PokeAPI.PokemonEvolutionDTO?>::class.java).toList()
        }
    }
}