package com.example.holmi_production.money_counter_app.keyboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.holmi_production.money_counter_app.App
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.extensions.getDayAddition
import com.example.holmi_production.money_counter_app.extensions.toCurencyFormat
import com.example.holmi_production.money_counter_app.model.CategoryType
import com.example.holmi_production.money_counter_app.model.Spending
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppCompatFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_bottom_keyboard.*
import kotlinx.android.synthetic.main.keyboard.*
import kotlinx.android.synthetic.main.keyboard.view.*
import leakcanary.AppWatcher
import org.joda.time.DateTime

class KeyboardFragment : AndroidXMvpAppCompatFragment(), KeyboardFragmnetView,
    IKeyboardListener, ICategoryPickedListener, IDatePickerCallback {

    override fun showNewSumSnack(sum: Double, days: Int) {
        val message = "новая сумма: ${sum.toCurencyFormat()} на ${days.getDayAddition()}"
        Snackbar.make(fragment_keyboard, message, Snackbar.LENGTH_SHORT)
            .show()
    }

    override fun showAfterAddingSnack(spending: Spending) {
        val message = if (spending.isSpending)
            "Расход. ${CategoryType.values()[spending.categoryType].description}. ${spending.sum} рублей"
        else
            "Доход. ${CategoryType.values()[spending.categoryType].description}. ${spending.sum} рублей"
        val snack = Snackbar.make(fragment_keyboard, message, Snackbar.LENGTH_SHORT)
        snack.setAction("Отмена") {
            presenter.undoAdding(spending)
        }
        snack.show()
    }

    override fun showCategoryButton(categoryType: CategoryType) {
        keyboard.setCategoryButtonValue(categoryType)
    }

    override fun datePicked(date: DateTime) {
        presenter.recalculateAverageSum(date)
    }

    override fun categoryPicked(type: CategoryType) {
        key_category.setBackgroundColor(type.color)
        key_category.findViewById<ImageView>(R.id.categoryImage).setImageResource(CategoryType.getImage(type))
        presenter.setType(type.id)
    }

    override fun showCategoryDialog() {
        categoryPickerFragment = CategoryPickerFragment.newInstance()
        categoryPickerFragment.setListener(this)
        findNavController().navigate(R.id.action_mainFragment_to_categoryPickerFragment)
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

    override fun showSpentSum(sum: String) {
        spent.text = sum
    }

    override fun showMoney(money: String) {
//        expense.date = money
    }

    private lateinit var key: Keyboard
    private lateinit var timePickerDialog: TimePickerDialog

    @InjectPresenter
    lateinit var presenter: KeyboardFragmentPresenter

    private lateinit var categoryPickerFragment: CategoryPickerFragment

    @ProvidePresenter
    fun initPresenter(): KeyboardFragmentPresenter {
        return App.component.getKeyboardPresenter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_bottom_keyboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        key = view.findViewById(R.id.keyboard)

        var savedSum = "0"
        if (savedInstanceState != null)
            savedSum = savedInstanceState.getString("SUM")

        Log.d("M_KeyboardFragment", "getted$savedSum")
        key.setSum(savedSum)

        key.setListener(this)
        left_days.setOnClickListener {
            timePickerDialog = TimePickerDialog.newInstance(withMinDate = true)
            timePickerDialog.setListener(this)
            timePickerDialog.show(childFragmentManager, "datePicker")
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

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("SUM", purshace_sum_textview.text.toString())
        Log.d("M_KeyboardFragment", "saved")
        super.onSaveInstanceState(outState)
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
}

