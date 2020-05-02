package com.example.holmi_production.money_counter_app.ui.fragments

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.extensions.hideKeyboard
import com.example.holmi_production.money_counter_app.ui.presenters.FirstLaunchPresenter
import com.example.holmi_production.money_counter_app.ui.presenters.FirstLaunchView
import kotlinx.android.synthetic.main.fragment_first_launch.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import org.joda.time.DateTime

class FirstLaunchFragment : MvpAppCompatFragment(),
    FirstLaunchView {
    override fun showMainScreen() {
//        findNavController().navigate(R.id.action_navFirstLaunch_to_navMain)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_first_launch, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        finish_activity.setOnClickListener {
            presenter.goToMainScreen()
        }
        cost_item_sum.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            //TODO возможно объединить в один или вынести в презентер
            override fun afterTextChanged(s: Editable?) {
                if (cost_item_sum.text!!.isEmpty()) {
                    finish_activity.isEnabled = false
                    sum_per_day.isVisible = false
                } else {
                    sum_per_day.isVisible = true
                    presenter.getSum(s.toString().toDouble())
                }
                if (cost_item_date.text!!.isNotEmpty()) {
                    presenter.getSumPerDay()
                    finish_activity.isEnabled = true
                }
            }
        })

        cost_item_sum.setOnFocusChangeListener { v, hasFocus ->
            if(!hasFocus){
                v.clearFocus()
                v.hideKeyboard()
                Log.d("qwerty","ledt")
            }
        }

        cost_item_date.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (cost_item_sum.text!!.isNotEmpty()) {
                    presenter.getSumPerDay()
                    finish_activity.isEnabled = true
                } else
                    finish_activity.isEnabled = false
            }
        })
        cost_item_date.setOnClickListener {
            it.hideKeyboard()
            val time = DateTime()
            val picker = DatePickerDialog(
                context,
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

    override fun showSumPerDay(sumPerDay: String) {
        sum_per_day.text = sumPerDay
    }

    override fun showDate(pickedDate: String, difference: String) {
        cost_item_date.setText(pickedDate)
        date_difference.text = difference
    }

    private var dateDialog: DatePickerDialog.OnDateSetListener =
        DatePickerDialog.OnDateSetListener { picker, _, _, _ ->
            val time = DateTime(picker.year, picker.month + 1, picker.dayOfMonth, 0, 0)
            presenter.updateDate(time)
        }

    @InjectPresenter
    lateinit var presenter: FirstLaunchPresenter
}