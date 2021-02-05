package com.dedistonks.pokedex.models

import androidx.room.*
import com.google.gson.Gson


@Entity(tableName = "pokemons")
@TypeConverters(Pokemon.AbilityConverter::class, Pokemon.SpritesConverter::class, Pokemon.EvolutionsConverter::class)
data class Pokemon (
    @PrimaryKey override val id: Int,
    @ColumnInfo(name = "name") override val name: String,
    @ColumnInfo(name = "description") val description: String? = null,
    @ColumnInfo(name = "height") val height: Int? = null,
    @ColumnInfo(name = "abilities") val abilities: List<String?> = emptyList(),
    @ColumnInfo(name = "types") val types: List<String?> = emptyList(),
    @ColumnInfo(name = "sprites") val sprites: PokemonSprites? = null,
    @ColumnInfo(name = "evolutions") val evolutions: List<PokemonEvolution?> = emptyList(),
    @ColumnInfo(name = "games") val games: List<String?> = emptyList(),
) : ListAPIResource(ListContentType.POKEMON) {

    override fun isComplete(): Boolean {
        return !this.description.isNullOrEmpty()
                && this.height != null
                && this.abilities.isNotEmpty()
                && this.types.isNotEmpty()
                && this.sprites != null
                && this.evolutions.isNotEmpty()
                && this.games.isNotEmpty()
    }


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
            fun fromSprites(pokemonSpritesDTO: PokemonSprites): String = Gson().toJson(pokemonSpritesDTO)

            @JvmStatic
            @TypeConverter
            fun toSprites(json: String): PokemonSprites =
                Gson().fromJson(json, PokemonSprites::class.java)
        }
    }

    class EvolutionsConverter {
        companion object {
            @JvmStatic
            @TypeConverter
            fun fromEvolutions(evolutions: List<PokemonEvolution?>): String = Gson().toJson(evolutions)

            @JvmStatic
            @TypeConverter
            fun toEvolutions(json: String): List<PokemonEvolution?> =
                Gson().fromJson(json, Array<PokemonEvolution?>::class.java).toList()
        }
    }


}