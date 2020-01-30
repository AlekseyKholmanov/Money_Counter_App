package com.example.holmi_production.money_counter_app.ui.end_period_fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.holmi_production.money_counter_app.App
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppCompatFragment
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_end_period.*
import leakcanary.AppWatcher

class EndPeriodFragment : AndroidXMvpAppCompatFragment(), EndPeriodView {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
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

    override fun onDestroy() {
        super.onDestroy()
        AppWatcher.objectWatcher.watch(this)
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