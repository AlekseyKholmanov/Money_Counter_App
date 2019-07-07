package com.example.holmi_production.money_counter_app.keyboard

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
        val params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        gridLayout.layoutParams = params
        gridLayout.columnCount = 3
        for (i in 0 until CategoryType.values().count()) {
            val type = CategoryType.values()[i]
            val categoryItem =
                CategoryItem(type.color, type.description, getImage(type), context!!)
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

    private fun getImage(categoryType: CategoryType): Int {
        return when (categoryType) {
            CategoryType.SALARY -> R.drawable.icon_salary
            CategoryType.HOME -> R.drawable.icon_home
            CategoryType.ENTERTAINMENT -> R.drawable.icon_glass
            CategoryType.FOOD -> R.drawable.icon_food
            CategoryType.TRANSPORT -> R.drawable.icon_bus
            CategoryType.WEAR -> R.drawable.icon_clothes_2
            CategoryType.NET -> R.drawable.icon_network
            CategoryType.BAR -> R.drawable.icon_bar
            CategoryType.OTHER -> R.drawable.icon_other
        }
    }
}

interface ICategoryPickedListener {
    fun categoryPicked(type: CategoryType)
}