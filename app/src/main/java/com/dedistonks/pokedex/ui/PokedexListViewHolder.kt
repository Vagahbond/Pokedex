package com.dedistonks.pokedex.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dedistonks.pokedex.R
import com.dedistonks.pokedex.models.ListAPIResource

//TODO: is generic needed ?
class PokedexListViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val idText: TextView = view.findViewById(R.id.id_text)
    private val content: TextView = view.findViewById(R.id.content)

    private var item: ListAPIResource? = null

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
        fun create(parent: ViewGroup): PokedexListViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.pokedex_list_content, parent, false)
            return PokedexListViewHolder(view)
        }
    }


}