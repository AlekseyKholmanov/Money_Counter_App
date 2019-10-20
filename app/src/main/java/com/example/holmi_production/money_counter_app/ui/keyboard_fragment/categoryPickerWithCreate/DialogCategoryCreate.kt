package com.example.holmi_production.money_counter_app.ui.keyboard_fragment.categoryPickerWithCreate

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckedTextView
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.SpDirection
import com.example.holmi_production.money_counter_app.utils.ColorUtils

class DialogCategoryCreate : AppCompatDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context!!)
        val view = activity!!.layoutInflater.inflate(R.layout.dialog_create_category, null)
        builder.setView(view)
        builder.setTitle("Новая категория")
        chAccess = view.findViewById(R.id.ch_accumulation)
        chIncome = view.findViewById(R.id.ch_income)
        chSpending = view.findViewById(R.id.ch_spending)
        saveButton = view.findViewById(R.id.btn_create_category)
        etCategoryname = view.findViewById(R.id.et_category_name)
        generateColorBtn = view.findViewById(R.id.btn_generate_color)
        colorContainer = view.findViewById(R.id.v_color_container)
        chAccess.setOnClickListener {
            chAccess.isChecked = !chAccess.isChecked
            saveButton.isEnabled = isCheckboxesChecked()
        }
        chIncome.setOnClickListener {
            chIncome.isChecked = !chIncome.isChecked
            saveButton.isEnabled = isCheckboxesChecked()
        }
        chSpending.setOnClickListener {
            chSpending.isChecked = !chSpending.isChecked
            saveButton.isEnabled = isCheckboxesChecked()
        }
        generateColorBtn.setOnClickListener {
            val color = ColorUtils.getColor()
            colorContainer.setBackgroundColor(color)
        }
        saveButton.setOnClickListener {
            val color = colorContainer.background
            callback!!.categoryCreated(etCategoryname.text.toString(), listOf(), (color as ColorDrawable).color, false)
            callback = null
            dismiss()
        }
        return builder.create()

    }

    fun setCallback(callback: ICategoryCreateCallback) {
        this.callback = callback
    }

    private fun isCheckboxesChecked(): Boolean {
        return (chIncome.isChecked or chAccess.isChecked or chSpending.isChecked) and etCategoryname.text.isNotBlank()
    }

    private var callback: ICategoryCreateCallback? = null
    private lateinit var chAccess: CheckedTextView
    private lateinit var chIncome: CheckedTextView
    private lateinit var chSpending: CheckedTextView
    private lateinit var etCategoryname: EditText
    private lateinit var generateColorBtn:Button
    private lateinit var colorContainer:View
    private lateinit var saveButton: Button

    companion object {
        fun newInstance(): DialogCategoryCreate {
            return DialogCategoryCreate()
        }
    }
}

interface ICategoryCreateCallback {
    fun categoryCreated(categoryName: String, categoryType: List<SpDirection>, color:Int?, isSubCategory: Boolean)
}