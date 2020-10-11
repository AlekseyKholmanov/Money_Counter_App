package com.example.holmi_production.money_counter_app.ui.fragments.onboarding

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.main.BaseFragment
import com.example.holmi_production.money_counter_app.model.enums.CurrencyType
import com.example.holmi_production.money_counter_app.ui.LaunchDestination
import com.example.holmi_production.money_counter_app.ui.MainActivity
import com.example.holmi_production.money_counter_app.ui.dialogs.BottomDialog
import com.example.holmi_production.money_counter_app.ui.viewModels.CreateAccountViewModel
import kotlinx.android.synthetic.main.fragment_onboarding_account_create.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import reactivecircus.flowbinding.android.widget.editorActionEvents
import reactivecircus.flowbinding.android.widget.textChanges

class CreateAccountFragment : BaseFragment(R.layout.fragment_onboarding_account_create) {

    private val viewModel: CreateAccountViewModel by viewModel()

    private val args: CreateAccountFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        accountNameEditText.textChanges()
            .map { it.isNotBlank() }
            .onEach {
                createAccount.visibility = if(it) View.VISIBLE else View.GONE
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        createAccount.setOnClickListener {
            viewModel.createAccount(
                description = accountNameEditText.text.toString(),
                isHidden = hiddenMode.isChecked,
                isCalculatePerDay = sumPerDayMode.isChecked,
                password = null,
                startBalance = if (accountBalanceEditText.text.isNullOrEmpty()) {
                    0.0
                } else {
                    accountBalanceEditText.text.toString()
                        .toDouble()
                }
            )
            if (args.from == LaunchDestination.ONBOARDING) {
                startActivity(
                    Intent(
                        requireContext(),
                        MainActivity::class.java
                    )
                )
                requireActivity().finishAfterTransition()
            } else {
                findNavController().popBackStack()
            }
        }
        accountCurrencyEditText.setOnClickListener {
            findNavController().navigate(
                R.id.action_global_bottomDialog, bundleOf(
                    BottomDialog.ARGS_SELECTED_ID_INT to (viewModel.currencyType.value?.ordinal
                        ?: View.NO_ID),
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