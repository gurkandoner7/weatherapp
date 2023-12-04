package com.portal.weatherapp.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.portal.weatherapp.data.model.MainItem
import com.portal.weatherapp.data.model.WeatherData
import com.portal.weatherapp.databinding.ItemWeatherCardViewBinding
import com.portal.weatherapp.domain.mapper.toWeatherData

class WeatherAdapter(
    private val context: Context
) : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {
    private var items: MutableList<WeatherData> = mutableListOf()

    fun addItem(newItem: MainItem) {
        val weatherDataList = newItem.toWeatherData()
        items.addAll(weatherDataList)
        notifyItemRangeInserted(items.size - weatherDataList.size, weatherDataList.size)
    }

    fun updateItems(newItems: List<MainItem>) {
        items.clear()
        newItems.forEach { newItem ->
            items.addAll(newItem.toWeatherData())
        }
        notifyDataSetChanged()
    }

    inner class WeatherViewHolder(private val binding: ItemWeatherCardViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(weatherData: WeatherData) {
            binding.apply {
                tvHeader.setText(weatherData.header)
                ivIcon.setImageResource(weatherData.icon)
                tvDesc.setText(weatherData.desc)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemWeatherCardViewBinding.inflate(inflater, parent, false)
        return WeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val weatherItem = items[position]
        holder.bind(weatherItem)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}