package com.example.holmi_production.money_counter_app.ui.fragments.onboarding

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.main.BaseFragment
import com.example.holmi_production.money_counter_app.ui.viewModels.CreateAccountViewModel
import kotlinx.android.synthetic.main.fragment_onboarding_account_create.*
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
    }
}