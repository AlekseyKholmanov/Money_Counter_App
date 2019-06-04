package com.example.holmi_production.money_counter_app.costs.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.costs.CostTimeDivider
import com.example.holmi_production.money_counter_app.costs.ListItem
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate

class CostTimeDividerDelegate : AdapterDelegate<List<ListItem>>() {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_costs_date_divider, parent, false)
        return ViewHolder(view as TextView)
    }

    override fun isForViewType(items: List<ListItem>, position: Int): Boolean {
        return items[position] is CostTimeDivider
    }

    override fun onBindViewHolder(
        items: List<ListItem>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: List<Any>) {
        if (holder is ViewHolder)
            holder.text.text = (items[position] as CostTimeDivider).text
    }

    class ViewHolder(val text: TextView) : RecyclerView.ViewHolder(text)

}