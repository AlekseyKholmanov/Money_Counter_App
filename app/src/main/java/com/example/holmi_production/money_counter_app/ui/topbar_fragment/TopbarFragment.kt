package com.example.holmi_production.money_counter_app.ui.topbar_fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.di.modules.Injectable
import com.example.holmi_production.money_counter_app.model.PeriodTypeEnums
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppCompatFragment
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_topbar.*
import leakcanary.AppWatcher
import org.joda.time.DateTime
import javax.inject.Inject


class TopbarFragment : AndroidXMvpAppCompatFragment(), TopbarView, ITopbarDatePickerCallback, Injectable {

    companion object{
        fun newInstance(): TopbarFragment {
            return TopbarFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_topbar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_topbar_left.setOnClickListener {
            presenter.setNewPeriod(false)
        }
        btn_topbar_right.setOnClickListener {
            presenter.setNewPeriod(true)
        }
        tv_topbar_text.setOnClickListener {
            val dialog = TopbarDatePickerDialog.newInstance()
            dialog.setListener(this)
            dialog.show(childFragmentManager, "TopbarDatePicker")
        }
        presenter.getPeriod()
    }

    override fun showDate(date:String) {
        tv_topbar_text.text = date
    }

    override fun datePicked(type: PeriodTypeEnums) {
        presenter.setPeriod(type)
    }

    override fun datePicked(left: DateTime, right: DateTime) {
        presenter.setPeriod(left, right)
    }

    override fun onDestroy() {
        super.onDestroy()
        AppWatcher.objectWatcher.watch(this)
    }

    @ProvidePresenter
    fun providePresenter(): TopbarPresenter? = presenter


    @Inject
    @InjectPresenter
    lateinit var presenter: TopbarPresenter
}