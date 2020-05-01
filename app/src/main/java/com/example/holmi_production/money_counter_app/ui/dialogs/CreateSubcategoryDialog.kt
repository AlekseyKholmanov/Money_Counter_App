package com.example.holmi_production.money_counter_app.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.holmi_production.money_counter_app.R

class CreateSubcategoryDialog(
    private val createSubcategoryCallback: (String) -> Unit = {}
) : DialogFragment() {

    lateinit var et: EditText

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialog_create_subcategory, null)
        et = view.findViewById(R.id.et_subcategory_name)
        val dialog = AlertDialog.Builder(context)
        dialog.setTitle("Добавление подкатегории")
        dialog.setPositiveButton("Создать") { _, _ ->
            createSubcategoryCallback(et.text.toString())
            dismiss()
        }
        dialog.setNegativeButton("Отмена") { _, _ ->
            dismiss()
        }
        dialog.setView(view)
        return dialog.create()
    }
}