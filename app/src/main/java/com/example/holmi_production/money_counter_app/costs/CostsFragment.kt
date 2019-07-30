package com.example.holmi_production.money_counter_app.costs

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
import com.example.holmi_production.money_counter_app.costs.adapter.CostsAdapter
import com.example.holmi_production.money_counter_app.model.ListItem
import com.example.holmi_production.money_counter_app.model.Spending
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppCompatFragment
import kotlinx.android.synthetic.main.fragment_bottom_costs.*

class CostsFragment : AndroidXMvpAppCompatFragment(), CostsView {
    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("qwerty","cost attached")
    }

    override fun onStop() {
        super.onStop()
        Log.d("qwerty","cost stopped")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("qwerty","cost view destroyed")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("qwerty","cost detached")
    }

    override fun onResume() {
        super.onResume()
        Log.d("qwerty","cost resumed")
    }
    private lateinit var adapter: CostsAdapter
    @InjectPresenter
    lateinit var presenter: CostsPresenter

    @ProvidePresenter
    fun providePresenter() = App.component.getCostsPresenter()

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
        val itemTouchHelper = ItemTouchHelper(swipeHandle)
        itemTouchHelper.attachToRecyclerView(spendingList)
    }

    //target api 24 lvl. current 21
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.loadCosts()
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
            emptyPlaceholder.isVisible = false
            spendingList.isVisible = true
            adapter.notifyDataSetChanged()
        }
    }

    override fun updateList() {
        adapter.notifyDataSetChanged()
    }

    private fun showEmptyPlaceholder() {
        emptyPlaceholder.isVisible = true
        spendingList.isVisible = false
    }

}