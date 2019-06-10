package com.example.holmi_production.money_counter_app.costs.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.costs.ListItem
import com.example.holmi_production.money_counter_app.costs.SwipeToDeleteCallback
import com.example.holmi_production.money_counter_app.model.Spending
import com.example.holmi_production.money_counter_app.toCurencyFormat
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate

class CostsItemDelegate : AdapterDelegate<List<ListItem>>() {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cost_item, parent, false)
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
            if (item.categoryTypes.isSpending)
                holder.category.setBackgroundColor(Color.RED)
            else
                holder.category.setBackgroundColor(Color.GREEN)
            holder.bind(item)
        }
    }

    class ViewHolder internal constructor(v: View) : RecyclerView.ViewHolder(v) {

        private val date: AppCompatTextView = v.findViewById(R.id.date)
        val category: AppCompatTextView = v.findViewById(R.id.category)
        val sum: AppCompatTextView = v.findViewById(R.id.start_sum)
        fun bind(spending: Spending) {
            category.text = spending.categoryTypes.name
            date.text = spending.spendingDate.toString("HH:mm")
            sum.text = spending.price.toCurencyFormat()
        }

    }

}