package com.example.holmi_production.money_counter_app.ui.adapter.holder

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.extensions.getNameDayOfWeek
import com.example.holmi_production.money_counter_app.extensions.toCurencyFormat
import com.example.holmi_production.money_counter_app.extensions.toRUformat
import com.example.holmi_production.money_counter_app.extensions.withRubleSign
import com.example.holmi_production.money_counter_app.ui.adapter.items.TransactionDayHeaderItem
import kotlinx.android.synthetic.main.item_transaction_day_header.view.*

class TransactionDayHeaderHolder (v: View) : RecyclerView.ViewHolder(v) {

    fun bind(item: TransactionDayHeaderItem) {
        with(itemView){
            val color: Int = if (item.sum > 0) Color.parseColor("#2e7d32") else Color.parseColor("#c62828")
            headerDate.text = item.date.toRUformat()
            headerDayOfWeek.text = item.date.getNameDayOfWeek()
            headerSum.text = item.sum.toCurencyFormat().withRubleSign()
            headerSum.setTextColor(color)
        }
    }
}