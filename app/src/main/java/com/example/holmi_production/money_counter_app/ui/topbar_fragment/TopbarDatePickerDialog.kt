package com.example.holmi_production.money_counter_app.ui.topbar_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.holmi_production.money_counter_app.R
import kotlinx.android.synthetic.main.dialog_date_picker.*
import org.joda.time.DateTime

class TopbarDatePickerDialog : DialogFragment(){
    companion object {
        fun newInstance() = TopbarDatePickerDialog()
    }

    private var callback: ITopbarDatePickerCallback? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_date_picker,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_category_day.setOnClickListener {
            callback?.datePicked(DateTime().withTimeAtStartOfDay(), DateTime().withTime(23,59,59,999))
            dismiss()
        }

        btn_category_week.setOnClickListener {
            val start = DateTime().withDayOfWeek(1).withTimeAtStartOfDay()
            val end = DateTime().withDayOfWeek(7).withTime(23,59,59,999)
            callback?.datePicked(start,end)
            dismiss()
        }

        btn_category_month.setOnClickListener {
            val startMonth = DateTime().withDayOfMonth(1).withTimeAtStartOfDay()
            val endMonth = startMonth.plusMonths(1).minusDays(1).withTime(23,59,59,999)
            callback?.datePicked(startMonth,endMonth)
            dismiss()
        }
        btn_custom_date.setOnClickListener{

        }
    }

    fun setListener(callback: ITopbarDatePickerCallback) {
        this.callback = callback
    }

    fun deleteListener(){
        callback = null
    }
}
interface ITopbarDatePickerCallback{
    fun datePicked(left:DateTime, right:DateTime)
}