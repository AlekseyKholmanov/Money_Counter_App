package com.example.holmi_production.money_counter_app.ui.fragments.onboarding

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.main.BaseFragment
import com.example.holmi_production.money_counter_app.model.enums.CurrencyType
import com.example.holmi_production.money_counter_app.model.enums.PeriodType
import com.example.holmi_production.money_counter_app.ui.dialogs.BottomDialog
import com.example.holmi_production.money_counter_app.ui.viewModels.CreateAccountViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.android.synthetic.main.fragment_onboarding_account_create.*
import org.joda.time.DateTime
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateAccountFragment : BaseFragment(R.layout.fragment_onboarding_account_create) {

    private val viewModel: CreateAccountViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createAccount.setOnClickListener {
            viewModel.createAccount(
                description = accountNameEditText.text.toString(),
                isHidden = hiddenMode.isChecked,
                isCalculatePerDay = sumPerDayMode.isChecked,
                password = null,
                startBalance = accountBalanceEditText.text.toString().toDouble()
            )
            findNavController().navigate(R.id.dashboardFragment)
        }
        accountCurrencyEditText.setOnClickListener {
            findNavController().navigate(
                R.id.action_global_bottomDialog, bundleOf(
                    BottomDialog.ARGS_SELECTED_ID to (viewModel.currencyType.value?.ordinal ?: View.NO_ID),
                    BottomDialog.ARGS_DIALOG_TYPE to BottomDialog.TYPE_ACCOUNT_CURRENCY
                )
            )
        }

        viewModel.currencyType.observe(viewLifecycleOwner, Observer(::updateCurrencyType))

        setResultListener()
    }

    private fun updateCurrencyType(currencyType: CurrencyType?) {
        val text = if (currencyType == null) "" else "${currencyType.name}: ${currencyType.icon}"
        Log.d("M_CreateAccountFragment", "$text")
        accountCurrencyEditText.setText(text)
    }

    private fun setResultListener() {
        setFragmentResultListener(BottomDialog.REQUEST_FROM_ACCOUNT_CURRENCY_TYPE) { _, bundle ->
            val selected = CurrencyType.values()[bundle.getInt(BottomDialog.TYPE_ACCOUNT_CURRENCY)]
            viewModel.updateCurrency(selected)
        }
    }
}