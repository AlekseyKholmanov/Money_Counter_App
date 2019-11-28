package com.example.holmi_production.money_counter_app.ui.keyboard_fragment.categoryPickerWithCreate.edit_category_dialog

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.entity.Category
import com.example.holmi_production.money_counter_app.model.entity.SubCategory
import com.example.holmi_production.money_counter_app.ui.keyboard_fragment.categoryPickerWithCreate.create_category_dialog.CategoryDetailFragment
import com.example.holmi_production.money_counter_app.ui.keyboard_fragment.categoryPickerWithCreate.create_category_dialog.ICategoryStateListener
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.dialog_edit_category.*
import leakcanary.AppWatcher

class EditCategoryDialog private constructor() : DialogFragment(), ICategoryStateListener,ICreateSubcategoryCallback {
    override fun createSubcategory(name: String) {
        val category = arguments?.getParcelable("category") as Category
        val patentID = category.id!!
        val subCategory = SubCategory(parentId = patentID, description = name)
        callback.addSubcategory(subCategory)
        val chip = buildChip(subCategory, category.color!!)
        chips_group.addView(chip)
    }

    override fun updateStateButton(isEnable: Boolean) {
        btn_update.isEnabled = isEnable
    }

    lateinit var callback: ICategoryEditor

    lateinit var name:EditText
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        dialog!!.setTitle("dialog title")
        return inflater.inflate(R.layout.dialog_edit_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val categoryDetail = CategoryDetailFragment.newInstance(arguments!!)
        categoryDetail.setListener(this)
        childFragmentManager.beginTransaction().apply {
            add(R.id.container, categoryDetail)
            commit()
        }

        val category = arguments?.getParcelable("category") as Category
        val subcategories = arguments?.getParcelableArray("subcategories") as Array<SubCategory>
        if(subcategories.isEmpty()) chips_group.visibility = View.GONE else chips_group.visibility = View.VISIBLE

        subcategories.forEach { subcategory ->
            chips_group.addView(buildChip(subcategory, category.color!!))
        }
        btn_update.isEnabled = false
        btn_update.setOnClickListener {
            callback.updateCategory( categoryDetail.getCurrentState())
            dismiss()
        }
        btn_create_subcategory.setOnClickListener {
            val dialog = CreateSubcategoryDialog.newInstance()
            dialog.setListener(this)
            dialog.show(childFragmentManager, "CreateSubcategoryDialog")
        }
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val displayMetrics = DisplayMetrics()
            activity!!.windowManager.defaultDisplay.getMetrics(displayMetrics)
            val width = (displayMetrics.widthPixels * 0.8f).toInt()
            val height = ViewGroup.LayoutParams.MATCH_PARENT

            dialog.window!!.setLayout(width, height)
        }
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
        chip.setOnCloseIconClickListener {
            chips_group.removeView(chip)
            callback.deleteSubcategory( subcategory)
        }
        return chip
    }

    override fun onDestroy() {
        super.onDestroy()
        AppWatcher.objectWatcher.watch(this)
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