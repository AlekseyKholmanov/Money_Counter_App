package com.example.holmi_production.money_counter_app.ui.adapter

import com.daimajia.swipe.SwipeLayout
import com.daimajia.swipe.implments.SwipeItemRecyclerMangerImpl
import com.daimajia.swipe.interfaces.SwipeAdapterInterface
import com.daimajia.swipe.interfaces.SwipeItemMangerInterface
import com.daimajia.swipe.util.Attributes
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.Item
import com.example.holmi_production.money_counter_app.ui.adapter.delegate.TransactionDayHeaderDelegate
import com.example.holmi_production.money_counter_app.ui.adapter.delegate.TransactionItemDelegate
import com.example.holmi_production.money_counter_app.ui.adapter.delegate.ZeroItemAdapterDelegate
import com.example.holmi_production.money_counter_app.ui.adapter.diffUtil.TransactionDiffutil
import com.example.holmi_production.money_counter_app.ui.adapter.holder.TransactionItemHolder
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class TransactionAdapter(
    callback: TransactionAdapterCallback
) : AsyncListDifferDelegationAdapter<Item>(TransactionDiffutil()), SwipeItemMangerInterface,
    SwipeAdapterInterface {


    private val itemManager = SwipeItemRecyclerMangerImpl(this)

    interface TransactionAdapterCallback: TransactionItemHolder.Callback

    init {
        delegatesManager.addDelegate(TransactionItemDelegate(callback, itemManager))
        delegatesManager.addDelegate(TransactionDayHeaderDelegate())
        delegatesManager.addDelegate(ZeroItemAdapterDelegate(R.layout.item_transaction_0data))
    }

    override fun openItem(position: Int) {
        itemManager.openItem(position)
    }

    override fun closeItem(position: Int) {
        itemManager.closeItem(position)
    }

    override fun closeAllExcept(layout: SwipeLayout?) {
        itemManager.closeAllExcept(layout)
    }

    override fun closeAllItems() {
        itemManager.closeAllItems()
    }

    override fun getOpenItems(): List<Int> {
        return itemManager.openItems
    }

    override fun getOpenLayouts(): List<SwipeLayout?>? {
        return itemManager.openLayouts
    }

    override fun removeShownLayouts(layout: SwipeLayout?) {
        itemManager.removeShownLayouts(layout)
    }

    override fun isOpen(position: Int): Boolean {
        return itemManager.isOpen(position)
    }

    override fun getMode(): Attributes.Mode? {
        return itemManager.mode
    }

    override fun setMode(mode: Attributes.Mode?) {
        itemManager.mode = mode
    }

    override fun getSwipeLayoutResourceId(position: Int): Int = R.id.swipe

}