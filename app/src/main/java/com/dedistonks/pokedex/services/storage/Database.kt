package com.dedistonks.pokedex.services.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
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
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }

            return this.instance!!
        }
    }

    abstract fun pokemonDao(): PokemonDao

}

val MIGRATION_1_2: Migration = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE pokemon "
                    + "ADD abilities TEXT, "
                    + " types TEXT, "
                    + " evolutions TEXT, "
                    + " sprites TEXT, "
                    + " games TEXT; "
        )
    }
}
