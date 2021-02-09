package com.dedistonks.pokedex.ui.evolutions_list

import android.app.Activity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dedistonks.pokedex.R
import com.dedistonks.pokedex.models.PokemonEvolution
import com.dedistonks.pokedex.utils.ImageToBitmap
import java.util.*


class EvolutionsListViewHolder(
        view: View,
        private val callBack: EvolutionIDConsumer,
        private val parentActivity: Activity):
        RecyclerView.ViewHolder(view) {

    private var ivEvolutionPicture: ImageView = view.findViewById(R.id.ivEvolutionPicture)
    private var tvEvolutionName: TextView = view.findViewById(R.id.tvEvolutionName)

    private var item: PokemonEvolution? = null

    init {
        view.setOnClickListener {
            item?.let {
                callBack.accept(it.id)
            }
        }
    }



    fun bind(item: PokemonEvolution) {
        this.item = item

        item.sprite?.let{ url ->
            ImageToBitmap.from(url,) {
                parentActivity.runOnUiThread {
                    ivEvolutionPicture.setImageBitmap(it)
                }
            }
        }

        tvEvolutionName.text = item.name.capitalize(Locale.ROOT)
    }
}