package com.example.holmi_production.money_counter_app.main

import android.app.Dialog
import android.os.Bundle
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.CategoryType

class CategoryAlertDialog : DialogFragment() {
    companion object {
        fun newInstance(): CategoryAlertDialog {
            return CategoryAlertDialog()
        }
    }

    lateinit var callback: ICategoryPickedListener

    var selectedType = CategoryType.OTHER

    fun setListener(callback: ICategoryPickedListener) {
        this.callback = callback
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val gridLayout = GridLayout(context)
        val params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        gridLayout.layoutParams = params
        gridLayout.columnCount = 3
        for (i in 0 until CategoryType.values().count() - 1) {
            val categoryItem = CategoryItem(CategoryType.values()[i], context!!)
            categoryItem.setOnClickListener {
                selectedType = CategoryType.values()[i]
                callback.categoryPicked(selectedType)
                this.dismiss()
            }
            gridLayout.addView(categoryItem)
        }

        return AlertDialog.Builder(requireContext())
            .setIcon(R.drawable.ic_launcher_background)
            .setView(gridLayout)
            .create()
    }
}

interface ICategoryPickedListener {
    fun categoryPicked(type: CategoryType)
}