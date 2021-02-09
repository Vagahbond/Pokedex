package com.dedistonks.pokedex.ui.items

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dedistonks.pokedex.data.PokeAPIRepository
import com.dedistonks.pokedex.models.Item

class ItemDetailViewModel(
        private val repository: PokeAPIRepository,
) : ViewModel() {

    private var selectedIndex: Int = 0

    var currentItemResult =  MutableLiveData<Item>()


    suspend fun loadItem(index: Int): Item {
        Log.d(this.javaClass.name, "Loading item ${index}.")
        val lastResult = currentItemResult.value

        if (selectedIndex == index && lastResult != null) {
            return lastResult
        }

        selectedIndex = index

        val newResult  = repository.getItem(index)

        currentItemResult.value = newResult

        return newResult
    }
}