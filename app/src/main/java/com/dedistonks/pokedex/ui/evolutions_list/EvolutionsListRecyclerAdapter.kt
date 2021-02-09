package com.dedistonks.pokedex.ui.evolutions_list

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dedistonks.pokedex.R
import com.dedistonks.pokedex.models.PokemonEvolution
import java.util.function.Consumer

class EvolutionsListRecyclerAdapter(
        private val values: List<PokemonEvolution>,
        private val parentActivity: Activity,
        private val callBack: EvolutionIDConsumer,
) :
        RecyclerView.Adapter<EvolutionsListViewHolder>() {


    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int):
            EvolutionsListViewHolder {

        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.evolution_list_content, parent, false)
        return EvolutionsListViewHolder(view, callBack, parentActivity)
    }

    override fun onBindViewHolder(holder: EvolutionsListViewHolder, position: Int) {
        holder.bind(values[position])
    }

    override fun getItemCount(): Int {
        return this.values.size
    }


    companion object {
        fun getHorizontalLayoutManager(context: Context): LinearLayoutManager {
            return LinearLayoutManager(
                    context,
                    LinearLayoutManager.HORIZONTAL,
                    false,
            )
        }

        fun getSeparator(context: Context): DividerItemDecoration {
            return DividerItemDecoration(
                    context,
                    LinearLayoutManager.HORIZONTAL
            )
        }
    }

}