package com.example.holmi_production.money_counter_app.ui.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.holmi_production.money_counter_app.model.AccountDetails
import com.example.holmi_production.money_counter_app.model.enums.AccountType
import com.example.holmi_production.money_counter_app.model.uiModels.AccountInfo
import kotlinx.android.synthetic.main.item_account.view.*

class AccountViewHolder(v: View, val callback: Callback) : RecyclerView.ViewHolder(v) {


    fun bind(item: AccountInfo) {
        with(itemView){
            accountName.text = item.description
            accountBalance.text = item.balance.toString()
            plus.setOnClickListener {
                callback.plusClicked(item.id)
            }
            minus.setOnClickListener {
                callback.minusClicked(item.id)
            }
            accountType.text = when(item.accountType){
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
