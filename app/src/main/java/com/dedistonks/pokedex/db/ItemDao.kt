package com.dedistonks.pokedex.db

import androidx.paging.PagingSource
import androidx.room.*
import com.dedistonks.pokedex.models.Item
import com.dedistonks.pokedex.models.ListAPIResource
import com.dedistonks.pokedex.models.Pokemon


@Dao
interface ItemDao {
//    @Query("SELECT * FROM ITEMS")
//    fun getAll(): List<Item>

//    @Query("SELECT * FROM ITEMS WHERE id IN (:itemIds)")
//    fun loadAllByIds(itemIds: List<Int>): List<Pokemon>

    @Query("SELECT * FROM ITEMS WHERE ID IN (:id) LIMIT 1")
    suspend fun getById(id: Int): Item

    @Query("SELECT * FROM ITEMS WHERE name LIKE :name")
    suspend fun findByName(name: String): Item

    @Query("SELECT * FROM ITEMS")
    fun getAll(): PagingSource<Int, Item>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg items: Item)

    @Delete
    suspend fun delete(item: Item)

    @Query("DELETE FROM ITEMS")
    suspend fun nukeItems()
}