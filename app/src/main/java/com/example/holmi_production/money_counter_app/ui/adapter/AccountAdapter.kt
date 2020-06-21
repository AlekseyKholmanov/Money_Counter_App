package com.example.holmi_production.money_counter_app.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.entity.AccountEntity
import com.example.holmi_production.money_counter_app.ui.adapter.holder.AccountViewHolder

class AccountAdapter: RecyclerView.Adapter<AccountViewHolder>() {

    private val items = mutableListOf<AccountEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_account,parent)
        return AccountViewHolder(view)
    }

    override fun getItemCount(): Int  = items.size

    fun setItems(newItems: List<AccountEntity>){
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        holder.bind(items[position])
    }
}