package com.dedistonks.pokedex.ui.pokemons

import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.dedistonks.pokedex.Injection
import com.dedistonks.pokedex.R
import com.dedistonks.pokedex.databinding.PokemonDetailBinding
import com.dedistonks.pokedex.models.Pokemon
import com.dedistonks.pokedex.utils.ImageToBitmap
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * A fragment representing a single Pokemon detail screen.
 * This fragment is either contained in a [PokemonListActivity]
 * in two-pane mode (on tablets) or a [PokemonDetailActivity]
 * on handsets.
 */
class PokemonDetailFragment : Fragment() {
    /**
     * The pokemon we are currently displaying
     */


    private lateinit var binding: PokemonDetailBinding
    private lateinit var viewModel: PokemonDetailViewModel  // TODO:  set all viewmodels to be inited this wae

    private var loadJob: Job? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        context?.let {
            viewModel = ViewModelProvider(this, Injection.provideViewModelFactory(it))
                    .get(PokemonDetailViewModel::class.java)
        }

        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                // Load the dummy content specified by the fragment
                // arguments. In a real-world scenario, use a Loader
                // to load content from a content provider.
                    load(it.getInt(ARG_ITEM_ID))
            }
        }

        viewModel.currentPokemonResult.observe(this,) {
            bindPokemon(it)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = PokemonDetailBinding.inflate(layoutInflater)

        return binding.root
    }

    private fun load(index: Int) {
        loadJob?.cancel()
        loadJob = lifecycleScope.launch {
            viewModel.loadPokemon(index)
        }
    }

    private fun setImageToComponent(target: ImageView, value: Bitmap) {
        activity?.let {
            it.runOnUiThread{
                target.setImageBitmap(value)
            }
        }
    }

    private fun bindPokemon(pokemon: Pokemon) {
        binding.twID.text = getString(R.string.id_property, pokemon.id)
        binding.twName.text = pokemon.name

        binding.tvDetails.text = pokemon.description
        binding.tvHeight.text = getString(R.string.height_property, pokemon.height?.times(10))

        if (pokemon.abilities.isNotEmpty()) binding.tvAbility3.text = pokemon.abilities[0]
        if (pokemon.abilities.size >= 2) binding.tvAbility2.text = pokemon.abilities[1]
        if (pokemon.abilities.size >= 3) binding.tvAbility1.text = pokemon.abilities[2]


        if (pokemon.types.isNotEmpty()) binding.tvType2.text = pokemon.types[0]
        if (pokemon.types.size >= 2) binding.tvType1.text = pokemon.types[1]

        pokemon.sprites?.front?.let{ url ->
            ImageToBitmap.from(url,) {
                setImageToComponent(binding.ivPokemonFront, it)
            }
        }

        pokemon.sprites?.frontShiny?.let{ url ->
            ImageToBitmap.from(url,) {
                setImageToComponent(binding.ivPokemonShinyFront, it)
            }
        }

        pokemon.sprites?.back?.let{ url ->
            ImageToBitmap.from(url,) {
                setImageToComponent(binding.ivPokemonBack, it)
            }
        }

        pokemon.sprites?.backShiny?.let{ url ->
            ImageToBitmap.from(url,) {
                setImageToComponent(binding.ivPokemonShinyBack, it)
            }
        }
    }

    companion object {
        const val ARG_ITEM_ID = "pokemon_id"
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

}