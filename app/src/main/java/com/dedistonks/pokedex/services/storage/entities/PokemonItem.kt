package com.dedistonks.pokedex.services.storage.entities

import androidx.room.*
import com.google.gson.Gson

@Entity
@TypeConverters(PokemonItem.effectsTypeConverter::class)
class PokemonItem(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "effects") val effects: List<String?>,
    @ColumnInfo(name = "spriteUrl") val spriteUrl: String?,
    @ColumnInfo(name = "description") val description: String,
) {
    class effectsTypeConverter {
        companion object {
            @JvmStatic
            @TypeConverter
            fun fromEffects(effects: List<String>): String = Gson().toJson(effects)

            @JvmStatic
            @TypeConverter
            fun toEffects(json: String): List<String> =
                Gson().fromJson(json, Array<String>::class.java).toList()
        }
    }
}