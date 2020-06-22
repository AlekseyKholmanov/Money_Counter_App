package com.example.holmi_production.money_counter_app.ui.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.holmi_production.money_counter_app.model.AccountDetails
import com.example.holmi_production.money_counter_app.model.enums.AccountType
import kotlinx.android.synthetic.main.item_account.view.*

class AccountViewHolder(v: View, val callback: Callback) : RecyclerView.ViewHolder(v) {


    fun bind(item: AccountDetails) {
        with(itemView){
            accountName.text = item.account.description
            val balance = item.transactions.sumByDouble { it.sum }
            accountBalance.text = balance.toString()
            plus.setOnClickListener {
                callback.plusClicked(item.account.id)
            }
            minus.setOnClickListener {
                callback.minusClicked(item.account.id)
            }
            accountType.text = when(item.account.accountType){
                AccountType.DEBET -> "D"
                AccountType.FUNDED -> "F"
                AccountType.CREDIT -> "K"
            }
        }
    }
}
interface Callback{
    fun minusClicked(accountId:String)
    fun plusClicked(accountId: String)
}
