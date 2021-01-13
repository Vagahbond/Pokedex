package com.dedistonks.pokedex.services.storage.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.dedistonks.pokedex.services.storage.entities.PokemonItem


@Dao
interface PokemonItemDao {
    @Query("SELECT * FROM PokemonItem")
    fun getAll(): List<PokemonItem>

    @Query("SELECT * FROM PokemonItem WHERE id IN (:pokemonItemIds)")
    fun loadAllByIds(pokemonItemIds: List<Int>): List<PokemonItem>

    @Query("SELECT * FROM PokemonItem WHERE name LIKE :name")
    fun findByName(name: String): PokemonItem

    @Insert
    fun insertAll(vararg pokemon: PokemonItem)

    @Delete
    fun delete(pokemon: PokemonItem)

    @Query("DELETE FROM PokemonItem")
    fun nukePokemonItems()
}