package com.example.holmi_production.money_counter_app.ui.fragments

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
import com.example.holmi_production.money_counter_app.extensions.toCurencyFormat
import com.example.holmi_production.money_counter_app.extensions.withRubleSign
import com.example.holmi_production.money_counter_app.ui.adapter.CostsAdapter
import com.example.holmi_production.money_counter_app.model.ListItem
import com.example.holmi_production.money_counter_app.model.entity.SpendingListItem
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppCompatFragment
import com.example.holmi_production.money_counter_app.ui.presenters.CostsPresenter
import com.example.holmi_production.money_counter_app.ui.presenters.CostsView
import com.example.holmi_production.money_counter_app.ui.utils.SwipeToDeleteCallback
import kotlinx.android.synthetic.main.fragment_bottom_costs.*

class CostsFragment : AndroidXMvpAppCompatFragment(),
    CostsView {

    companion object {
        fun newInstance(): CostsFragment {
            return CostsFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom_costs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter =
            CostsAdapter()
        presenter.observeSpengings()
        spendingList.layoutManager = LinearLayoutManager(requireContext())

        spendingList.adapter = adapter
        val swipeHandle = object : SwipeToDeleteCallback(context!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = adapter.items[viewHolder.adapterPosition]
                if (item is SpendingListItem) {
                    adapter.notifyItemRemoved(viewHolder.adapterPosition)
                    presenter.delete(item.spending)
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandle)
        itemTouchHelper.attachToRecyclerView(spendingList)
    }

    @ProvidePresenter
    fun providePresenter() = App.component.getCostsPresenter()

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
        showEmptyPlaceholder(show = true)
    }

    override fun showSpending(spending: List<ListItem>) {
        adapter.items = spending
        adapter.notifyDataSetChanged()
        if (spending.isEmpty())
            showEmptyPlaceholder(show = true)
        else {
            showEmptyPlaceholder(show = false)
        }
    }

    override fun showSumByDirection(spending: Double, income: Double) {
        val incomeText =
            if (income == 0.0)
                "+ ${"0".withRubleSign()}"
            else
                "+ ${income.toCurencyFormat().withRubleSign()}"
        val spendingText = if (spending == 0.0)
            "- ${"0".withRubleSign()}"
        else
            "- ${spending.toCurencyFormat().withRubleSign()}"
        tv_costs_income.text = incomeText
        tv_costs_spending.text = spendingText

    }

    private fun showEmptyPlaceholder(show: Boolean) {
        emptyPlaceholder_costs.isVisible = show
        spendingList.isVisible = !show
    }

    private lateinit var adapter: CostsAdapter
    @InjectPresenter
    lateinit var presenter: CostsPresenter

}