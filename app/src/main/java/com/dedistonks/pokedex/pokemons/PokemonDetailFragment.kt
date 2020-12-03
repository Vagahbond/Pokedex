package com.dedistonks.pokedex.pokemons

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dedistonks.pokedex.R
import com.dedistonks.pokedex.api.PokeAPI
import com.dedistonks.pokedex.utils.ImageToBitmap
import java.util.*

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
                    rootView.findViewById<TextView>(R.id.twName).text = pokemon.name.capitalize(Locale.getDefault())
                    rootView.findViewById<TextView>(R.id.twID).text = pokemon.id.toString()

                    rootView.findViewById<TextView>(R.id.tvDetails).text = pokemon.description
                    rootView.findViewById<TextView>(R.id.tvHeight).text = getString(R.string.height_property,  String.format("%.2f",pokemon.height.toDouble().div(10))) // Yeah, for realm the API gives it in decimeters...

                    rootView.findViewById<TextView>(R.id.tvType1).text = pokemon.types[0].toUpperCase(Locale.getDefault())
                    rootView.findViewById<TextView>(R.id.tvType2).text =  if (pokemon.types.size > 1)  pokemon.types[1].toUpperCase(Locale.getDefault()) else  ""


                    rootView.findViewById<TextView>(R.id.tvAbility1).text = pokemon.abilities[0]
                    rootView.findViewById<TextView>(R.id.tvAbility2).text = if (pokemon.abilities.size > 1)  pokemon.abilities[1] else ""
                    rootView.findViewById<TextView>(R.id.tvAbility3).text = if (pokemon.abilities.size > 2)  pokemon.abilities[2] else ""
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


//    class AbilitiesRecyclerViewAdapter(private val values: List<String>) :
//            RecyclerView.Adapter<AbilitiesRecyclerViewAdapter.ViewHolder>() {
//
//
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//            val view = LayoutInflater.from(parent.context)
//                    .inflate(R.layout.abilities_list_content, parent, false)
//
//            return ViewHolder(view)
//        }
//
//        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//            val item = values[position]
//            holder.tvName.text = item
//            Log.d("Ability" , item)
//        }
//
//        override fun getItemCount() = values.size
//
//        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//            val tvName: TextView = view.findViewById(R.id.tvAbilityName)
//        }
//    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
    }
}