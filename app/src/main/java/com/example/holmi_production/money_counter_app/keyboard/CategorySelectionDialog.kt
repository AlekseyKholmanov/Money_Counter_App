package com.example.holmi_production.money_counter_app.keyboard

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.GridView
import android.widget.GridView.AUTO_FIT
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.CategoryType
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppCompatFragment
import kotlinx.android.synthetic.main.dialog_category_picker.*

class CategoryAlertDialog : DialogFragment() {
    companion object {
        fun newInstance(): CategoryAlertDialog {
            return CategoryAlertDialog()
        }
    }

    lateinit var callback: ICategoryPickedListener

    fun setListener(callback: ICategoryPickedListener) {
        this.callback = callback
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_category_picker, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gridView = view.findViewById(R.id.gridView) as GridView
        gridView.adapter = CategoryDialogAdapter(context!!,callback)
    }
}

interface ICategoryPickedListener {
    fun categoryPicked(type: CategoryType)
}