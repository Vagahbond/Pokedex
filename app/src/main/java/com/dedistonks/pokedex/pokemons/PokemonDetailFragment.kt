package com.dedistonks.pokedex.pokemons

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.dedistonks.pokedex.R
import com.dedistonks.pokedex.api.PokeAPI
import com.dedistonks.pokedex.utils.ImageToBitmap

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

            val frontView: ImageView = rootView.findViewById(R.id.ivPokemonFront)
            val backView: ImageView = rootView.findViewById(R.id.ivPokemonBack)
            val sFrontView: ImageView = rootView.findViewById(R.id.ivPokemonShinyFront)
            val sBackView: ImageView = rootView.findViewById(R.id.ivPokemonShinyBack)

            pokemon.sprites.front?.let { ImageToBitmap.from(it) { bitmap -> setImageToComponent(frontView, bitmap) } }
            pokemon.sprites.back?.let { ImageToBitmap.from(it) { bitmap -> setImageToComponent(backView, bitmap) } }
            pokemon.sprites.frontShiny?.let { ImageToBitmap.from(it) { bitmap -> setImageToComponent(sFrontView, bitmap) } }
            pokemon.sprites.backShiny?.let { ImageToBitmap.from(it) { bitmap -> setImageToComponent(sBackView, bitmap) } }

            activity?.let {
                it.runOnUiThread {
                    rootView.findViewById<TextView>(R.id.twName).text = pokemon.name.capitalize()
                    rootView.findViewById<TextView>(R.id.twID).text = pokemon.id.toString()

                    rootView.findViewById<TextView>(R.id.tvDetails).text = pokemon.description
                    rootView.findViewById<TextView>(R.id.tvHeight).text = getString(R.string.height_property,  String.format("%.2f",pokemon.height.toDouble().div(10))) // Yeah, for realm the API gives it in decimeters...

                    rootView.findViewById<TextView>(R.id.tvType1).text = pokemon.types[0].toUpperCase()
                    rootView.findViewById<TextView>(R.id.tvType2).text =  if (pokemon.types.size > 1)  pokemon.types[1].toUpperCase() else  ""

                }
            }
        }
        return rootView
    }

    private fun setImageToComponent(target: ImageView, value: Bitmap) {
        activity?.let {
            it.runOnUiThread{
                target.setImageBitmap(value)
            }
        }
    }


    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
    }
}