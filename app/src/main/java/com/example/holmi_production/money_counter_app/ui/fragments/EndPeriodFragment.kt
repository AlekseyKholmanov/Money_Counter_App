package com.example.holmi_production.money_counter_app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.holmi_production.money_counter_app.App
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.ui.presenters.EndPeriodPresenter
import com.example.holmi_production.money_counter_app.ui.presenters.EndPeriodView
import kotlinx.android.synthetic.main.fragment_end_period.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class EndPeriodFragment : MvpAppCompatFragment(),
    EndPeriodView {

    @ProvidePresenter
    fun initPresenter(): EndPeriodPresenter {
        return App.component.getEndPeriodPresenter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getSum()
        endPeriod_next.setOnClickListener {
            presenter.goToMain()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_end_period, container, false)
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

    @InjectPresenter
    lateinit var presenter: EndPeriodPresenter

}