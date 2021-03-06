package com.example.holmi_production.money_counter_app.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.extensions.toRUformat
import com.example.holmi_production.money_counter_app.main.BaseFragment
import com.example.holmi_production.money_counter_app.model.Item
import com.example.holmi_production.money_counter_app.model.entity.FilterPeriodEntity
import com.example.holmi_production.money_counter_app.ui.adapter.TransactionAdapter
import com.example.holmi_production.money_counter_app.ui.adapter.items.ZeroItem
import com.example.holmi_production.money_counter_app.ui.dialogs.BottomDialog
import com.example.holmi_production.money_counter_app.ui.viewModels.TransactionViewModel
import kotlinx.android.synthetic.main.fragment_trasnsactions.*
import org.joda.time.DateTime
import org.koin.androidx.viewmodel.ext.android.viewModel


class TransactionFragment : BaseFragment(R.layout.fragment_trasnsactions) {
    private lateinit var adapter: TransactionAdapter

    private val viewModel: TransactionViewModel by viewModel()

    private val transactionAdapterCallback =
        object : TransactionAdapter.TransactionAdapterCallback {
            override fun openTransactionDetails(transactionId: String) {
            }

            override fun deleteTransaction(transactionId: String) {
                viewModel.deleteTransaction(transactionId)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(viewModel) {
            observeTransaction()
            observeActivePeriod()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = TransactionAdapter(transactionAdapterCallback)
        transactionList.adapter = adapter
        with(viewModel) {
            transactions.observe(viewLifecycleOwner, Observer(::updateTransactions))
            activePeriod.observe(viewLifecycleOwner, Observer(::updatePeriod))
            summary.observe(viewLifecycleOwner, Observer(::updateSummary))
        }
        right.setOnClickListener {
            viewModel.moveDateForward()
        }
        left.setOnClickListener {
            viewModel.moveDateBack()
        }
    }

    private fun updateSummary(pair: Pair<Double, Double>) {
        incomeSummary.text = pair.first.toString()
        spendingSummary.text = pair.second.toString()
    }

    private fun updatePeriod(filterPeriodEntity: FilterPeriodEntity) {
        val text =
            if (filterPeriodEntity.from.toRUformat() == filterPeriodEntity.to.toRUformat()) {
                "Today ${DateTime.now().toRUformat()}"
            } else {
                "${filterPeriodEntity.from.toRUformat()} - ${filterPeriodEntity.to.toRUformat()}"
            }
        period.text = text
    }

    private fun updateTransactions(transactions: List<Item>) {
        adapter.items = if (transactions.isEmpty()) {
            listOf(ZeroItem(R.layout.item_transaction_0data))
        } else {
            transactions
        }
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

}