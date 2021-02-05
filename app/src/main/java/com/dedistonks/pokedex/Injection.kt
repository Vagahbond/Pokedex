package com.dedistonks.pokedex

import android.content.Context
import androidx.lifecycle.ViewModel
import com.dedistonks.pokedex.api.PokeAPI
import com.dedistonks.pokedex.data.PokeAPIRepository
import com.dedistonks.pokedex.db.PokedexDatabase
import com.dedistonks.pokedex.models.ListContentType
import com.dedistonks.pokedex.ui.ViewModelFactory

/**
 * Class that handles object creation.
 * Like this, objects can be passed as parameters in the constructors and then replaced for
 * testing, where needed.
 */
object Injection {


    private fun providePokedexRepository(context: Context): PokeAPIRepository {
        return PokeAPIRepository(PokeAPI(), PokedexDatabase.getInstance(context))
    }

    fun provideViewModelFactory(context: Context, contentType: ListContentType? = null): ViewModelFactory {
        return ViewModelFactory(providePokedexRepository(context), contentType)
    }

}
