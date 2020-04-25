package com.example.holmi_production.money_counter_app.ui.dialogs

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
import com.example.holmi_production.money_counter_app.ui.fragments.CategoryDetailFragment
import com.example.holmi_production.money_counter_app.ui.fragments.ICategoryStateListener
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.dialog_edit_category.*
import leakcanary.AppWatcher

class EditCategoryDialog : DialogFragment(),
    ICategoryStateListener,
    ICreateSubcategoryCallback {
    override fun createSubcategory(name: String) {
        val category = arguments?.getParcelable("category") as Category
        callback.addSubcategory(
            SubCategory(
                parentId = category.id,
                description = name,
                color = ColorStateList.valueOf(category.color).defaultColor
            )
        )
    }

    override fun updateStateButton(isEnable: Boolean) {
        btn_update.isEnabled = isEnable
    }

    lateinit var callback: ICategoryEditor

    lateinit var name: EditText
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog!!.setTitle("dialog title")
        return inflater.inflate(R.layout.dialog_edit_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val categoryDetail = CategoryDetailFragment.newInstance(arguments!!)
        categoryDetail.setListener(this)
        childFragmentManager.beginTransaction().apply {
            replace(R.id.container, categoryDetail)
            commit()
        }

        val subcategories = arguments?.getParcelableArray("subcategories") as Array<*>
        subcategories.forEach { subcategory ->
            chips_group.addView(buildChip(subcategory as SubCategory))
        }
        btn_update.setOnClickListener {
            callback.updateCategory(categoryDetail.getCurrentState())
            dismiss()
        }
        btn_create_subcategory.setOnClickListener {
            val dialog =
                CreateSubcategoryDialog.newInstance()
            dialog.setListener(this)
            dialog.show(childFragmentManager, "CreateSubcategoryDialog")
        }
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val displayMetrics = DisplayMetrics()
            requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
            val width = (displayMetrics.widthPixels * 0.8f).toInt()
            val height = ViewGroup.LayoutParams.WRAP_CONTENT
            dialog.window?.setLayout(width, height)
        }
    }

    fun setListener(callback: ICategoryEditor) {
        this.callback = callback
    }

    private fun buildChip(subcategory: SubCategory): Chip {
        val chip = Chip(context)
        val text = subcategory.description
        chip.text = text
        chip.chipBackgroundColor = ColorStateList.valueOf(subcategory.color)
        chip.textSize = 20f
        chip.isCloseIconVisible = true
        chip.setOnCloseIconClickListener {
            chips_group.removeView(chip)
            callback.deleteSubcategory(subcategory)
        }
        return chip
    }

    override fun onDestroy() {
        super.onDestroy()
        AppWatcher.objectWatcher.watch(this)
    }

    fun updateSubcategories(subcategories: List<SubCategory>) {
        chips_group.removeAllViews()
        subcategories.forEach {
            val chip = buildChip(it)
            chips_group.addView(chip)
        }
    }

    companion object {
        fun newInstance(args: Bundle): EditCategoryDialog {
            val fr =
                EditCategoryDialog()
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