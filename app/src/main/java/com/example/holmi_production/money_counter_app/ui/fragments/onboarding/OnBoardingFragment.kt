package com.example.holmi_production.money_counter_app.ui.fragments.onboarding

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.main.BaseFragment
import com.example.holmi_production.money_counter_app.ui.viewModels.DashboardViewModel
import com.example.holmi_production.money_counter_app.ui.viewModels.OnBoardingViewModel
import kotlinx.android.synthetic.main.fragment_onboarding.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class OnBoardingFragment: BaseFragment(R.layout.fragment_onboarding) {


    private val viewModel: OnBoardingViewModel by viewModel()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createManual.setOnClickListener {
            val direction = OnBoardingFragmentDirections.actionOnBoardingFragmentToCreateAccountFragment()
            findNavController().navigate(direction)
        }
        createAuto.setOnClickListener {
            viewModel.createAccount()
            val graph = findNavController().graph
            graph.startDestination =
                R.id.dashboardFragment
            with(findNavController()) {
                restoreState(bundleOf())
                this.graph = graph
            }
        }
    }
}