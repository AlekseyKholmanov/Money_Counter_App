package com.example.holmi_production.money_counter_app.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.AccountDetails
import com.example.holmi_production.money_counter_app.ui.adapter.holder.AccountViewHolder
import com.example.holmi_production.money_counter_app.ui.adapter.holder.Callback

class AccountAdapter(private val callback: Callback) : RecyclerView.Adapter<AccountViewHolder>() {

    val items = mutableListOf<AccountDetails>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_account, parent, false)
        return AccountViewHolder(view, callback)
    }

    override fun getItemCount(): Int = items.size

    fun setItems(newItems: List<AccountDetails>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        holder.bind(items[position])
    }
}