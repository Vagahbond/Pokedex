package com.dedistonks.pokedex.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.dedistonks.pokedex.api.PokeAPI
import com.dedistonks.pokedex.models.Item
import com.dedistonks.pokedex.models.ListAPIResource
import com.dedistonks.pokedex.models.ListContentType
import com.dedistonks.pokedex.models.Pokemon
import com.dedistonks.pokedex.db.PokedexDatabase
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException


@OptIn(ExperimentalPagingApi::class)
class ListRemoteMediator (
        private val service: PokeAPI,
        private val database: PokedexDatabase,
        private val resourceType: ListContentType
) : RemoteMediator<Int, ListAPIResource>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, ListAPIResource>): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                state.anchorPosition?.let { position ->
                    state.closestItemToPosition(position)?.id?.div(state.config.pageSize)
                } ?: STARTING_PAGE_INDEX


            }
            LoadType.PREPEND -> {
                val remoteKey = state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.id?.div(state.config.pageSize)
                remoteKey ?: throw InvalidObjectException("Remote key and the prevKey should not be null")

                remoteKey.minus(1 )

            }
            LoadType.APPEND -> {
                val remoteKeys = state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.id?.div(state.config.pageSize)
                remoteKeys ?: throw InvalidObjectException("Remote key should not be null for $loadType")

                remoteKeys.plus(1 )
            }

        }

        try {

            val response = getConcernedResource(page, state.config.pageSize)

            Log.d(this.javaClass.name, "Fetched ${response.size} $resourceType")

            val endOfPaginationReached = response.isEmpty()

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    nukeConcernedResource()
                }

                insertConcernedResource(response)
            }

            return MediatorResult.Success(endOfPaginationReached)

        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }


    }

    private fun nukeConcernedResource() {
        when (resourceType) {
            ListContentType.POKEMON -> database.pokemonDao().nukePokemons()
            ListContentType.ITEM -> database.itemDao().nukeItems()
            else -> throw NotImplementedError("Nuke for this resource is not defined in ListRemoteMediator")
        }
    }

    private suspend fun getConcernedResource(page : Int, pageSize: Int): List<ListAPIResource> {
        Log.d(this.javaClass.name, "Querying from API $pageSize $resourceType from $page on")
        val offset = page * pageSize

        return when(resourceType) {
            ListContentType.POKEMON -> service.getPokemons(offset, pageSize)
            ListContentType.ITEM -> service.getItems(offset, pageSize)
            else -> throw NotImplementedError("Fetching for this resource is not defined in ListRemoteMediator")
        }
    }

    private fun insertConcernedResource(data: List<ListAPIResource>) {
        when(resourceType) {
            ListContentType.POKEMON -> database.pokemonDao().insertAll(pokemons = data.map { it as Pokemon }.toTypedArray() )
            ListContentType.ITEM -> database.itemDao().insertAll(items = data.map { it as Item }.toTypedArray() )
            else -> throw NotImplementedError("Insertion for this resource is not defined in ListRemoteMediator")
        }
    }

    private companion object {
        const val STARTING_PAGE_INDEX = 0
    }
}