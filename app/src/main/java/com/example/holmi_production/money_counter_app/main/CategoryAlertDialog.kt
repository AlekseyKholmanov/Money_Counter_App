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
        @JvmStatic
        fun newInstance(): CategoryAlertDialog {
            return CategoryAlertDialog()
        }
    }

    lateinit var categoryChosed: ICategoryChosed
    fun setListener(categoryChosed: ICategoryChosed) {
        this.categoryChosed = categoryChosed
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val gridLayout = GridLayout(context)
        val params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        gridLayout.layoutParams = params
        gridLayout.columnCount = 3
        for (i in 0 until CategoryType.values().count()) {
            val categoryItem = CategoryItem(CategoryType.values()[i].description, context!!)
            categoryItem.setOnClickListener {
                categoryChosed.chosed(CategoryType.values()[i])
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

interface ICategoryChosed {
    fun chosed(type: CategoryType)
}