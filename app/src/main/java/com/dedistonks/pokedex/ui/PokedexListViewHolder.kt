package com.dedistonks.pokedex.ui


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.dedistonks.pokedex.R
import com.dedistonks.pokedex.models.ListAPIResource
import com.dedistonks.pokedex.ui.pokemons.PokemonDetailActivity
import com.dedistonks.pokedex.ui.pokemons.PokemonDetailFragment

//TODO: is generic needed ?
class PokedexListViewHolder(view: View, parentActivity: AppCompatActivity, twoPane: Boolean): RecyclerView.ViewHolder(view) {
    private val idText: TextView = view.findViewById(R.id.id_text)
    private val content: TextView = view.findViewById(R.id.content)

    private var item: ListAPIResource? = null

    init {
        view.setOnClickListener { v ->
            Log.d(this.javaClass.name, "Constructor ran.")

            if (twoPane) {
                val fragment = PokemonDetailFragment().apply {
                    arguments = Bundle().apply {
                        putInt(PokemonDetailFragment.ARG_ITEM_ID, item?.id ?: 0)
                    }
                }
                parentActivity.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.pokemon_detail_container, fragment)
                        .commit()
            } else {
                val intent = Intent(v.context, PokemonDetailActivity::class.java).apply {
                    putExtra(PokemonDetailFragment.ARG_ITEM_ID, item?.id)
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