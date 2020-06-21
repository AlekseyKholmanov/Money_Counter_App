package com.example.holmi_production.money_counter_app.ui.fragments.onboarding

import android.os.Bundle
import android.view.View
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.main.BaseFragment
import com.example.holmi_production.money_counter_app.ui.viewModels.CreateAccountViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateAccountFragment : BaseFragment(R.layout.fragment_onboarding_account_create) {

    private val viewModel: CreateAccountViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}