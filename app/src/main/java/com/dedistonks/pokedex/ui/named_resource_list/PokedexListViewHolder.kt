package com.dedistonks.pokedex.ui.named_resource_list


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.RecyclerView
import com.dedistonks.pokedex.R
import com.dedistonks.pokedex.models.Item
import com.dedistonks.pokedex.models.ListAPIResource
import com.dedistonks.pokedex.models.Pokemon
import com.dedistonks.pokedex.ui.items.ItemDetailActivity
import com.dedistonks.pokedex.ui.items.ItemDetailFragment
import com.dedistonks.pokedex.ui.pokemons.PokemonDetailActivity
import com.dedistonks.pokedex.ui.pokemons.PokemonDetailFragment
import java.lang.IllegalStateException


@ExperimentalPagingApi
class PokedexListViewHolder(
        view: View,
        parentActivity: AppCompatActivity,
        twoPane: Boolean):
        RecyclerView.ViewHolder(view) {

    private val idText: TextView = view.findViewById(R.id.id_text)
    private val content: TextView = view.findViewById(R.id.content)

    private var item: ListAPIResource? = null

    init {
        view.setOnClickListener { v ->
            //Log.d(this.javaClass.name, "Constructor ran.")

            if (twoPane) {
                val fragment = when (item) {
                    is Pokemon -> PokemonDetailFragment()
                    is Item -> ItemDetailFragment()
                    else -> throw IllegalStateException("PokedexListViewHolder received an unknown item type.")
                }

                fragment.apply {
                    arguments = Bundle().apply {
                        when (item) {
                            is Pokemon -> putInt(PokemonDetailFragment.ARG_ITEM_ID, item?.id ?: 0)
                            is Item -> putInt(ItemDetailFragment.ARG_ITEM_ID, item?.id ?: 0)
                            else -> throw IllegalStateException("PokedexListViewHolder received an unknown item type.")
                        }

                    }
                }
                parentActivity.supportFragmentManager
                        .beginTransaction().let {
                            when (item) {
                                is Pokemon -> it.replace(R.id.pokemon_detail_container, fragment)
                                is Item -> it.replace(R.id.item_detail_container, fragment)
                                else -> throw IllegalStateException("PokedexListViewHolder received an unknown item type.")
                            }
                        }.commit()
            } else {
                val intent = when (item) {
                    is Pokemon -> Intent(v.context, PokemonDetailActivity::class.java)
                    is Item -> Intent(v.context, ItemDetailActivity::class.java)
                    else -> throw IllegalStateException("PokedexListViewHolder received an unknown item type.")
                }

                intent.apply {
                    when (item) {
                        is Pokemon -> putExtra(PokemonDetailFragment.ARG_ITEM_ID, item?.id)
                        is Item -> putExtra(ItemDetailFragment.ARG_ITEM_ID, item?.id)
                        else -> throw IllegalStateException("PokedexListViewHolder received an unknown item type.")
                    }

                }
                v.context.startActivity(intent)
            }
        }

    }


    fun bind(item: ListAPIResource?) {
        Log.d(this.javaClass.name, "Binding item : $item")
        if (item == null) {
            val resources = itemView.resources
            idText.text = "x"
            content.text = resources.getString(R.string.unloaded_data)
        } else {
            showItemData(item)
        }


    }



    private fun showItemData(item: ListAPIResource) {
        this.item = item

        idText.text = item.id.toString()
        content.text = item.name
    }

    companion object {
        fun create(parent: ViewGroup, parentActivity: AppCompatActivity, twoPane: Boolean): PokedexListViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.pokedex_list_content, parent, false)
            return PokedexListViewHolder(view, parentActivity, twoPane)
        }
    }


}