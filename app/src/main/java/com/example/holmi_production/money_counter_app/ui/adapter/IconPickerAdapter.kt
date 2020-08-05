package com.example.holmi_production.money_counter_app.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.ui.adapter.holder.ImageHolder

class IconPickerAdapter(private val imageIds: List<Int>, val callback: ImageHolder.Callback) :
    RecyclerView.Adapter<ImageHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return ImageHolder(item)
    }

    override fun getItemCount(): Int = imageIds.size

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        val imageId = imageIds[position]
        holder.itemView.setOnClickListener {
            callback.imagePicked(position)
        }
        holder.bind(imageId, position)
    }
}