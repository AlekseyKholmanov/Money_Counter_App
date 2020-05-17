package com.example.holmi_production.money_counter_app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.holmi_production.money_counter_app.App
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.extensions.toCurencyFormat
import com.example.holmi_production.money_counter_app.extensions.withRubleSign
import com.example.holmi_production.money_counter_app.main.BaseFragment
import com.example.holmi_production.money_counter_app.ui.adapter.CostsAdapter
import com.example.holmi_production.money_counter_app.model.ListItem
import com.example.holmi_production.money_counter_app.model.entity.SpendingDetails
import com.example.holmi_production.money_counter_app.ui.presenters.CostsPresenter
import com.example.holmi_production.money_counter_app.ui.presenters.CreateCategoryPresenter
import com.example.holmi_production.money_counter_app.ui.utils.SwipeToDeleteCallback
import kotlinx.android.synthetic.main.fragment_bottom_costs.*







class CostsFragment : BaseFragment(R.layout.fragment_bottom_costs)
     {

    override fun inject() {
   //AppComponent.instance.inject(this)
    }

    private lateinit var adapter: CostsAdapter
//    @Inject
//    lateinit var presenterProvider: Provider<CostsPresenter>
//
//    private val presenter by moxyPresenter { presenterProvider.get() }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
   //AppComponent.instance.inject(this)
        adapter =
            CostsAdapter()
//        presenter.observeSpengings()
        spendingList.layoutManager = LinearLayoutManager(requireContext())

        spendingList.adapter = adapter
        val swipeHandle = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = adapter.items[viewHolder.adapterPosition]
                if (item is SpendingDetails) {
                    adapter.notifyItemRemoved(viewHolder.adapterPosition)
//                    presenter.delete(item.spending)
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandle)
        itemTouchHelper.attachToRecyclerView(spendingList)
    }


//    override fun onError(error: Throwable) {
//        showEmptyPlaceholder(show = true)
//    }
//
//    override fun showSpending(spending: List<ListItem>) {
//        adapter.items = spending
//        adapter.notifyDataSetChanged()
//        if (spending.isEmpty())
//            showEmptyPlaceholder(show = true)
//        else {
//            showEmptyPlaceholder(show = false)
//        }
//    }
//
//    override fun showSumByDirection(spending: Double, income: Double) {
//        val incomeText =
//            if (income == 0.0)
//                "+ ${"0".withRubleSign()}"
//            else
//                "+ ${income.toCurencyFormat().withRubleSign()}"
//        val spendingText = if (spending == 0.0)
//            "- ${"0".withRubleSign()}"
//        else
//            "- ${spending.toCurencyFormat().withRubleSign()}"
//        tv_costs_income.text = incomeText
//        tv_costs_spending.text = spendingText
//
//    }

    private fun showEmptyPlaceholder(show: Boolean) {
        emptyPlaceholder_costs.isVisible = show
        spendingList.isVisible = !show
    }

    companion object {
        fun newInstance(): CostsFragment {
            return CostsFragment()
        }
    }

}