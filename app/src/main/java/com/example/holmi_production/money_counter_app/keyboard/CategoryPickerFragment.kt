package com.example.holmi_production.money_counter_app.keyboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.CategorySpendingDirection
import com.example.holmi_production.money_counter_app.model.CategoryType
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppCompatFragment
import leakcanary.AppWatcher

class CategoryPickerFragment : AndroidXMvpAppCompatFragment() {
    companion object {
        fun newInstance(): CategoryPickerFragment {
            return CategoryPickerFragment()
        }
    }

    lateinit var callback: ICategoryPickedListener

    fun setListener(callback: ICategoryPickedListener) {
        this.callback = callback
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_category_picker, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val costsGrid = view.findViewById(R.id.costsGrid) as GridView
        val incomeGrid = view.findViewById(R.id.incomeGrid) as GridView
        val list = CategoryType.values().toList()
        costsGrid.adapter =
            CategoryDialogAdapter(context!!, list.filter { it.spendingDirection == CategorySpendingDirection.SPENDING })
        incomeGrid.adapter =
            CategoryDialogAdapter(
                context!!,
                list.filter { it.spendingDirection == CategorySpendingDirection.INCOME || it.spendingDirection == CategorySpendingDirection.BOTH })
    }

    override fun onDestroy() {
        super.onDestroy()
        AppWatcher.objectWatcher.watch(this)
    }
}

interface ICategoryPickedListener {
    fun categoryPicked(type: CategoryType)
}