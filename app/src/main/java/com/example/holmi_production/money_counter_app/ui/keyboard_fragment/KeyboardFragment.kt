package com.example.holmi_production.money_counter_app.ui.keyboard_fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.holmi_production.money_counter_app.App
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.extensions.getDayAddition
import com.example.holmi_production.money_counter_app.extensions.toCurencyFormat
import com.example.holmi_production.money_counter_app.extensions.withRubleSign
import com.example.holmi_production.money_counter_app.model.entity.Category
import com.example.holmi_production.money_counter_app.model.entity.SpendingWithCategory
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppCompatFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_bottom_keyboard.*
import leakcanary.AppWatcher
import org.joda.time.DateTime

class KeyboardFragment : AndroidXMvpAppCompatFragment(), KeyboardFragmnetView,
    IKeyboardListener, IDatePickerCallback {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_bottom_keyboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        keybaordPart = KeyboardPartFragment.newInstance()
        keybaordPart.setListener(this)
        childFragmentManager.beginTransaction().apply {
            add(R.id.keyboard,keybaordPart)
            commit()
        }
        left_days.setOnClickListener {
            timePickerDialog = TimePickerDialog.newInstance(withMinDate = true)
            timePickerDialog.setListener(this)
            timePickerDialog.show(childFragmentManager, "datePicker")
        }
        if (arguments == null)
            presenter.getCategoryButtonValue()
        else {
            val categoryId = arguments!!.getInt("categoryId")
            presenter.setCategoryButonType(categoryId)
        }
        presenter.getDaysLeft()
        presenter.setObservers()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("M_KeyboardFragment", "onCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.d("M_KeyboardFragment", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("M_KeyboardFragment", "onResume")
    }

    override fun onDestroyView() {
        Log.d("qwerty", "keyboard destroy view")
        left_days.setOnClickListener(null)
        super.onDestroyView()
    }

    override fun onPause() {
        super.onPause()
        Log.d("M_KeyboardFragment", "OnPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("M_KeyboardFragment", "onStop")
    }

    override fun onDestroy() {
        Log.d("M_KeyboardFragment", "onDestroy")
        super.onDestroy()
        AppWatcher.objectWatcher.watch(this)
    }

    @ProvidePresenter
    fun initPresenter(): KeyboardPresenter {
        return App.component.getKeyboardPresenter()
    }

    override fun showNewSumSnack(sum: Double, days: Int) {
        val message = "новая сумма: ${sum.toCurencyFormat()} на ${days.getDayAddition()}"
        Snackbar.make(fragment_keyboard, message, Snackbar.LENGTH_SHORT)
            .show()
    }

    override fun showSnack(spending: SpendingWithCategory) {
        val message = if (spending.spending.isSpending)
            "Расход. ${spending.category[0].description}. ${spending.spending.sum.toCurencyFormat().withRubleSign()}"
        else
            "Доход. ${spending.category[0].description}. ${spending.spending.sum.toCurencyFormat().withRubleSign()}"
        val snack = Snackbar.make(fragment_keyboard, message, Snackbar.LENGTH_SHORT)
        snack.setAction("Отмена") {
            presenter.undoAdding(spending.spending)
        }
        snack.show()
    }

    override fun datePicked(date: DateTime) {
        presenter.recalculateAverageSum(date)
    }

    override fun updateCategoryPickerButton(category: Category) {
        keybaordPart.updateCategoryButton(color = category.color, imageId = category.imageId)
    }

    override fun showCategoryDialog() {
        withCreate = FragmentCategoryPicker.newInstance()
        findNavController().navigate(R.id.action_mainFragment_to_categoryPickerWithCreateFragment)
    }

    override fun showAverageSum(sum: String, isDisplayed: Boolean) {
        val displayed = if (isDisplayed) View.VISIBLE else View.GONE
        new_sum_per_day_text.visibility = displayed
        new_sum_per_day.visibility = displayed
        new_sum_per_day.text = sum
    }

    override fun enterPressed(money: Double, comment: String, isSpending: Boolean) {
        Log.d("qwerty", money.toString())
        presenter.saveSpend(money, comment, isSpending)
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


    override fun showMoney(money: String) {
//        expense.date = money
    }

    private lateinit var timePickerDialog: TimePickerDialog
    private lateinit var withCreate: FragmentCategoryPicker
    private lateinit var keybaordPart:KeyboardPartFragment
    @InjectPresenter
    lateinit var presenter: KeyboardPresenter
}

