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
import com.dedistonks.pokedex.services.DataService
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private val dataService: DataService = DataService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dataService.getItems(0, 5, this.applicationContext) {
            Log.d("doggo", it.toString())
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