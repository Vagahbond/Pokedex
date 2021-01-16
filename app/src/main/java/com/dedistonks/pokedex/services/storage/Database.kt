package com.dedistonks.pokedex.services.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.dedistonks.pokedex.services.storage.dao.PokemonDao
import com.dedistonks.pokedex.services.storage.dao.PokemonItemDao
import com.dedistonks.pokedex.services.storage.entities.Pokemon
import com.dedistonks.pokedex.services.storage.entities.PokemonItem


@Database(entities = arrayOf(Pokemon::class, PokemonItem::class), version = 2, exportSchema = false)
public abstract class AppDatabase : RoomDatabase() {
    companion object {
        var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if(this.instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "database-name"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }

            return this.instance!!
        }
    }

    abstract fun pokemonDao(): PokemonDao
    abstract fun pokemonItemDao(): PokemonItemDao
}
