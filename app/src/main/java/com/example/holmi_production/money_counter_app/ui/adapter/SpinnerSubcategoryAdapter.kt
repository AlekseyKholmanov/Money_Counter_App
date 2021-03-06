package com.example.holmi_production.money_counter_app.ui.adapter

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.holmi_production.money_counter_app.model.entity.CategoryEntity

class SpinnerSubcategoryAdapter(context: Context, val resource: Int, val categories: Array<CategoryEntity>) :
    ArrayAdapter<CategoryEntity>(context, resource, categories){

    override fun getCount(): Int = categories.size

    override fun getItem(position: Int): CategoryEntity? = categories[position]

    override fun getItemId(position: Int): Long  = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val label = super.getView(position, convertView, parent) as TextView
        label.setTextColor(Color.BLACK)
        label.text = categories[position].description
        return label
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val label = super.getView(position, convertView, parent) as TextView
        label.setTextColor(Color.BLACK)
        label.text = categories[position].description
        return label
    }
}