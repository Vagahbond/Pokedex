package com.dedistonks.pokedex.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dedistonks.pokedex.data.PokeAPIRepository
import com.dedistonks.pokedex.models.ListAPIResource
import com.dedistonks.pokedex.models.ListContentType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class PokedexListViewModel(
        private val repository: PokeAPIRepository,
        private val contentType: ListContentType,
        ) : ViewModel() {

    private var items: Flow<PagingData<ListAPIResource>>? = null

    @ExperimentalPagingApi
    fun refreshItems(): Flow<PagingData<ListAPIResource>> {
        Log.d(this.javaClass.name, "Refreshing items.")

        val newItems : Flow<PagingData<ListAPIResource>> =
                when (contentType) {
                    ListContentType.POKEMON -> repository.getPokemonsStream()
                    ListContentType.ITEM -> repository.getItemsStream()
                    else -> throw NotImplementedError("This resource's type data fetching was not implemented in pokedexlistviewModel")
                } .cachedIn(viewModelScope)


        items = newItems
        return newItems
    }



}