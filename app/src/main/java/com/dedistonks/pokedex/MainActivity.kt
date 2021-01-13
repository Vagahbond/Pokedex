package com.dedistonks.pokedex

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.dedistonks.pokedex.objects.ObjectListActivity
import com.dedistonks.pokedex.pokemons.PokemonListActivity

class MainActivity : AppCompatActivity() {

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
            val intent = Intent(this, ObjectListActivity::class.java)
            startActivity(intent)
        }
    }

}