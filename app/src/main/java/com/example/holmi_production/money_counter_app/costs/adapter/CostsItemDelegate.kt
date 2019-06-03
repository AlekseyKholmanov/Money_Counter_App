package com.example.holmi_production.money_counter_app.costs.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.costs.ListItem
import com.example.holmi_production.money_counter_app.model.Spending
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate

class CostsItemDelegate() : AdapterDelegate<MutableList<ListItem>>() {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cost_item, parent, false)
        return ViewHolder(view)
    }

    override fun isForViewType(items: MutableList<ListItem>, position: Int): Boolean {
        return items[position] is Spending
    }

    override fun onBindViewHolder(
        items: MutableList<ListItem>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        list: List<Any>) {
        if (holder is ViewHolder) {
            val item = items[position] as Spending
            if (item.categoryTypes.isSpending){
                holder.category.setBackgroundColor(Color.RED)
            }
            else{
                holder.category.setBackgroundColor(Color.GREEN)
            }
            holder.category.text = item.categoryTypes.expense.name
            holder.date.tag = items[position]
            holder.date.text = item.spendingDate.toString("HH:mm")
            holder.sum.text = item.price.toString()
        }

    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val date: AppCompatTextView = view.findViewById(R.id.date)
        val category: AppCompatTextView = view.findViewById(R.id.category)
        val sum: AppCompatTextView = view.findViewById(R.id.start_sum)
    }

}