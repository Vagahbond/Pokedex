package com.dedistonks.pokedex.db

import androidx.paging.PagingSource
import androidx.room.*
import com.dedistonks.pokedex.models.ListAPIResource
import com.dedistonks.pokedex.models.Pokemon

@Dao
interface PokemonDao {

    // TODO : optimize
    @Query("SELECT * FROM pokemons")
    fun getAll(): PagingSource<Int, Pokemon>

    @Query("SELECT * FROM pokemons WHERE id = (:Id) LIMIT 1")
    fun getById(Id: Int) : Pokemon

//    @Query("SELECT * FROM pokemons WHERE id IN (:pokemonIds)")
//    fun getAllByIds(pokemonIds: List<Int>): List<Pokemon>

    @Query("SELECT * FROM pokemons WHERE name LIKE :name")
    fun findByName(name: String): Pokemon

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg pokemons: Pokemon)

    @Delete
    fun delete(pokemon: Pokemon)

    @Query("DELETE FROM pokemons")
    fun nukePokemons()
}