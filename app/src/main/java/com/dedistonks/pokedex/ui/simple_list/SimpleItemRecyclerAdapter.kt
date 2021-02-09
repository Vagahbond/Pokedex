package com.dedistonks.pokedex.ui.simple_list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dedistonks.pokedex.R

class SimpleItemRecyclerAdapter(
    private val values: List<String>
) :
    RecyclerView.Adapter<SimpleItemRecyclerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.horiztontal_simple_list_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.tvName.text = item
    }

    override fun getItemCount(): Int {
        return this.values.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvGameName)
    }


    companion object {
        fun getHorizontalLayoutManager(context: Context) : LinearLayoutManager {
            return LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false,
            )
        }

        fun getSeparator(context: Context) : DividerItemDecoration {
            return DividerItemDecoration(
                context,
                LinearLayoutManager.HORIZONTAL
            )
        }
    }
}