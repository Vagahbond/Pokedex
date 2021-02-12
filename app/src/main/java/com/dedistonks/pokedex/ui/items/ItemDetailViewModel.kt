package com.dedistonks.pokedex.ui.items

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dedistonks.pokedex.data.PokeAPIRepository
import com.dedistonks.pokedex.data.ResourceMediatorResponse
import com.dedistonks.pokedex.models.Item

class ItemDetailViewModel(
        private val repository: PokeAPIRepository,
) : ViewModel() {

    private var selectedIndex: Int = 0


    var error = MutableLiveData<String>()


    var currentItemResult =  MutableLiveData<Item>()


    suspend fun loadItem(index: Int): Unit {
        Log.d(this.javaClass.name, "Loading item ${index}.")
        val lastResult = currentItemResult.value

        if (selectedIndex == index && lastResult != null) {
            return
        }

        selectedIndex = index

        when (val newResult  = repository.getItem(index)) {
            is ResourceMediatorResponse.Success -> {
                currentItemResult.value = newResult.data as Item
                error.value = null
            }
            is ResourceMediatorResponse.Error -> {
                currentItemResult.value = null
                error.value = newResult.errorString
            }
        }



    }
}