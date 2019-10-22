package com.example.holmi_production.money_counter_app.ui.topbar_fragment

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate
import com.appeaser.sublimepickerlibrary.datepicker.SublimeDatePicker
import com.example.holmi_production.money_counter_app.R
import kotlinx.android.synthetic.main.dialog_date_picker.*
import org.joda.time.DateTime

class TopbarDatePickerDialog : AppCompatDialogFragment(),SublimeDatePicker.OnDateChangedListener{
    override fun onDateChanged(view: SublimeDatePicker?, selectedDate: SelectedDate?) {

    }
    lateinit var btnDay:Button
    lateinit var btnWeek:Button
    lateinit var btnMonth:Button
    lateinit var btnCustom:Button

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context!!)
        val view = activity!!.layoutInflater.inflate(R.layout.dialog_date_picker, null)
        builder.setView(view)
        btnDay = view.findViewById(R.id.btn_category_day)
        btnWeek = view.findViewById(R.id.btn_category_week)
        btnMonth = view.findViewById(R.id.btn_category_month)
        btnCustom = view.findViewById(R.id.btn_custom_date)
        btnDay.setOnClickListener {
            callback?.datePicked(DateTime().withTimeAtStartOfDay(), DateTime().withTime(23,59,59,999))
            dismiss()
        }

        btnWeek.setOnClickListener {
            val start = DateTime().withDayOfWeek(1).withTimeAtStartOfDay()
            val end = DateTime().withDayOfWeek(7).withTime(23,59,59,999)
            callback?.datePicked(start,end)
            dismiss()
        }

        btnMonth.setOnClickListener {
            val startMonth = DateTime().withDayOfMonth(1).withTimeAtStartOfDay()
            val endMonth = startMonth.plusMonths(1).minusDays(1).withTime(23,59,59,999)
            callback?.datePicked(startMonth,endMonth)
            dismiss()
        }
        btnCustom.setOnClickListener{
            //            val time = DateTime.now()
//            val now = Calendar.getInstance()
//            val dialog = SublimeDatePicker(context)
//
//            dialog.init(SelectedDate(Calendar.getInstance()),true, this)

        }
        return builder.create()
    }

    companion object {
        fun newInstance() = TopbarDatePickerDialog()
    }

    private var callback: ITopbarDatePickerCallback? = null

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