package com.example.holmi_production.money_counter_app.costs.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.extensions.toCurencyFormat
import com.example.holmi_production.money_counter_app.model.CostTimeDivider
import com.example.holmi_production.money_counter_app.model.ListItem
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate

class CostTimeDividerDelegate : AdapterDelegate<List<ListItem>>() {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.costs_list_date, parent, false)
        return ViewHolder(view)
    }

    override fun isForViewType(items: List<ListItem>, position: Int): Boolean {
        return items[position] is CostTimeDivider
    }

    override fun onBindViewHolder(
        items: List<ListItem>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: List<Any>) {
        if (holder is ViewHolder){
            val divider  = (items[position] as CostTimeDivider)
        holder.bind(divider)}
    }

    class ViewHolder internal constructor(v:View): RecyclerView.ViewHolder(v){
        private val date = v.findViewById(R.id.costs_divider_date) as TextView
        private val sum = v.findViewById(R.id.costs_divider_sum) as TextView
        fun bind(divider: CostTimeDivider) {
            date.text = divider.date
            sum.text = divider.sum.toCurencyFormat()

        }
    }

}