package com.dedistonks.pokedex.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dedistonks.pokedex.models.Pokemon
import com.dedistonks.pokedex.models.Item


@Database(entities = [Pokemon::class, Item::class], version = 3, exportSchema = false)
abstract class PokedexDatabase : RoomDatabase() {
    companion object {
        var instance: PokedexDatabase? = null

        fun getInstance(context: Context): PokedexDatabase {
            return instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    PokedexDatabase::class.java, "database-name"
                )
                    .fallbackToDestructiveMigration()
                    .build()

        }
    }

    abstract fun pokemonDao(): PokemonDao
    abstract fun itemDao(): ItemDao
}
