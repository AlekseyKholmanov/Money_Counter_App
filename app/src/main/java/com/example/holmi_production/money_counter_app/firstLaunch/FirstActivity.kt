package com.example.holmi_production.money_counter_app.firstLaunch

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.holmi_production.money_counter_app.App
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppCompatActivity
import kotlinx.android.synthetic.main.first_launch_activity.*
import java.text.SimpleDateFormat
import java.util.*

class FirstActivity : AndroidXMvpAppCompatActivity(), FirstLaunchView, TextWatcher {
    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        finish_activity.isEnabled = (date.text.isNotEmpty() && start_sum.text.isNotEmpty())

    }

    override fun displayDateActivity() {

    }
    var c: Calendar = Calendar.getInstance()
    var dateDialog: DatePickerDialog.OnDateSetListener =
        DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            c.set(Calendar.YEAR,year)
            c.set(Calendar.MONTH,monthOfYear)
            c.set(Calendar.DAY_OF_MONTH,dayOfMonth)
            updateLabel()
        }

    private fun updateLabel() {
        val myFormat = "dd/MM/yy" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.UK)

        date.setText(sdf.format(c.time))
    }

    @InjectPresenter
    lateinit var presenter: FirstLaunchPresenter

    @ProvidePresenter
    fun initPresenter(): FirstLaunchPresenter {
        return App.component.getFirstLaunchPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first_launch_activity)
        finish_activity.setOnClickListener {
            finish()
        }
        start_sum.addTextChangedListener(this)
        date.addTextChangedListener(this)
        date.setOnClickListener {
            val picker = DatePickerDialog(
                this,
                dateDialog,
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)
            )
            picker.datePicker.minDate = System.currentTimeMillis()-1000
            picker.show()
        }
    }

}