package com.example.holmi_production.money_counter_app.keyboard

import android.os.Bundle
import android.util.Log
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

    private var callback: ICategoryPickedListener? = null

    fun setListener(callback: ICategoryPickedListener) {
        this.callback = callback
    }

    fun deleteListener(){
        callback = null
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

interface ICategoryPickedListener {
    fun categoryPicked(type: CategoryType)
}