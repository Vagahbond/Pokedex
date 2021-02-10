package com.dedistonks.pokedex.data


import androidx.room.withTransaction
import com.dedistonks.pokedex.api.PokeAPI
import com.dedistonks.pokedex.models.Item
import com.dedistonks.pokedex.models.ListAPIResource
import com.dedistonks.pokedex.models.ListContentType
import com.dedistonks.pokedex.models.Pokemon
import com.dedistonks.pokedex.db.PokedexDatabase
import retrofit2.HttpException
import java.io.IOException


class ResourceRemoteMediator (
        private val service: PokeAPI,
        private val database: PokedexDatabase,
        private val resourceType: ListContentType,
        private val index: Int,
) {

    suspend fun load(): ResourceMediatorResponse {

        val cacheData : ListAPIResource?

        try {
             cacheData = getConcernedResourceFromDatabase()

            cacheData?.let {
                if (cacheData.isComplete()) return ResourceMediatorResponse.Success(cacheData)
            }


            val itemFromAPI = getConcernedResource() // TODO implement response from API to manage error cases


            database.withTransaction {
                updateConcernedResource(itemFromAPI)
            }

            return ResourceMediatorResponse.Success(itemFromAPI)

        } catch (exception: IOException) {
            return ResourceMediatorResponse.Error("An exception occurred.", exception)
        } catch (exception: HttpException) {
            return ResourceMediatorResponse.Error("HHTP error: " + exception.message(), exception)
        }
    }


    private suspend fun getConcernedResource(): ListAPIResource {
        return when(resourceType) {
            ListContentType.POKEMON -> service.getPokemon(index)
            ListContentType.ITEM -> service.getItem(index)
            else -> throw NotImplementedError("Fetching for this resource is not defined in ListRemoteMediator")
        }
    }

    private suspend fun getConcernedResourceFromDatabase(): ListAPIResource{
        return when(resourceType) {
            ListContentType.POKEMON -> database.pokemonDao().getById(index)
            ListContentType.ITEM -> database.itemDao().getById(index)
            else -> throw NotImplementedError("Fetching resource from database not implemented in SingleResourceRemoteMediator")
        }
    }


    private suspend fun updateConcernedResource(resource : ListAPIResource) {
        when(resource) {
            is Pokemon -> database.pokemonDao().insertAll(resource)
            is Item -> database.itemDao().insertAll(resource)
            else -> throw NotImplementedError("Insertion for this resource is not defined in ResourceRemoteMediator")
        }
    }

}


