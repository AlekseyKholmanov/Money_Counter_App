package com.example.holmi_production.money_counter_app.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.extensions.toCurencyFormat
import com.example.holmi_production.money_counter_app.extensions.toRUformat
import com.example.holmi_production.money_counter_app.main.BaseFragment
import com.example.holmi_production.money_counter_app.model.DashbordFilter
import com.example.holmi_production.money_counter_app.model.enums.CurrencyType
import com.example.holmi_production.money_counter_app.ui.adapter.delegate.dashboardTransactionDelegate
import com.example.holmi_production.money_counter_app.ui.adapter.diffUtil.DashboardGroupDiffUtilCallback
import com.example.holmi_production.money_counter_app.ui.adapter.items.AccountSummary
import com.example.holmi_production.money_counter_app.ui.adapter.items.TransactionGroupItem
import com.example.holmi_production.money_counter_app.ui.custom.CurrencyBadge
import com.example.holmi_production.money_counter_app.ui.dialogs.BottomDialog
import com.example.holmi_production.money_counter_app.ui.viewModels.DashboardViewModel
import com.google.android.material.snackbar.Snackbar
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import kotlinx.android.synthetic.main.fragment_dashboard.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment : BaseFragment(R.layout.fragment_dashboard) {

    private val viewModel: DashboardViewModel by viewModel()

    private val transactionAdapter by lazy {
        AsyncListDifferDelegationAdapter(
            DashboardGroupDiffUtilCallback(),
            dashboardTransactionDelegate()
        )
    }

    private val currencyBadgeDrawable by lazy { CurrencyBadge(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(viewModel) {
            accountDetails.observe(viewLifecycleOwner, Observer(::updateAccounts))
            currency.observe(viewLifecycleOwner, Observer(::updateCurrency))
            accountSummary.observe(viewLifecycleOwner, Observer(::updateSummary))
            filterValue.observe(viewLifecycleOwner, Observer(::updateFilterValue))
        }
        updateToolbarTitle()
        accountExpenses.setTitleRes(R.string.expenses)
        accountIncome.setTitleRes(R.string.income)
        accountBalance.setTitleRes(R.string.balance)

        accountName.setOnClickListener {
            findNavController().navigate(
                R.id.action_global_bottomDialog,
                bundleOf(
                    BottomDialog.ARGS_DIALOG_TYPE to BottomDialog.TYPE_ACCOUNT_ID,
                    BottomDialog.ARGS_SELECTED_ID_STRING to viewModel.accountSummary.value?.accountId
                )
            )
        }
        accountCurrency.setOnClickListener {
            findNavController().navigate(
                R.id.action_global_bottomDialog,
                bundleOf(
                    BottomDialog.ARGS_DIALOG_TYPE to BottomDialog.TYPE_ACCOUNT_CURRENCY,
                    BottomDialog.ARGS_SELECTED_ID_INT to (viewModel.currency.value?.ordinal
                        ?: View.NO_ID),
                )
            )
        }
        setResultListener()
        accountItems.adapter = transactionAdapter
        currencyInfo.setOnClickListener {
            val text = viewModel.course?.let {
                "${it.date.toRUformat()}: 1$ ~ ${it.courses[viewModel.currency.value]} ${viewModel.currency.value!!.icon}"
            }
            Snackbar.make(requireView(), text ?: "no course info", Snackbar.LENGTH_SHORT).show()
        }
        showBottom.setOnClickListener {
            viewModel.accountSummary.value?.accountId?.let {
                val destination =
                    DashboardFragmentDirections.actionKeyboardFragmentToBottomKeyboard(it)
                findNavController().navigate(destination)
            }
        }
        accountIncome.setOnClickListener {
            val value =
                if (viewModel.filterValue.value == DashbordFilter.INCOME) DashbordFilter.ALL else DashbordFilter.INCOME
            viewModel.updateFilter(value)
        }
        accountExpenses.setOnClickListener {
            val value =
                if (viewModel.filterValue.value == DashbordFilter.SPENDING) DashbordFilter.ALL else DashbordFilter.SPENDING
            viewModel.updateFilter(value)
        }
        accountBalance.setOnClickListener {
            viewModel.updateFilter(DashbordFilter.ALL)
        }
        currencyBadge.setImageDrawable(currencyBadgeDrawable)
    }

    private fun updateFilterValue(dashbordFilter: DashbordFilter) {
        Log.d("M_DashboardFragment", "filter $dashbordFilter")
        when (dashbordFilter) {
            DashbordFilter.INCOME -> {

                accountExpenses.isHiglited = false
                accountIncome.isHiglited = true
            }
            DashbordFilter.SPENDING -> {
                accountExpenses.isHiglited = true
                accountIncome.isHiglited = false
            }
            else -> {
                accountExpenses.isHiglited = false
                accountIncome.isHiglited = false
            }
        }
    }

    private fun updateSummary(accountSummary: AccountSummary) {
        accountName.text = accountSummary.description
        accountExpenses.setAmount(accountSummary.expenses.toCurencyFormat())
        accountIncome.setAmount(accountSummary.income.toCurencyFormat())
        accountBalance.setAmount(accountSummary.balance.toCurencyFormat())
        currencyBadgeDrawable.updateCurrency(accountSummary.currencyType.icon)
    }

    private fun updateCurrency(currencyType: CurrencyType?) {
        accountCurrency.text = currencyType?.icon.toString()
    }

    private fun setResultListener() {
        setFragmentResultListener(BottomDialog.REQUEST_FROM_DASHBOARD_ACCOUNT_SELECTION) { _, bundle ->
            val selectedAccountId = bundle.getString(BottomDialog.TYPE_ACCOUNT_ID)
            selectedAccountId?.let { viewModel.updateAccountId(it) }
        }
        setFragmentResultListener(BottomDialog.REQUEST_FROM_DASHBOARD_FRAGMENT_CURRENCY_TYPE) { _, bundle ->
            val selected = CurrencyType.values()[bundle.getInt(BottomDialog.TYPE_ACCOUNT_CURRENCY)]
            viewModel.updateDisplayedCurrency(selected)
        }
    }

    private fun updateAccounts(details: List<TransactionGroupItem>) {
        transactionAdapter.items = details
    }

}

