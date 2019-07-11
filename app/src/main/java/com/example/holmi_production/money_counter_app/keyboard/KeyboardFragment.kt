package com.example.holmi_production.money_counter_app.keyboard

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.holmi_production.money_counter_app.App
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.CategoryType
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppCompatFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_bottom_keyboard.*
import kotlinx.android.synthetic.main.keyboard.*
import org.joda.time.DateTime

class KeyboardFragment : AndroidXMvpAppCompatFragment(), KeyboardFragmnetView,
    IKeyboardListener, ICategoryPickedListener, IDatePickerCallback {

    private lateinit var dialog: CategoryPickerFragment

    override fun showCategoryButton(categoryType: CategoryType) {
        keyboard.setCategoryButtonValue(categoryType)
    }

    override fun showSnack(message: String) {

        Snackbar.make(fragment_keyboard, message, Snackbar.LENGTH_SHORT)
            .show()
    }

    override fun datePicked(date: DateTime) {
        presenter.recalculateAverageSum(date)
    }

    override fun categoryPicked(type: CategoryType) {
        key_category.setBackgroundColor(type.color)
        key_category.findViewById<TextView>(R.id.mainText).text = type.description
        presenter.setType(type.id)
    }

    override fun showCategoryDialog() {
        dialog = CategoryPickerFragment.newInstance()
        dialog.setListener(this)
        findNavController().navigate(R.id.action_mainFragment_to_categoryPickerFragment)
    }

    override fun showAverageSum(sum: String, isDisplayed: Boolean) {
        val displayed = if (isDisplayed) View.VISIBLE else View.GONE
        new_sum_per_day_text.visibility = displayed
        new_sum_per_day.visibility = displayed
        new_sum_per_day.text = sum
    }

    override fun enterPressed(money: Double) {
        Log.d("qwerty", money.toString())
        presenter.saveSpend(money)
    }

    override fun moneyUpdated(money: Double) {
    }

    override fun showDaysLeft(days: String) {
        left_days.text = days
    }

    override fun showSumPerDay(money: String) {
        sum_per_day.text = money
    }

    override fun showIncomeSum(money: String) {
        left.text = money
    }

    override fun showSpentSum(sum: String) {
        spent.text = sum
    }

    override fun showMoney(money: String) {
//        expense.text = money
    }

    private lateinit var key: Keyboard

    @InjectPresenter
    lateinit var presenter: KeyboardFragmentPresenter

    @ProvidePresenter
    fun initPresenter(): KeyboardFragmentPresenter {
        return App.component.getKeyboardPresenter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_bottom_keyboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        key = view.findViewById(R.id.keyboard)
        key.setListener(this)
        left_days.setOnClickListener {
            val dialog = TimePickerDialog.newInstance(withMinDate = true)
            dialog.setListener(this)
            dialog.show(childFragmentManager, "datePicker")
        }
        if (arguments != null) {
            val id = arguments!!.getInt("categoryId")
            presenter.setType(id)
        }
        presenter.getDaysLeft()
        presenter.getCategoryButtonValue()
        presenter.setObservers()
        super.onViewCreated(view, savedInstanceState)
    }
}

