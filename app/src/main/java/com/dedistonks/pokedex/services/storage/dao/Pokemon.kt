package com.dedistonks.pokedex.services.storage.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.dedistonks.pokedex.services.storage.entities.Pokemon

@Dao
interface PokemonDao {
    @Query("SELECT * FROM pokemon")
    fun getAll(): List<Pokemon>

    @Query("SELECT * FROM pokemon WHERE id IN (:pokemonIds)")
    fun loadAllByIds(pokemonIds: IntArray): List<Pokemon>

    @Query("SELECT * FROM pokemon WHERE name LIKE :name")
    fun findByName(name: String): Pokemon

    @Insert
    fun insertAll(vararg pokemon: Pokemon)

    @Delete
    fun delete(user: Pokemon)
}