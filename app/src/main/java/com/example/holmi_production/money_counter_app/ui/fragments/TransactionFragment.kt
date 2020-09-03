package com.example.holmi_production.money_counter_app.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.extensions.toRUformat
import com.example.holmi_production.money_counter_app.main.BaseFragment
import com.example.holmi_production.money_counter_app.model.Item
import com.example.holmi_production.money_counter_app.model.entity.FilterPeriodEntity
import com.example.holmi_production.money_counter_app.model.enums.PeriodType
import com.example.holmi_production.money_counter_app.ui.adapter.TransactionAdapter
import com.example.holmi_production.money_counter_app.ui.adapter.items.ZeroItem
import com.example.holmi_production.money_counter_app.ui.dialogs.BottomDialog
import com.example.holmi_production.money_counter_app.ui.viewModels.TransactionViewModel
import com.google.android.material.datepicker.MaterialDatePicker
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
        period.setOnClickListener {

            findNavController().navigate(R.id.action_global_bottomDialog,
            bundleOf(
               BottomDialog.ARGS_DIALOG_TYPE to BottomDialog.TYPE_TOOLBAR_PERIOD,
                BottomDialog.ARGS_SELECTED_ID to viewModel.activePeriod.value!!.type.ordinal
            ))
        }
        right.setOnClickListener {
            viewModel.moveDateForward()
        }
        left.setOnClickListener {
            viewModel.moveDateBack()
        }
        setResultListener()
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

    private fun setResultListener() {
        setFragmentResultListener(BottomDialog.REQUEST_FROM_TRANSACTION_FRAGMENT) { _, bundle ->
            val selected = PeriodType.values()[bundle.getInt("periodType")]
            if (selected == PeriodType.CUSTOM) {
                val dialog = MaterialDatePicker.Builder
                    .dateRangePicker()
                    .setTheme(R.style.AppTheme_RangeDatePicker)
                    .build()

                dialog.show(childFragmentManager, "DATE_PICKER")
                dialog.addOnPositiveButtonClickListener {
                    val to = DateTime(it.first)
                    val from = DateTime(it.second)
                    viewModel.updateSelectedPeriod(selected, to, from)
                }
            } else {
                viewModel.updateSelectedPeriod(selected)
            }
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