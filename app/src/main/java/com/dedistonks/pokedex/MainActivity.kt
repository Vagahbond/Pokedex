package com.dedistonks.pokedex

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.ExperimentalPagingApi
import com.dedistonks.pokedex.ui.items.ItemListActivity
import com.dedistonks.pokedex.ui.pokemons.PokemonListActivity

class MainActivity : AppCompatActivity() {

    @ExperimentalPagingApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navToPokemonsButton : Button = findViewById(R.id.btPokemons)
        val navToObjectsButton : Button = findViewById(R.id.btObjects)

        navToPokemonsButton.setOnClickListener {
            val intent = Intent(this, PokemonListActivity::class.java)
            startActivity(intent)
        }

        navToObjectsButton.setOnClickListener{
            val intent = Intent(this, ItemListActivity::class.java)

            startActivity(intent)
        }
    }

}