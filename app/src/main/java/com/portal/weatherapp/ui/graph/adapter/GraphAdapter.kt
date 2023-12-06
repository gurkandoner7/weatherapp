package com.portal.Graphapp.ui.graph.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.portal.weatherapp.data.model.GraphItem
import com.portal.weatherapp.databinding.ItemGraphCardViewBinding
@SuppressLint("NotifyDataSetChanged")
class GraphAdapter(
    private val context: Context
) : RecyclerView.Adapter<GraphAdapter.GraphViewHolder>() {
    private var items: MutableList<GraphItem> = mutableListOf()

    fun addItem(newItem: GraphItem) {
        items.add(newItem)
        notifyDataSetChanged()
    }

    fun updateItems(newItems: List<GraphItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    inner class GraphViewHolder(private val binding: ItemGraphCardViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(graphItem: GraphItem) {
            binding.apply {
                tvHeader.setText(graphItem.header)
                ivIcon.setImageResource(graphItem.icon)
                tvDesc.setText(graphItem.desc)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GraphViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemGraphCardViewBinding.inflate(inflater, parent, false)
        return GraphViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GraphViewHolder, position: Int) {
        val graphItem = items[position]
        holder.bind(graphItem)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}