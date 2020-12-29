package com.dedistonks.pokedex.services.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dedistonks.pokedex.services.storage.dao.PokemonDao
import com.dedistonks.pokedex.services.storage.entities.Pokemon

@Database(entities = arrayOf(Pokemon::class), version = 1, exportSchema = false)
public abstract class AppDatabase : RoomDatabase() {
    companion object {
        var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if(this.instance == null) {
                instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, "database-name"
                ).build()
            }

            return this.instance!!
        }
    }

    abstract fun pokemonDao(): PokemonDao

}
