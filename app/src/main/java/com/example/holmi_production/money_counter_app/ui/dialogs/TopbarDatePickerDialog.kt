package com.example.holmi_production.money_counter_app.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.enums.PeriodType
import org.joda.time.DateTime

class TopbarDatePickerDialog : AppCompatDialogFragment(){
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
            callback?.datePicked(PeriodType.DAY)
            dismiss()
        }

        btnWeek.setOnClickListener {
            callback?.datePicked(PeriodType.WEEK)
            dismiss()
        }

        btnMonth.setOnClickListener {
            callback?.datePicked(PeriodType.MONTH)
            dismiss()
        }
        btnCustom.setOnClickListener{
            //            val time = DateTime.now()
//            val now = Calendar.getInstance()
//            val dialog = SublimeDatePicker(context)
//            dialog.init(SelectedDate(Calendar.getInstance()),true, this)
        }
        return builder.create()
    }

    companion object {
        fun newInstance() =
            TopbarDatePickerDialog()
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
    fun datePicked(type: PeriodType)
    fun datePicked(start:DateTime, end:DateTime)
}