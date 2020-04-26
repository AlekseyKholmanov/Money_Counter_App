package com.example.holmi_production.money_counter_app.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.entity.CategoryEntity
import com.example.holmi_production.money_counter_app.model.entity.SubCategoryEntity
import com.example.holmi_production.money_counter_app.ui.adapter.holder.CategoryPickerHolder

class CategoryPickerAdapter(val callback: CategoryPickerHolder.Callback) :
    RecyclerView.Adapter<CategoryPickerHolder>() {

    private val types: ArrayList<Pair<CategoryEntity, List<SubCategoryEntity>>> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryPickerHolder {
        val inflater = LayoutInflater.from(parent.context)
        val item = inflater.inflate(R.layout.item_category, parent, false)
        return CategoryPickerHolder(item, callback)
    }

    fun setCategory(categories: ArrayList<Pair<CategoryEntity, List<SubCategoryEntity>>>) {
        types.clear()
        types.addAll(categories)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = types.size

    override fun onBindViewHolder(holder: CategoryPickerHolder, position: Int) {
        holder.bind(types[position])
    }
}