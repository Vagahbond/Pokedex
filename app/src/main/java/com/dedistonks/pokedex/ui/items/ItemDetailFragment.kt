package com.dedistonks.pokedex.ui.items

import android.content.ClipData
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
import com.dedistonks.pokedex.databinding.ItemDetailBinding
import com.dedistonks.pokedex.models.Item
import com.dedistonks.pokedex.utils.ImageToBitmap
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * A fragment representing a single Object detail screen.
 * This fragment is either contained in a [ItemListActivity]
 * in two-pane mode (on tablets) or a [ItemDetailActivity]
 * on handsets.
 */
class ItemDetailFragment : Fragment() {

    /**
     * The dummy content this fragment is presenting.
     */

    private lateinit var binding: ItemDetailBinding
    private lateinit var viewModel: ItemDetailViewModel

    private var loadJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        context?.let {
            viewModel = ViewModelProvider(this, Injection.provideViewModelFactory(it))
                    .get(ItemDetailViewModel::class.java)
        }
        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                // Load the dummy content specified by the fragment
                // arguments. In a real-world scenario, use a Loader
                // to load content from a content provider.
                load(it.getInt(ARG_ITEM_ID))
            }
        }

        viewModel.currentItemResult.observe(this,) {
            bindItem(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = ItemDetailBinding.inflate(layoutInflater)

        return binding.root
    }

    private fun load(index: Int) {
        loadJob?.cancel()
        loadJob = lifecycleScope.launch {
            viewModel.loadItem(index)
        }
    }

    private fun setImageToComponent(target: ImageView, value: Bitmap) {
        activity?.let {
            it.runOnUiThread{
                target.setImageBitmap(value)
            }
        }
    }

    private fun bindItem(item: Item) {
        binding.tvItemName.text = item.name

        binding.tvItemCategory.text = item.category

        binding.tvItemDescription.text = item.description

        item.spriteUrl?.let {
            ImageToBitmap.from(it) { bitmap ->
                setImageToComponent(binding.ivItem, bitmap)
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