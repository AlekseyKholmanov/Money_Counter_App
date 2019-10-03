package com.example.holmi_production.money_counter_app.ui.costs_fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.holmi_production.money_counter_app.App
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.ui.costs_fragment.adapter.CostsAdapter
import com.example.holmi_production.money_counter_app.model.ListItem
import com.example.holmi_production.money_counter_app.model.entity.Spending
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppCompatFragment
import kotlinx.android.synthetic.main.fragment_bottom_costs.*
import leakcanary.AppWatcher

class CostsFragment : AndroidXMvpAppCompatFragment(), CostsView {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_bottom_costs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = CostsAdapter()
        spendingList.layoutManager = LinearLayoutManager(requireContext())

        spendingList.adapter = adapter
        val swipeHandle = object : SwipeToDeleteCallback(context!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = adapter.items[viewHolder.adapterPosition]
                if (item is Spending) {
                    adapter.notifyItemRemoved(viewHolder.adapterPosition)
                    adapter.notifyDataSetChanged()
                    presenter.delete(item)
                }
            }
        }
        presenter.getSpending()
        presenter.setObservers()
        val itemTouchHelper = ItemTouchHelper(swipeHandle)
        itemTouchHelper.attachToRecyclerView(spendingList)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("qwerty", "cost attached")
    }

    override fun onStop() {
        super.onStop()
        Log.d("qwerty", "cost stopped")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("qwerty", "cost view destroyed")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("qwerty", "cost detached")
    }

    override fun onResume() {
        super.onResume()
        Log.d("qwerty", "cost resumed")
    }

    override fun onError(error: Throwable) {
        if (adapter.itemCount == 0)
            showEmptyPlaceholder()
    }

    override fun showSpending(spending: List<ListItem>) {
        adapter.items = spending
        if (spending.isEmpty())
            showEmptyPlaceholder()
        else {
            emptyPlaceholder_costs.isVisible = false
            spendingList.isVisible = true
            adapter.notifyDataSetChanged()
        }
    }

    override fun updateList() {
        adapter.notifyDataSetChanged()
    }

    private fun showEmptyPlaceholder() {
        emptyPlaceholder_costs.isVisible = true
        spendingList.isVisible = false
    }

    override fun onDestroy() {
        super.onDestroy()
        AppWatcher.objectWatcher.watch(this)
    }

    @ProvidePresenter
    fun providePresenter() = App.component.getCostsPresenter()

    private lateinit var adapter: CostsAdapter
    @InjectPresenter
    lateinit var presenter: CostsPresenter

}