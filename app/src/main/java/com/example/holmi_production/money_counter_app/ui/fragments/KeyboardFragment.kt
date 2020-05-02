package com.example.holmi_production.money_counter_app.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.holmi_production.money_counter_app.App
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.di.components.AppComponent
import com.example.holmi_production.money_counter_app.extensions.getDayAddition
import com.example.holmi_production.money_counter_app.extensions.toCurencyFormat
import com.example.holmi_production.money_counter_app.extensions.withRubleSign
import com.example.holmi_production.money_counter_app.main.BaseFragment
import com.example.holmi_production.money_counter_app.main.MainActivity
import com.example.holmi_production.money_counter_app.model.SpDirection
import com.example.holmi_production.money_counter_app.model.entity.CategoryEntity
import com.example.holmi_production.money_counter_app.model.entity.SpendingEntity
import com.example.holmi_production.money_counter_app.model.entity.SubCategoryEntity
import com.example.holmi_production.money_counter_app.ui.dialogs.IDatePickerCallback
import com.example.holmi_production.money_counter_app.ui.presenters.KeyboardFragmnetView
import com.example.holmi_production.money_counter_app.ui.presenters.KeyboardPresenter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_bottom_keyboard.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import org.joda.time.DateTime
import javax.inject.Inject

class KeyboardFragment : BaseFragment(R.layout.fragment_bottom_keyboard),
    KeyboardFragmnetView,
    IKeyboardListener,
    IDatePickerCallback {

    companion object {
        fun newInstance(bundle: Bundle?): KeyboardFragment {
            val fragment =
                KeyboardFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun inject() {
        AppComponent.instance.inject(this)
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
        initializeKeyboard()
        left_days.setOnClickListener {
            presenter.observeEndPeriodDate()
        }
        if (arguments == null)
            presenter.getCategoryButtonValue()
        else {
            val categoryId = requireArguments().getInt("categoryId")
            presenter.setCategoryButonType(categoryId)
        }
        presenter.observeData()
        presenter.observeEndPeriodDate()
        Log.d("M_KeyboardFragment", "View Created")
    }

    override fun showNewSumSnack(sum: Double, days: Int) {
        val message = "новая сумма: ${sum.toCurencyFormat()} на ${days.getDayAddition()}"
        Snackbar.make(fragment_keyboard, message, Snackbar.LENGTH_SHORT)
            .show()
    }

    override fun showActionButtons(directions: List<SpDirection>) {
        keyboardPart.showActionButtons(directions)
    }

    override fun showSubcategoryMenu(subcategories: List<SubCategoryEntity>, color: Int) {
        keyboardPart.showChipsContainer(subcategories, color)
    }

    override fun showSnack(
        category: Pair<CategoryEntity, List<SubCategoryEntity>>,
        spending: SpendingEntity
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

    override fun updateCategoryPickerButton(category: CategoryEntity?) {
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

    private fun initializeKeyboard() {
        keyboardPart =
            KeyboardPartFragment.newInstance()
        keyboardPart.setListener(this)
        childFragmentManager.beginTransaction().apply {
            replace(R.id.keyboard, keyboardPart)
            commit()
        }
    }

    private lateinit var keyboardPart: KeyboardPartFragment

    @Inject
    @InjectPresenter
    lateinit var presenter: KeyboardPresenter

}

