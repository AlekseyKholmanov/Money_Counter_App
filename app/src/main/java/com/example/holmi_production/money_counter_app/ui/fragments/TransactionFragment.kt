package com.example.holmi_production.money_counter_app.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.main.BaseFragment
import com.example.holmi_production.money_counter_app.model.Item
import com.example.holmi_production.money_counter_app.ui.adapter.TransactionAdapter
import com.example.holmi_production.money_counter_app.ui.adapter.items.ZeroItem
import com.example.holmi_production.money_counter_app.ui.viewModels.TransactionViewModel
import kotlinx.android.synthetic.main.fragment_trasnsactions.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class TransactionFragment : BaseFragment(R.layout.fragment_trasnsactions) {
    private lateinit var adapter: TransactionAdapter

    private val viewModel: TransactionViewModel by viewModel()

    private val transactionAdapterCallback = object: TransactionAdapter.TransactionAdapterCallback{
        override fun openTransactionDetails(transactionId: String) {
        }

        override fun deleteTransaction(transactionId: String) {
            viewModel.deleteTransaction(transactionId)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.observeTransaction()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = TransactionAdapter(transactionAdapterCallback)
        transactionList.adapter = adapter
        with(viewModel) {
            transactions.observe(viewLifecycleOwner, Observer(::updateTransactions))
        }
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

    companion object {
        fun newInstance(): TransactionFragment {
            return TransactionFragment()
        }
    }

}