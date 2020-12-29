package com.dedistonks.pokedex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.dedistonks.pokedex.Adapters.Pokemon.PokemonEntityAdapter
import com.dedistonks.pokedex.api.PokeAPI
import com.dedistonks.pokedex.objects.ObjectListActivity
import com.dedistonks.pokedex.pokemons.PokemonListActivity
import com.dedistonks.pokedex.services.storage.AppDatabase

class MainActivity : AppCompatActivity() {

    private val api: PokeAPI = PokeAPI()
    private val pokemonEntityAdapter: PokemonEntityAdapter = PokemonEntityAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        api.getPokemons(0, 6){ pokemons ->
            pokemons.forEach { pokemon ->
                val pokemonVerification = AppDatabase.getInstance(this.applicationContext).pokemonDao().loadAllByIds(arrayListOf(pokemon.id))

                if(pokemonVerification.isEmpty()) {
                    api.getPokemon(pokemon.id) { pokemonDTO ->
                        AppDatabase.getInstance(this.applicationContext).pokemonDao().insertAll(pokemonEntityAdapter.adapt(pokemonDTO))
                    }
                }
            }
        }

        val navToPokemonsButton : Button = findViewById(R.id.btPokemons)
        val navToObjectsButton : Button = findViewById(R.id.btObjects)

        navToPokemonsButton.setOnClickListener {
            val intent = Intent(this, PokemonListActivity::class.java)
            startActivity(intent)
        }

        navToObjectsButton.setOnClickListener{
            val intent = Intent(this, ObjectListActivity::class.java)
            startActivity(intent)
        }
    }

}