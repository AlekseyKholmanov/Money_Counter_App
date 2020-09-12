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
import com.example.holmi_production.money_counter_app.main.BaseFragment
import com.example.holmi_production.money_counter_app.model.uiModels.DashboardAccountDetails
import com.example.holmi_production.money_counter_app.ui.adapter.delegate.dashboardTransactionDelegate
import com.example.holmi_production.money_counter_app.ui.adapter.diffUtil.DashboardGroupDiffUtilCallback
import com.example.holmi_production.money_counter_app.ui.adapter.holder.Callback
import com.example.holmi_production.money_counter_app.ui.adapter.items.TransactionGroupItem
import com.example.holmi_production.money_counter_app.ui.dialogs.BottomDialog
import com.example.holmi_production.money_counter_app.ui.viewModels.DashboardViewModel
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import kotlinx.android.synthetic.main.fragment_dashboard.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment : BaseFragment(R.layout.fragment_dashboard) {

    private val viewModel: DashboardViewModel by viewModel()

    private val accountsCallback = object : Callback {
        override fun minusClicked(accountId: String) {
            val directions =
                DashboardFragmentDirections.actionDashboardFragmentToSimpleBottomKeyboard(
                    accountId = accountId,
                    isSubstraction = true
                )
            findNavController().navigate(directions)
        }

        override fun plusClicked(accountId: String) {
            val directions =
                DashboardFragmentDirections.actionDashboardFragmentToSimpleBottomKeyboard(
                    accountId = accountId,
                    isSubstraction = false
                )
            findNavController().navigate(directions)
        }

    }
    private val transactionAdapter by lazy {
        AsyncListDifferDelegationAdapter(
            DashboardGroupDiffUtilCallback(),
            dashboardTransactionDelegate()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(viewModel) {
            accountDetails.observe(viewLifecycleOwner, Observer(::updateAccounts))
        }
//
        accountExpenses.setTitleRes(R.string.expenses)
        accountIncome.setTitleRes(R.string.income)
        accountBalance.setTitleRes(R.string.balance)

        accountName.setOnClickListener {
            findNavController().navigate(
                R.id.action_global_bottomDialog,
                bundleOf(
                    BottomDialog.ARGS_DIALOG_TYPE to BottomDialog.TYPE_ACCOUNT_SELECTION,
                    BottomDialog.ARGS_SELECTED_ID_STRING to viewModel.accountDetails.value?.account?.id
                )
            )
        }
        setResultListener()
        accountItems.adapter = transactionAdapter
        showBottom.setOnClickListener {
            viewModel.accountDetails.value?.account?.id?.let {
                val destination =
                    DashboardFragmentDirections.actionKeyboardFragmentToBottomKeyboard(it)
                findNavController().navigate(destination)
            }
        }
    }

    private fun setResultListener() {
        setFragmentResultListener(BottomDialog.REQUEST_FROM_ACCOUNT_SELECTION) { _, bundle ->
            val selectedAccountId = bundle.getString(BottomDialog.TYPE_ACCOUNT_SELECTION)
            selectedAccountId?.let { viewModel.updateAccountId(it) }
        }
    }

    private fun showFullBottomKeyboard(selectedId: String? = null) {
//        val accountId = selectedId ?: (accounts.adapter as AccountAdapter).items[accounts.currentItem].id
//        val direction =
//            DashboardFragmentDirections.actionKeyboardFragmentToBottomKeyboard(accountId)
//        findNavController().navigate(direction)
    }

    private fun updateAccounts(details: DashboardAccountDetails) {
        accountName.text = details.account.description
        val divided = details.transactions.partition { it.sum > 0 }
        accountExpenses.setAmount(divided.second.sumByDouble { it.sum }.toCurencyFormat())
        accountIncome.setAmount(divided.first.sumByDouble { it.sum }.toCurencyFormat())
        accountBalance.setAmount(details.transactions.sumByDouble { it.sum }.toCurencyFormat())
        transactionAdapter.items =
            details.transactions.groupBy { it.createdDate.withTimeAtStartOfDay() }
                .map { TransactionGroupItem(it.key, it.value) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("M_KeyboardFragment", "view destroyed")
    }

}

