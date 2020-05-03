package com.example.holmi_production.money_counter_app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.holmi_production.money_counter_app.App
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.di.components.AppComponent
import com.example.holmi_production.money_counter_app.main.BaseFragment
import com.example.holmi_production.money_counter_app.ui.presenters.EndPeriodPresenter
import com.example.holmi_production.money_counter_app.ui.presenters.EndPeriodView
import kotlinx.android.synthetic.main.fragment_end_period.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class EndPeriodFragment : BaseFragment(R.layout.fragment_end_period),
    EndPeriodView {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getSum()
        endPeriod_next.setOnClickListener {
            presenter.goToMain()
        }
    }

    override fun inject() {
        AppComponent.instance.inject(this)
    }


    override fun showDatePeriod(start: String, end: String) {
        end_period_date_row.text = "За период с $start по $end:"
    }

    override fun showLeftSum(sum: String) {
        end_period_left_sum.text = sum
    }

    override fun showSpendedSum(sum: String) {
        end_period_spended_sum.text = sum
    }

    override fun ShowAverageSumForPeriod(sum: String) {
        end_period_average_sum.text = sum
    }

    override fun goToMain() {
//        findNavController().navigate(R.id.action_navEndPeriod_to_navMain)
    }

    @Inject
    lateinit var presenterProvider: Provider<EndPeriodPresenter>

    private val presenter by moxyPresenter { presenterProvider.get() }

}