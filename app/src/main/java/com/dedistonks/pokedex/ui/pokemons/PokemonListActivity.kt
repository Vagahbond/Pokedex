package com.dedistonks.pokedex.ui.pokemons

import android.os.Bundle
import androidx.core.widget.NestedScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import com.dedistonks.pokedex.Injection
import com.dedistonks.pokedex.R
import com.dedistonks.pokedex.databinding.ActivityPokemonListBinding
import com.dedistonks.pokedex.models.ListContentType
import com.dedistonks.pokedex.ui.PokedexListAdapter
import com.dedistonks.pokedex.ui.PokedexListLoadStateAdapter
import com.dedistonks.pokedex.ui.PokedexListViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch



/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [PokemonDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
@ExperimentalPagingApi
class PokemonListActivity : AppCompatActivity() {


    private lateinit var binding: ActivityPokemonListBinding
    private lateinit var viewModel: PokedexListViewModel

    private lateinit var adapter: PokedexListAdapter

    private var refreshJob: Job? = null

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
                this,
                Injection.provideViewModelFactory(this, ListContentType.POKEMON)
        ).get(PokedexListViewModel::class.java)

        if (findViewById<NestedScrollView>(R.id.pokemon_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }

        adapter = PokedexListAdapter(twoPane, this)


        // Set a separator in the list
        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)

        val pokemonsList = findViewById<RecyclerView>(R.id.pokemon_list)

        pokemonsList.addItemDecoration(decoration)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.title = title


        initAdapter(pokemonsList)

        refreshPokemons()

        initRefresh(pokemonsList)

        binding.retryButton.setOnClickListener { adapter.retry() }

//        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }

    }

    private fun refreshPokemons() {
        refreshJob?.cancel()
        refreshJob = lifecycleScope.launch {
            viewModel.refreshItems().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun initAdapter(list: RecyclerView) {

        list.adapter = adapter.withLoadStateHeaderAndFooter(
            header = PokedexListLoadStateAdapter { adapter.retry() },
            footer = PokedexListLoadStateAdapter { adapter.retry() },
        )


        adapter.addLoadStateListener { loadState ->
            list.isVisible = loadState.source.refresh is LoadState.NotLoading
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error

            val errorState = loadState.source.append as? LoadState.Error
                    ?: loadState.source.prepend as? LoadState.Error
                    ?: loadState.append as? LoadState.Error
                    ?: loadState.prepend as? LoadState.Error

            errorState?.let {
                Toast.makeText(
                        this,
                        "\uD83D\uDE28 Wooops ${it.error}",
                        Toast.LENGTH_LONG
                ).show()

            }
        }
    }

    private fun initRefresh(list: RecyclerView) {
        // Scroll to top when the list is refreshed from network.


        lifecycleScope.launch {
            adapter.loadStateFlow
                    // Only emit when REFRESH LoadState for RemoteMediator changes.
                    .distinctUntilChangedBy { it.refresh }
                    // Only react to cases where Remote REFRESH completes i.e., NotLoading.
                    .filter { it.refresh is LoadState.NotLoading }
                    .collect { list.scrollToPosition(0) }
        }
    }


//    class PokemonRecyclerViewAdapter(private val parentActivity: PokemonListActivity,
//                                     private val twoPane: Boolean) :
//            PagedListAdapter<NamedApiResource, PokemonRecyclerViewAdapter.ViewHolder>(DIFF_CALLBACK) {
//
//        private val onClickListener: View.OnClickListener
//
//        private var values = listOf<NamedApiResource>()
//
//        init {
//
//            onClickListener = View.OnClickListener { v ->
//                val item = v.tag as NamedApiResource
//                if (twoPane) {
//                    val fragment = PokemonDetailFragment().apply {
//                        arguments = Bundle().apply {
//                            putInt(PokemonDetailFragment.ARG_ITEM_ID, item.id)
//                        }
//                    }
//                    parentActivity.supportFragmentManager
//                            .beginTransaction()
//                            .replace(R.id.pokemon_detail_container, fragment)
//                            .commit()
//                } else {
//                    val intent = Intent(v.context, PokemonDetailActivity::class.java).apply {
//                        putExtra(PokemonDetailFragment.ARG_ITEM_ID, item.id)
//                    }
//                    v.context.startActivity(intent)
//                }
//            }
//        }
//
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//            val view = LayoutInflater.from(parent.context)
//                    .inflate(R.layout.pokemon_list_content, parent, false)
//            return ViewHolder(view)
//        }
//
//        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//            val item = getItem(position)
//            holder.idView.text = item?.id.toString()
//            holder.contentView.text = item?.name
//
//            with(holder.itemView) {
//                tag = item
//                setOnClickListener(onClickListener)
//            }
//        }
//
//        override fun getItemCount() = values.size
//
//        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//            val idView: TextView = view.findViewById(R.id.id_text)
//            val contentView: TextView = view.findViewById(R.id.content)
//        }
//
//
//        companion object {
//            private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<NamedApiResource>() {
//                override fun areItemsTheSame(oldItem: NamedApiResource, newItem: NamedApiResource): Boolean {
//                    return oldItem.id == newItem.id
//                }
//
//                override fun areContentsTheSame(oldItem: NamedApiResource, newItem: NamedApiResource): Boolean {
//                    return oldItem.id == newItem.id
//                }
//            }
//        }
//    }
}