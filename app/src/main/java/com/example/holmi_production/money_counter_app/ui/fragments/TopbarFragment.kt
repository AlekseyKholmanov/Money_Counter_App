package com.example.holmi_production.money_counter_app.ui.fragments

import android.os.Bundle
import android.view.View
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.main.BaseFragment
import com.example.holmi_production.money_counter_app.model.enums.PeriodType
import com.example.holmi_production.money_counter_app.ui.dialogs.ITopbarDatePickerCallback
import org.joda.time.DateTime


class TopbarFragment : BaseFragment(R.layout.fragment_topbar),
    ITopbarDatePickerCallback {

    companion object{
        fun newInstance(): TopbarFragment {
            return TopbarFragment()
        }
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//
//        btn_topbar_left.setOnClickListener {
//            presenter.setNewPeriod(false)
//        }
//        btn_topbar_right.setOnClickListener {
//            presenter.setNewPeriod(true)
//        }
//        tv_topbar_text.setOnClickListener {
//            val dialog = TopbarDatePickerDialog.newInstance()
//            dialog.setListener(this)
//            dialog.show(childFragmentManager, "TopbarDatePicker")
//        }
//        presenter.getPeriod()
    }

//    override fun showDate(date:String) {
//        tv_topbar_text.text = date
//    }

    override fun datePicked(type: PeriodType) {
//        presenter.setPeriod(type)
    }

    override fun datePicked(left: DateTime, right: DateTime) {
//        presenter.setPeriod(left, right)
    }

//    @Inject
//    lateinit var vmFactory : ViewModelProvider.Factory
//
//    val keyboardViewModel: TopbarViewModel by viewModels{vmFactory}

}