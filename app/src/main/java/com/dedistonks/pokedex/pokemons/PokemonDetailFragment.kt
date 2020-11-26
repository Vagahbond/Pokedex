package com.dedistonks.pokedex.pokemons

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.CollapsingToolbarLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.dedistonks.pokedex.R
import com.dedistonks.pokedex.api.PokeAPI

/**
 * A fragment representing a single Pokemon detail screen.
 * This fragment is either contained in a [PokemonListActivity]
 * in two-pane mode (on tablets) or a [PokemonDetailActivity]
 * on handsets.
 */
class PokemonDetailFragment : Fragment() {
    //TODO : manage the 0 case.
    /**
     * The pokemon we are currently displaying
     */
    private var pokemonID: Int = 0

    private val api: PokeAPI = PokeAPI()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                // Load the dummy content specified by the fragment
                // arguments. In a real-world scenario, use a Loader
                // to load content from a content provider.
                    this.pokemonID = it.getInt(ARG_ITEM_ID)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.pokemon_detail, container, false)

        api.getPokemon(this.pokemonID){ pokemon ->
            activity.let {
                it?.runOnUiThread {
                    rootView.findViewById<ImageView>(R.id.ivPokemonFront).setImageURI(Uri.parse(pokemon.sprites.front))
                    rootView.findViewById<ImageView>(R.id.ivPokemonBack).setImageURI(Uri.parse(pokemon.sprites.back))
                    rootView.findViewById<ImageView>(R.id.ivPokemonShinyFront).setImageURI(Uri.parse(pokemon.sprites.frontShiny))
                    rootView.findViewById<ImageView>(R.id.ivPokemonShinyBack).setImageURI(Uri.parse(pokemon.sprites.backShiny))

                    rootView.findViewById<TextView>(R.id.twName).text = pokemon.name
                    rootView.findViewById<TextView>(R.id.twID).text = pokemon.id.toString()

                    rootView.findViewById<TextView>(R.id.tvDetails).text = pokemon.description
                    rootView.findViewById<TextView>(R.id.tvType1).text = pokemon.types[0]
                    rootView.findViewById<TextView>(R.id.tvType1).text =  if (pokemon.types.size > 1)  pokemon.types[1] else  ""
                }
            }
        }
        return rootView
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
    }
}