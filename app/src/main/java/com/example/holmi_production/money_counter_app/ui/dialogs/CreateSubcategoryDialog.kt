package com.example.holmi_production.money_counter_app.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.extensions.hideKeyboardInDialog
import com.example.holmi_production.money_counter_app.extensions.showKeyboardInDialog

class CreateSubcategoryDialog(
    private val createSubcategoryCallback: (String) -> Unit = {}
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialog_create_subcategory, null)
        val builder = AlertDialog.Builder(context)
        val editText = view.findViewById<EditText>(R.id.subcategoryEditText)
        requireActivity().showKeyboardInDialog(editText)
        builder.setTitle("Добавление подкатегории")
        builder.setPositiveButton("Создать") { _, _ ->
            createSubcategoryCallback(editText.text.toString())
            requireActivity().hideKeyboardInDialog()
            dismiss()
        }
        builder.setNegativeButton("Отмена") { _, _ ->
            requireActivity().hideKeyboardInDialog()
            dismiss()
        }
        builder.setView(view)
        return builder.create()
    }
}