package com.example.holmi_production.money_counter_app.ui.keyboard_fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.holmi_production.money_counter_app.App
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.extensions.getDayAddition
import com.example.holmi_production.money_counter_app.extensions.toCurencyFormat
import com.example.holmi_production.money_counter_app.extensions.withRubleSign
import com.example.holmi_production.money_counter_app.main.MainActivity
import com.example.holmi_production.money_counter_app.model.SpDirection
import com.example.holmi_production.money_counter_app.model.entity.Category
import com.example.holmi_production.money_counter_app.model.entity.Spending
import com.example.holmi_production.money_counter_app.model.entity.SubCategory
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppCompatFragment
import com.example.holmi_production.money_counter_app.ui.settings.SettingsPresenter
import com.google.android.material.snackbar.Snackbar
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_bottom_keyboard.*
import leakcanary.AppWatcher
import org.joda.time.DateTime
import javax.inject.Inject

class KeyboardFragment : AndroidXMvpAppCompatFragment(), KeyboardFragmnetView,
    IKeyboardListener, IDatePickerCallback {

    companion object {
        fun newInstance(bundle: Bundle?): KeyboardFragment {
            val fragment = KeyboardFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom_keyboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        keyboardPart = KeyboardPartFragment.newInstance()
        keyboardPart.setListener(this)
        childFragmentManager.beginTransaction().apply {
            replace(R.id.keyboard, keyboardPart)
            commit()
        }
        left_days.setOnClickListener {
            presenter.observeEndPeriodDate()
        }
        if (arguments == null)
            presenter.getCategoryButtonValue()
        else {
            val categoryId = arguments!!.getInt("categoryId")
            presenter.setCategoryButonType(categoryId)
        }
        //presenter.getDaysLeft()
        presenter.observeData()
        presenter.observeEndPeriodDate()
        Log.d("M_KeyboardFragment", "View Created")
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
        Log.d("M_KeyboardFragment", "fragment keyboard destroy view")
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

    override fun showNewSumSnack(sum: Double, days: Int) {
        val message = "новая сумма: ${sum.toCurencyFormat()} на ${days.getDayAddition()}"
        Snackbar.make(fragment_keyboard, message, Snackbar.LENGTH_SHORT)
            .show()
    }

    override fun showActionButtons(directions: List<SpDirection>) {
        keyboardPart.showActionButtons(directions)
    }

    override fun showSubcategoryMenu(subcategories: List<SubCategory>, color: Int) {
        keyboardPart.showChipsContainer(subcategories, color)
    }

    override fun showSnack(
        category: Pair<Category, List<SubCategory>>,
        spending: Spending
    ) {
        val message = if (spending.isSpending == SpDirection.SPENDING)
            "Расход. ${category.first.description}. ${spending.sum.toCurencyFormat().withRubleSign()}"
        else
            "Доход. ${category.first.description}. ${spending.sum.toCurencyFormat().withRubleSign()}"
        val snack = Snackbar.make(fragment_keyboard, message, Snackbar.LENGTH_SHORT)
        snack.setAction("Отмена") {
            presenter.undoAdding(spending)
        }
        snack.show()
    }

    override fun datePicked(date: DateTime) {
//        presenter.recalculateAverageSum(date)
    }

    override fun updateCategoryPickerButton(category: Category?) {
        keyboardPart.updateCategoryButton(category = category)
    }

    override fun showCategoryDialog() {
        (activity as MainActivity).showCategoryPicker()
    }

    override fun showAverageSum(sum: String, isDisplayed: Boolean) {
        val displayed = if (isDisplayed) View.VISIBLE else View.GONE
        new_sum_per_day_text.visibility = displayed
        new_sum_per_day.visibility = displayed
        new_sum_per_day.text = sum
    }

    override fun enterPressed(
        money: Double,
        comment: String,
        isSpending: SpDirection,
        subcategoryId: Int?
    ) {
        Log.d("qwerty", money.toString())
        presenter.saveSpend(money, comment, isSpending, subcategoryId)
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

    private lateinit var keyboardPart: KeyboardPartFragment

    @Inject
    @InjectPresenter
    lateinit var presenter: KeyboardPresenter

    @ProvidePresenter
    fun initPresenter(): KeyboardPresenter = App.component.getKeyboardPresenter()
}

