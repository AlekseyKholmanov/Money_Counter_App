package com.example.holmi_production.money_counter_app.keyboard

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import leakcanary.AppWatcher
import org.joda.time.DateTime

class TimePickerDialog : DialogFragment(), DatePickerDialog.OnDateSetListener {

    companion object {
        fun newInstance(startDate: DateTime = DateTime(), withMinDate: Boolean): TimePickerDialog {
            val dialog = TimePickerDialog()
            if (withMinDate) {
                val args = Bundle()
                args.putLong("startDate", startDate.millis)
                dialog.arguments = args
            }
            return dialog
        }
    }

    private var callback: IDatePickerCallback? = null
    override fun onDateSet(picker: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        val time = DateTime(picker.year, picker.month + 1, picker.dayOfMonth, 0, 0)
        callback?.datePicked(time)
        Log.d("qwerty", time.toString())

    }

    fun setListener(callback: IDatePickerCallback) {
        this.callback = callback
    }

    fun deleteListener(){
        callback = null
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val time = DateTime()
        val startDate: Long = arguments?.getLong("startDate") ?: DateTime().minusMonths(1).millis
        val dialog = DatePickerDialog(context!!, this, time.year, time.monthOfYear - 1, time.dayOfMonth)
        dialog.datePicker.minDate = startDate
        return dialog
    }

    override fun onDestroy() {
        super.onDestroy()
        AppWatcher.objectWatcher.watch(this)
    }
}

interface IDatePickerCallback {
    fun datePicked(date: DateTime)
}