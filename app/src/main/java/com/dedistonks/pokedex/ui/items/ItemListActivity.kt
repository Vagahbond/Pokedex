package com.dedistonks.pokedex.ui.items

import android.os.Bundle
import android.util.Log
import androidx.core.widget.NestedScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.Toolbar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.map
import androidx.recyclerview.widget.DividerItemDecoration
import com.dedistonks.pokedex.Injection
import com.dedistonks.pokedex.R
import com.dedistonks.pokedex.databinding.ActivityItemListBinding
import com.dedistonks.pokedex.models.ListContentType
import com.dedistonks.pokedex.ui.PokedexListAdapter
import com.dedistonks.pokedex.ui.PokedexListLoadStateAdapter
import com.dedistonks.pokedex.ui.PokedexListViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [ItemDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
@ExperimentalPagingApi
class ItemListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityItemListBinding
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
        binding = ActivityItemListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (findViewById<NestedScrollView>(R.id.object_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }

        adapter = PokedexListAdapter(twoPane, this)


        viewModel = ViewModelProvider(
                this,
                Injection.provideViewModelFactory(this, ListContentType.ITEM),
        ).get(PokedexListViewModel::class.java)


        // Set a separator in the list
        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)

        val itemList = findViewById<RecyclerView>(R.id.object_list)

        itemList.addItemDecoration(decoration)



        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.title = title

        initAdapter(itemList)

        refreshItems()

//        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }

    }


    private fun refreshItems() {
        refreshJob?.cancel()
        refreshJob = lifecycleScope.launch {
            viewModel.refreshItems().collectLatest { liveData ->
                adapter.submitData(liveData)
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

//    private fun setupRecyclerView(recyclerView: RecyclerView) {
//        recyclerView.adapter = SimpleItemRecyclerViewAdapter(this, DummyContent.ITEMS, twoPane)
//    }
//
//    class SimpleItemRecyclerViewAdapter(
//            private val parentActivity: ItemListActivity,
//            private val values: List<DummyContent.DummyItem>,
//            private val twoPane: Boolean
//    ) :
//        RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {
//
//        private val onClickListener: View.OnClickListener
//
//        init {
//            onClickListener = View.OnClickListener { v ->
//                val item = v.tag as DummyContent.DummyItem
//                if (twoPane) {
//                    val fragment = ItemDetailFragment().apply {
//                        arguments = Bundle().apply {
//                            putString(ItemDetailFragment.ARG_ITEM_ID, item.id)
//                        }
//                    }
//                    parentActivity.supportFragmentManager
//                        .beginTransaction()
//                        .replace(R.id.object_detail_container, fragment)
//                        .commit()
//                } else {
//                    val intent = Intent(v.context, ItemDetailActivity::class.java).apply {
//                        putExtra(ItemDetailFragment.ARG_ITEM_ID, item.id)
//                    }
//                    v.context.startActivity(intent)
//                }
//            }
//        }
//
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//            val view = LayoutInflater.from(parent.context)
//                .inflate(R.layout.item_list_content, parent, false)
//            return ViewHolder(view)
//        }
//
//        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//            val item = values[position]
//            holder.idView.text = item.id
//            holder.contentView.text = item.content
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
//    }
}