package com.dedistonks.pokedex.data;

import android.util.Log
import androidx.paging.*
import com.dedistonks.pokedex.api.PokeAPI;
import com.dedistonks.pokedex.models.ListAPIResource
import com.dedistonks.pokedex.models.ListContentType
import com.dedistonks.pokedex.models.Pokemon
import com.dedistonks.pokedex.db.PokedexDatabase;
import com.dedistonks.pokedex.models.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher

class PokeAPIRepository(
        private val service : PokeAPI,
        private val database : PokedexDatabase
) {

        companion object {
                private const val NETWORK_PAGE_SIZE = 50
        }

        @ExperimentalPagingApi
        fun getPokemonsStream(): Flow<PagingData<ListAPIResource>> {
                Log.d(this.javaClass.name, "querying all the pokemons.")

                @Suppress("UNCHECKED_CAST") val pagingSourceFactory = { database.pokemonDao().getAll() as PagingSource<Int, ListAPIResource> }

                return Pager(
                        config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
                        remoteMediator = ListRemoteMediator(
                                service,
                                database,
                                ListContentType.POKEMON
                        ),
                        pagingSourceFactory = pagingSourceFactory
                ).flow
        }

        @ExperimentalPagingApi
        fun getItemsStream(): Flow<PagingData<ListAPIResource>> {
                Log.d("PokeAPIRepository", "querying all the items.")

                @Suppress("UNCHECKED_CAST") val pagingSourceFactory = { database.itemDao().getAll() as PagingSource<Int, ListAPIResource> }

                return Pager(
                        config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
                        remoteMediator = ListRemoteMediator(
                                service,
                                database,
                                ListContentType.ITEM
                        ),
                        pagingSourceFactory = pagingSourceFactory
                ).flow
        }

        suspend fun getPokemon(index: Int) : ResourceMediatorResponse {
                Log.d("PokeAPIRepository", "querying pokemon at index ${index}.")

                return ResourceRemoteMediator(service, database, ListContentType.POKEMON, index).load()
        }

        suspend fun getItem(index: Int): ResourceMediatorResponse {
                Log.d("PokeAPIRepository", "querying item at index ${index}.")

                return ResourceRemoteMediator(service, database, ListContentType.ITEM, index).load()
        }
}

