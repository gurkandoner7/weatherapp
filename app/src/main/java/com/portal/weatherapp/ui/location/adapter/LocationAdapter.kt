package com.portal.weatherapp.ui.location.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.portal.weatherapp.R
import com.portal.weatherapp.databinding.ItemLocationBinding
import com.portal.weatherapp.repository.db.location.LocationEntity

@SuppressLint("NotifyDataSetChanged")
class LocationAdapter(
    private val context: Context,
    private var onItemDeleteClicked: (LocationEntity) -> Unit,
    private var onItemClicked: (LocationEntity) -> Unit,
) : RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {
    private var items: MutableList<LocationEntity> = mutableListOf()

    fun updateItems(newItems: List<LocationEntity>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    private fun removeItem(position: Int) {
        if (position != RecyclerView.NO_POSITION) {
            val deletedItem = items[position]
            items.removeAt(position)
            notifyItemRemoved(position)
            onItemDeleteClicked(deletedItem)
        }
    }

    inner class LocationViewHolder(private val binding: ItemLocationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(locationItem: LocationEntity) {
            binding.apply {
                tvHeader.setText(locationItem.cityName)
                tvLat.setText(locationItem.lat?.toString())
                tvLon.setText(locationItem.lon?.toString())
                clCardview.setOnClickListener {
                    onItemClicked(locationItem)
                }
                btnDelete.setOnClickListener {
                    showConfirmationDialog(bindingAdapterPosition)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLocationBinding.inflate(inflater, parent, false)
        return LocationViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    private fun showConfirmationDialog(position: Int) {
        val builder = AlertDialog.Builder(context, R.style.AlertDialogCustom)
        builder.setTitle(context.getString(R.string.warning))
            .setMessage(context.getString(R.string.dialog_question))
            .setPositiveButton(context.getString(R.string.approve)) { dialog, _ ->
                if (position != RecyclerView.NO_POSITION) {
                    removeItem(position)
                }
                dialog.dismiss()
            }
            .setNegativeButton(context.getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }


    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) =
        holder.bind(items[position])

}