package com.dedistonks.pokedex.pokemons

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.widget.NestedScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.dedistonks.pokedex.R


import com.dedistonks.pokedex.api.PokeAPI
import me.sargunvohra.lib.pokekotlin.model.NamedApiResource

/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [PokemonDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class PokemonListActivity : AppCompatActivity() {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false

    private lateinit var  adapter : PokemonRecyclerViewAdapter


    private val viewModel : PokemonListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_list)




//        val toolbar = findViewById<Toolbar>(R.id.toolbar)
//        setSupportActionBar(toolbar)
//        toolbar.title = title

//        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }

        if (findViewById<NestedScrollView>(R.id.pokemon_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }

        adapter  = PokemonRecyclerViewAdapter(this, twoPane)

        viewModel.pokemonsList.observe(this, {
            adapter.submitList(it)
            Log.d("check", "went through the oserver's callback");
            Log.d("check", "nb items :  ${it.count()}");
        })

        findViewById<RecyclerView>(R.id.pokemon_list).adapter = this.adapter
    }

    class PokemonRecyclerViewAdapter(private val parentActivity: PokemonListActivity,
                                     private val twoPane: Boolean) :
            PagedListAdapter<NamedApiResource, PokemonRecyclerViewAdapter.ViewHolder>(DIFF_CALLBACK) {

        private val onClickListener: View.OnClickListener

        private var values = listOf<NamedApiResource>()

        init {

            onClickListener = View.OnClickListener { v ->
                val item = v.tag as NamedApiResource
                if (twoPane) {
                    val fragment = PokemonDetailFragment().apply {
                        arguments = Bundle().apply {
                            putInt(PokemonDetailFragment.ARG_ITEM_ID, item.id)
                        }
                    }
                    parentActivity.supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.pokemon_detail_container, fragment)
                            .commit()
                } else {
                    val intent = Intent(v.context, PokemonDetailActivity::class.java).apply {
                        putExtra(PokemonDetailFragment.ARG_ITEM_ID, item.id)
                    }
                    v.context.startActivity(intent)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.pokemon_list_content, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = getItem(position)
            holder.idView.text = item?.id.toString()
            holder.contentView.text = item?.name

            with(holder.itemView) {
                tag = item
                setOnClickListener(onClickListener)
            }
        }

        override fun getItemCount() = values.size

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val idView: TextView = view.findViewById(R.id.id_text)
            val contentView: TextView = view.findViewById(R.id.content)
        }


        companion object {
            private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<NamedApiResource>() {
                override fun areItemsTheSame(oldItem: NamedApiResource, newItem: NamedApiResource): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: NamedApiResource, newItem: NamedApiResource): Boolean {
                    return oldItem.id == newItem.id
                }
            }
        }
    }
}