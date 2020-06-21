package com.example.holmi_production.money_counter_app.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.main.BaseFragment
import com.example.holmi_production.money_counter_app.model.CategoryDetails
import com.example.holmi_production.money_counter_app.ui.viewModels.DashboardViewModel
import kotlinx.android.synthetic.main.fragment_dashboard_old.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment : BaseFragment(R.layout.fragment_dashboard_old) {

    private val dashboardViewModel: DashboardViewModel by viewModel()
    lateinit var part: Fragment



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initToolbar()

        with(dashboardViewModel) {
        }
        showBottom.setOnClickListener {
            val direction = DashboardFragmentDirections.actionKeyboardFragmentToBottomKeyboard()
            findNavController().navigate(direction)
        }

//        keyboard.setListener(object : IKeyboardListener {
////            override fun enterPressed(
////                money: Double,
////                comment: String,
////                isSpending: SpDirection,
////                subcategoryId: Int?
////            ) {
////                //TODO save spending here
////            }
////
////            override fun showCategoryDialog() {
////                findNavController().navigate(R.id.selectCategoryFragment)
////            }
////        })


//        left_days.setOnClickListener {
//            presenter.observeEndPeriodDate()
//        }
//        if (arguments == null)
//            presenter.getCategoryButtonValue()
//        else {
//            val categoryId = requireArguments().getInt("categoryId")
//            presenter.setCategoryButonType(categoryId)
//        }
//        presenter.observeData()
//        presenter.observeEndPeriodDate()
    }

    private fun updateCategory(categoryDetails: CategoryDetails?) {

//        keyboard.showActionButtons(categoryDetails?.category?.spendingDirection)
//
//        keyboard.setCategory(categoryDetails?.category )

        //TODO show subcategory here
    }

    override fun onDestroyView() {
            super.onDestroyView()
//        keyboard.setListener(null)
        Log.d("M_KeyboardFragment", "view destroyed")
    }

//    override fun showNewSumSnack(sum: Double, days: Int) {
//        val message = "новая сумма: ${sum.toCurencyFormat()} на ${days.getDayAddition()}"
//        Snackbar.make(fragment_keyboard, message, Snackbar.LENGTH_SHORT)
//            .show()
//    }
//
//    override fun showActionButtons(directions: List<SpDirection>) {
//        keyboardPart.showActionButtons(directions)
//    }
//
//    override fun showSubcategoryMenu(subcategories: List<SubCategoryEntity>, color: Int) {
//        keyboardPart.showChipsContainer(subcategories, color)
//    }
//
//    override fun showSnack(
//        category: Pair<CategoryEntity, List<SubCategoryEntity>>,
//        spending: SpendingEntity
//    ) {
//        val message = if (spending.isSpending == SpDirection.SPENDING)
//            "Расход. ${category.first.description}. ${spending.sum.toCurencyFormat().withRubleSign()}"
//        else
//            "Доход. ${category.first.description}. ${spending.sum.toCurencyFormat().withRubleSign()}"
//        val snack = Snackbar.make(fragment_keyboard, message, Snackbar.LENGTH_SHORT)
//        snack.setAction("Отмена") {
////            presenter.undoAdding(spending)
//        }
//        snack.show()
//    }

//    override fun datePicked(date: DateTime) {
////        presenter.recalculateAverageSum(date)
//    }
//
//    override fun updateCategoryPickerButton(category: CategoryEntity?) {
//        keyboardPart.updateCategoryButton(category = category)
//    }

//    override fun showCategoryDialog() {
//        findNavController().navigate(R.id.selectCategoryFragment)
//    }

//    override fun showAverageSum(sum: String, isDisplayed: Boolean) {
//        val displayed = if (isDisplayed) View.VISIBLE else View.GONE
//        new_sum_per_day_text.visibility = displayed
//        new_sum_per_day.visibility = displayed
//        new_sum_per_day.text = sum
//    }

//    override fun enterPressed(
//        money: Double,
//        comment: String,
//        isSpending: SpDirection,
//        subcategoryId: Int?
//    ) {
//        Log.d("qwerty", money.toString())
////        presenter.saveSpend(money, comment, isSpending, subcategoryId)
//    }

//    override fun moneyUpdated(money: Double) {
//    }

//    override fun showDaysLeft(days: String) {
//        left_days.text = days
//    }
//
//    override fun showSumPerDay(money: String) {
//        sum_per_day.text = money
//    }
//
//    override fun showIncomeSum(money: String) {
//        left.text = money
//    }
//
//
//    override fun showMoney(money: String) {
////        expense.date = money
//    }

//    private fun initializeKeyboard() {
//        keyboardPart =
//            KeyboardPartFragment.newInstance()
//        keyboardPart.setListener(this)
//        childFragmentManager.beginTransaction().apply {
//            replace(R.id.keyboard, keyboardPart)
//            commit()
//        }
//    }

}

