package com.example.holmi_production.money_counter_app.firstLaunch

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.core.view.isVisible
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.holmi_production.money_counter_app.App
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.first_launch_activity.*
import org.joda.time.DateTime

class FirstActivity : AndroidXMvpAppCompatActivity(), FirstLaunchView {
    override fun showMainScreen() {
        finish()
    }

    override fun showSumPerDay(sumPerDay: String) {
        sum_per_day.text = sumPerDay
    }

    override fun onBackPressed() {
        Snackbar.make(first_launch_activity,"введите дату и сумму", Snackbar.LENGTH_SHORT).show()
    }

    override fun showDate(pickedDate: String, difference: String) {
        date.setText(pickedDate)
        date_difference.text = difference
    }

    private var dateDialog: DatePickerDialog.OnDateSetListener =
        DatePickerDialog.OnDateSetListener { picker, _, _, _ ->
            val time = DateTime(picker.year, picker.month + 1, picker.dayOfMonth, 0, 0)
            presenter.updateDate(time)
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
            presenter.goToMainScreen()
        }
        start_sum.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            //TODO возможно объединить в один или вынести в презентер
            override fun afterTextChanged(s: Editable?) {
                if (start_sum.text.isEmpty()) {
                    finish_activity.isEnabled = false
                    sum_per_day.isVisible = false
                } else {
                    sum_per_day.isVisible = true
                    presenter.getSum(s.toString().toDouble())
                }
                if (date.text.isNotEmpty()) {
                    presenter.getSumPerDay()
                    finish_activity.isEnabled = true
                }
            }
        })
        date.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (start_sum.text.isNotEmpty()) {
                    presenter.getSumPerDay()
                    finish_activity.isEnabled = true
                } else
                    finish_activity.isEnabled = false
            }
        })
        date.setOnClickListener {
            val time = DateTime()
            val picker = DatePickerDialog(
                this,
                dateDialog,
                time.year,
                time.monthOfYear - 1,
                time.dayOfMonth
            )
            picker.datePicker.minDate =
                DateTime()
                    .withTimeAtStartOfDay()
                    .plusDays(1)
                    .millis
            picker.show()
        }
    }

}