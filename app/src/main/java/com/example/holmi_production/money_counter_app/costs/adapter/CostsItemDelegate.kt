package com.example.holmi_production.money_counter_app.costs.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.costs.ListItem
import com.example.holmi_production.money_counter_app.extensions.toCurencyFormat
import com.example.holmi_production.money_counter_app.model.Spending
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate

class CostsItemDelegate : AdapterDelegate<List<ListItem>>() {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cost_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun isForViewType(items: List<ListItem>, position: Int): Boolean {
        return items[position] is Spending
    }

    override fun onBindViewHolder(
        items: List<ListItem>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        list: List<Any>) {
        if (holder is ViewHolder) {
            val item = items[position] as Spending
            holder.category.setBackgroundColor(item.categoryTypes.color)
            holder.bind(item)
        }
    }

    class ViewHolder internal constructor(v: View) : RecyclerView.ViewHolder(v) {

        private val date: AppCompatTextView = v.findViewById(R.id.date)
        val category: AppCompatTextView = v.findViewById(R.id.category)
        val sum: AppCompatTextView = v.findViewById(R.id.start_sum)
        fun bind(spending: Spending) {
            category.text = spending.categoryTypes.description
            date.text = spending.spendingDate.toString("HH:mm")
            sum.text = spending.sum.toCurencyFormat()

        }

    }

}