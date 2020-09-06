package com.example.holmi_production.money_counter_app.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.main.BaseFragment
import com.example.holmi_production.money_counter_app.model.CategoryDetails
import com.example.holmi_production.money_counter_app.model.uiModels.DashboardAccountDetails
import com.example.holmi_production.money_counter_app.ui.adapter.delegate.dashboardTransactionDelegate
import com.example.holmi_production.money_counter_app.ui.adapter.diffUtil.DashboardGroupDiffUtilCallback
import com.example.holmi_production.money_counter_app.ui.adapter.holder.Callback
import com.example.holmi_production.money_counter_app.ui.adapter.items.TransactionGroupItem
import com.example.holmi_production.money_counter_app.ui.dialogs.BottomDialog
import com.example.holmi_production.money_counter_app.ui.viewModels.DashboardViewModel
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import kotlinx.android.synthetic.main.fragment_dashboard.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment : BaseFragment(R.layout.fragment_dashboard) {

    private val viewModel: DashboardViewModel by viewModel()

    private val accountsCallback = object : Callback {
        override fun minusClicked(accountId: String) {
            val directions =
                DashboardFragmentDirections.actionDashboardFragmentToSimpleBottomKeyboard(
                    accountId = accountId,
                    isSubstraction = true
                )
            findNavController().navigate(directions)
        }

        override fun plusClicked(accountId: String) {
            val directions =
                DashboardFragmentDirections.actionDashboardFragmentToSimpleBottomKeyboard(
                    accountId = accountId,
                    isSubstraction = false
                )
            findNavController().navigate(directions)
        }

    }
    private val transactionAdapter by lazy {
        AsyncListDifferDelegationAdapter(
            DashboardGroupDiffUtilCallback(),
            dashboardTransactionDelegate()
        )
    }

    private val viewPagerCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(viewModel) {
            accountDetails.observe(viewLifecycleOwner, Observer(::updateAccounts))
        }
//        adapter.registerAdapterDataObserver(indicator.adapterDataObserver)
//        accounts.adapter = adapter
//        indicator.setViewPager(accounts)
//
        accountExpenses.setTitleRes(R.string.expenses)
        accountIncome.setTitleRes(R.string.income)
        accountBalance.setTitleRes(R.string.balance)

        accountName.setOnClickListener {
            findNavController().navigate(
                R.id.action_global_bottomDialog,
                bundleOf(
                    BottomDialog.ARGS_DIALOG_TYPE to BottomDialog.TYPE_ACCOUNT_SELECTION,
                    BottomDialog.ARGS_SELECTED_ID_STRING to viewModel.accountDetails.value?.account?.id
                )
            )
        }
        setResultListener()
        accountItems.adapter = transactionAdapter

//
//        addAccount.setOnClickListener {
//            val destinatination =
//                DashboardFragmentDirections.actionDashboardFragmentToCreateAccountFragment2()
//            findNavController().navigate(destinatination)
//        }
//        accounts.registerOnPageChangeCallback(viewPagerCallback)

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

    private fun setResultListener() {
        setFragmentResultListener(BottomDialog.REQUEST_FROM_ACCOUNT_SELECTION) { _, bundle ->
            val selectedAccountId = bundle.getString(BottomDialog.TYPE_ACCOUNT_SELECTION)
            selectedAccountId?.let { viewModel.updateAccountId(it) }
        }
    }

    private fun showFullBottomKeyboard(selectedId: String? = null) {
//        val accountId = selectedId ?: (accounts.adapter as AccountAdapter).items[accounts.currentItem].id
//        val direction =
//            DashboardFragmentDirections.actionKeyboardFragmentToBottomKeyboard(accountId)
//        findNavController().navigate(direction)
    }

    private fun updateAccounts(details: DashboardAccountDetails) {
        accountName.text = details.account.description
        val divided = details.transactions.partition { it.sum > 0 }
        accountExpenses.setAmount(divided.second.sumByDouble { it.sum }.toString())
        accountIncome.setAmount(divided.first.sumByDouble { it.sum }.toString())
        accountBalance.setAmount(details.transactions.sumByDouble { it.sum }.toString())
        transactionAdapter.items = details.transactions.groupBy { it.createdDate.withTimeAtStartOfDay() }
            .map { TransactionGroupItem(it.key, it.value) }
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

