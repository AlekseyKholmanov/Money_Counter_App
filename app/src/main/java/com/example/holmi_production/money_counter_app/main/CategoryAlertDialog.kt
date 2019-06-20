package com.example.holmi_production.money_counter_app.main

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.Expense

class CategoryAlertDialog : DialogFragment() {
    companion object {
        @JvmStatic
        fun newInstance(): CategoryAlertDialog {
            return CategoryAlertDialog()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val layout = GridLayout(context)
        val parms = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layout.layoutParams = parms
        layout.columnCount = 3
        val inflater = View.inflate(context, R.layout.dialog_layout, null)
        val container = inflater.findViewById<GridLayout>(R.id.dialog_root)
        for (i in 0 until Expense.values().count()) {
            val categoryItem = CategoryItem(Expense.values()[i].description, context!!)
            layout.addView(categoryItem)
        }

        return AlertDialog.Builder(requireContext())
            .setIcon(R.drawable.ic_launcher_background)
            .setTitle("Blah")
            .setView(layout)
            .create()
    }
}