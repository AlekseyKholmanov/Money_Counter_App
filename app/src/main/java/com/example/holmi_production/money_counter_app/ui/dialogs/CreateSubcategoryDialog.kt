package com.example.holmi_production.money_counter_app.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.example.holmi_production.money_counter_app.R
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import reactivecircus.flowbinding.android.widget.textChanges

class CreateSubcategoryDialog(
    private val createSubcategoryCallback: (String) -> Unit = {}
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialog_create_subcategory, null)
        val builder = AlertDialog.Builder(context)
        val editText = view.findViewById<EditText>(R.id.subcategoryEditText)
        builder.setTitle("Добавление подкатегории")
        builder.setPositiveButton("Создать") { _, _ ->
            createSubcategoryCallback(editText.text.toString())
            dismiss()
        }
        builder.setNegativeButton("Отмена") { _, _ ->
            dismiss()
        }
        builder.setView(view)
        return builder.create()
    }
}