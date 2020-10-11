package com.example.holmi_production.money_counter_app.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.main.BaseFragment
import com.example.holmi_production.money_counter_app.main.WithoutToolbar
import com.example.holmi_production.money_counter_app.ui.viewModels.SettingsViewModel
import kotlinx.android.synthetic.main.frament_settings.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class SettingsFragment : BaseFragment(R.layout.frament_settings), WithoutToolbar {

    //TODO change later
    private val transactionLimit = 300

    private val viewModel: SettingsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.transactionCount.observe(viewLifecycleOwner, Observer(::updateTransactionCount))
    }

    private fun updateTransactionCount(count: Int) {
        transactionCount.text = getString(R.string.transaction_count,transactionLimit - count, transactionLimit)
    }
}