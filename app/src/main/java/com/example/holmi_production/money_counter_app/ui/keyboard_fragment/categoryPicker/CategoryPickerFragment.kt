package com.example.holmi_production.money_counter_app.ui.keyboard_fragment.categoryPicker

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.SpDirection
import com.example.holmi_production.money_counter_app.model.CategoryType
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppCompatFragment
import leakcanary.AppWatcher
import kotlin.math.cos

class CategoryPickerFragment : AndroidXMvpAppCompatFragment() {
    companion object {
        fun newInstance(): CategoryPickerFragment {
            return CategoryPickerFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_category_picker, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val costsGrid = view.findViewById(R.id.costsGrid) as RecyclerView
        val incomeGrid = view.findViewById(R.id.incomeGrid) as RecyclerView
        val list = CategoryType.values().toList()
        val manager = GridLayoutManager(context,3)
        val manager2 = GridLayoutManager(context,3)
        costsGrid.layoutManager = manager
        incomeGrid.layoutManager = manager2
        costsGrid.adapter =CategoryDialogAdapter(list.filter { it.spendingDirection == SpDirection.SPENDING }.toMutableList())
        incomeGrid.adapter = CategoryDialogAdapter(list.filter { it.spendingDirection == SpDirection.INCOME
                        || it.spendingDirection == SpDirection.BOTH }.toMutableList())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("M_CategoryPicker","OnCreate")
    }

    override fun onPause() {
        super.onPause()
        Log.d("M_CategoryPicker","onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("M_CategoryPicker","onStop")
    }

    override fun onStart() {
        super.onStart()
        Log.d("M_CategoryPicker","onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("M_CategoryPicker","onResume")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("M_CategoryPicker", "onDestroy")
        AppWatcher.objectWatcher.watch(this)
    }
}