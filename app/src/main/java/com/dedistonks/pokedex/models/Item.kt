package com.dedistonks.pokedex.models

import androidx.room.*
import com.google.gson.Gson

@Entity(tableName = "items")
@TypeConverters(Item.EffectsTypeConverter::class)
data class Item(
    @PrimaryKey override val id: Int,
    @ColumnInfo(name = "name") override val name: String,
    @ColumnInfo(name = "category") val category: String? = null,
    @ColumnInfo(name = "effects") val effects: List<String?> = emptyList(),
    @ColumnInfo(name = "spriteUrl") val spriteUrl: String? = null,
    @ColumnInfo(name = "description") val description: String? = null,
) : ListAPIResource(ListContentType.ITEM) {

    override fun isComplete(): Boolean {
        return !this.category.isNullOrEmpty()
                && this.effects.isNotEmpty()
                && !this.spriteUrl.isNullOrEmpty()
                && !this.description.isNullOrEmpty()
    }

    class EffectsTypeConverter {
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