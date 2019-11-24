package com.example.holmi_production.money_counter_app.ui.keyboard_fragment.categoryPickerWithCreate.edit_category_dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckedTextView
import android.widget.EditText
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.SpDirection
import com.example.holmi_production.money_counter_app.model.SquareImageView
import com.example.holmi_production.money_counter_app.model.entity.Category
import com.example.holmi_production.money_counter_app.model.entity.SubCategory
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.android.synthetic.main.chart_pie.*
import kotlinx.android.synthetic.main.container_category_detail.*

class EditCategoryDialog private constructor() : DialogFragment() {

    lateinit var callback: ICategoryEditor

    lateinit var image:SquareImageView
    lateinit var name:EditText
    lateinit var ch_sp:CheckedTextView
    lateinit var ch_in:CheckedTextView
    lateinit var ch_ac:CheckedTextView
    lateinit var chips:ChipGroup

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = activity!!.layoutInflater
        val view = inflater.inflate(R.layout.edit_category_lightbox, null)
        val dialog = AlertDialog.Builder(context)
        val category = arguments?.getParcelable("category") as Category
        val subcategories = arguments?.getParcelableArray("subcategories") as Array<SubCategory>
        dialog.setTitle(category.description)
        image = view.findViewById(R.id.iv_category_image)
        name = view.findViewById(R.id.et_category_name)
        ch_sp = view.findViewById(R.id.ch_spending)
        ch_in = view.findViewById(R.id.ch_income)
        ch_ac = view.findViewById(R.id.ch_accumulation)
        chips = view.findViewById(R.id.chips_group)

        category.color?.let { image.setBackgroundColor(it) }
        category.imageId?.let { image.setImageResource(it) }
        name.setText(category.description)

        for (i in category.spendingDirection){
            if( i == SpDirection.INCOME)
                ch_in.isChecked = true
            if (i == SpDirection.SPENDING)
                ch_sp.isChecked = true
            if (i == SpDirection.ACCUMULATION)
                ch_ac.isChecked = true
        }
        if(subcategories.isEmpty()) chips.visibility = View.GONE else chips.visibility = View.VISIBLE

        subcategories.forEach { subcategory ->
            chips.addView(buildChip(subcategory, category.color!!))
        }

        dialog.setView(view)
        return dialog.create()
    }

    fun setListener(callback: ICategoryEditor) {
        this.callback = callback
    }

    private fun buildChip(subcategory: SubCategory,color:Int): Chip {
        val chip = Chip(context)
        val text = subcategory.description
        chip.text = text
        chip.chipBackgroundColor= ColorStateList.valueOf(color)
        chip.textSize = 20f
        chip.isCloseIconVisible = true
        return chip
    }

    companion object {
        fun newInstance(args: Bundle): EditCategoryDialog {
            val fr = EditCategoryDialog()
            fr.arguments = args
            return fr
        }
    }

    interface ICategoryEditor {
        fun addSubcategory(subcategory: SubCategory)
        fun deleteSubcategory(subcategory: SubCategory)
        fun updateCategory(category: Category)
    }
}