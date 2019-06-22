package com.example.holmi_production.money_counter_app.main

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import org.joda.time.DateTime

class TimePickerDialog :DialogFragment(),DatePickerDialog.OnDateSetListener{

    companion object{
        fun newInstance(): TimePickerDialog {
            return TimePickerDialog()
        }
    }

    lateinit var callback:IDatePickerCallback

    override fun onDateSet(picker: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        val time = DateTime(picker.year, picker.month-1, picker.dayOfMonth, 0, 0)
        callback.datePicked(time)
        Log.d("qwerty",time.toString())

    }
    fun setListener(callback: IDatePickerCallback){
        this.callback=callback
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val time= DateTime()
        return DatePickerDialog(context!!, this, time.year,time.monthOfYear-1,time.dayOfMonth)
    }
}

interface IDatePickerCallback{
    fun datePicked(date:DateTime)
}