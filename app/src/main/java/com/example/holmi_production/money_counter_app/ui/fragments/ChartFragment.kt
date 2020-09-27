package com.example.holmi_production.money_counter_app.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.main.BaseFragment
import com.example.holmi_production.money_counter_app.model.RecyclerItem
import com.example.holmi_production.money_counter_app.ui.adapter.chartCategoriesHeaderDelegate
import com.example.holmi_production.money_counter_app.ui.adapter.chartCategoryDetailsAdapterDelegate
import com.example.holmi_production.money_counter_app.ui.adapter.decorators.ListDelegationDecorator
import com.example.holmi_production.money_counter_app.ui.adapter.items.CharCategoryItem
import com.example.holmi_production.money_counter_app.ui.adapter.items.ChartCategoryHeaderItem
import com.example.holmi_production.money_counter_app.ui.adapter.top5categoriesAdapterDelegate
import com.example.holmi_production.money_counter_app.ui.custom.CharItem
import com.example.holmi_production.money_counter_app.ui.utils.Rotate3dAnimation
import com.example.holmi_production.money_counter_app.ui.viewModels.ChartViewModel
import com.github.mikephil.charting.data.BarEntry
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import kotlinx.android.synthetic.main.fragment_trasnsactions.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class ChartFragment : BaseFragment(R.layout.fragment_trasnsactions) {

    private val viewModel: ChartViewModel by viewModel()

    private val top5Adapter by lazy(LazyThreadSafetyMode.NONE) {
        val manager = AdapterDelegatesManager<List<RecyclerItem>>().apply {
            addDelegate(CharCategoryItem.shortType, top5categoriesAdapterDelegate())
        }
        ListDelegationAdapter(manager)
    }

    private val detailsAdapter by lazy(LazyThreadSafetyMode.NONE) {
        val manager = AdapterDelegatesManager<List<RecyclerItem>>().apply {
            addDelegate(CharCategoryItem.detailsType, chartCategoryDetailsAdapterDelegate())
            addDelegate(ChartCategoryHeaderItem.VIEW_TYPE, chartCategoriesHeaderDelegate())
        }
        ListDelegationAdapter(manager)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(viewModel) {
            observeTransaction()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.transactions.observe(viewLifecycleOwner, Observer(::updateTransaction))
        percentList.adapter = top5Adapter
        detailsList.adapter = detailsAdapter
        detailsList.addItemDecoration(ListDelegationDecorator(requireContext()))
//        swap.setOnClickListener {
//            val ddAnim = Rotate3dAnimation(
//                mFromDegrees = 0f,
//                mToDegrees = 180f,
//                mCenterX = swap.width / 2f,
//                mCenterY = swap.height / 2f,
//                mDepthZ = 0f,
//                mReverse = false
//            )
//
//            ddAnim.setAnimationListener(object : Animation.AnimationListener {
//                override fun onAnimationStart(animation: Animation?) {
//                }
//
//                override fun onAnimationEnd(animation: Animation?) {
//                    swap.text = if (viewModel.showExpense) "Expense" else "Income"
//                }
//
//                override fun onAnimationRepeat(animation: Animation?) {
//                }
//
//            })
//            val animation = RotateAnimation(
//                0f, 360f,
//                swap.width / 2f, swap.height / 2f
//            )
//            ddAnim.duration = 1000L
//            swap.animation = ddAnim
//            ddAnim.start()
//            viewModel.swapValues()
//        }
    }

    private fun updateTransaction(transaction: List<CharCategoryItem>) {
        Log.d("M_ChartFragment", "${transaction.size}")
        val size = transaction.size
        val items = mutableListOf<CharCategoryItem>()
        val top4Transaction = transaction.take(4)
        items.addAll(top4Transaction)
        if (size > 4) {
            val lastItems = transaction.drop(4)
            items.add(
                CharCategoryItem(
                    categoryName = "Other",
                    sum = lastItems.sumByDouble { it.sum },
                    percentage = lastItems.sumByDouble { it.percentage },
                    color = Color.RED,
                    categoryImage = null
                )
            )
        }
        val charItems = items.map {
            CharItem(
                angle = (it.percentage * 360 / 100).toFloat(),
                color = it.color ?: ContextCompat.getColor(requireContext(), R.color.colorAccent)
            )
        }
        top5Adapter.items = items
        val detailsItem = mutableListOf<RecyclerItem>()
        detailsItem.add(
            ChartCategoryHeaderItem(spendingType = if (viewModel.showExpense) "Expenses" else "Spending",
                sum = transaction.sumByDouble { it.sum })
        )
        detailsItem.addAll(transaction)
        chart.updateIndicator(charItems)
        detailsAdapter.items = detailsItem
        top5Adapter.notifyDataSetChanged()
        detailsAdapter.notifyDataSetChanged()
    }

    fun prepareChartItems(transaction: List<CharCategoryItem>): List<BarEntry> {
        return emptyList()
    }

}