package com.example.holmi_production.money_counter_app.ui.keyboard_fragment.categoryPickerWithCreate.create_category_dialog

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.SpDirection
import com.example.holmi_production.money_counter_app.utils.ColorUtils
import kotlinx.android.synthetic.main.dialog_create_category.*

class CategoryCreateFragment: Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_create_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ch_accumulation.setOnClickListener {
            ch_accumulation.isChecked = !ch_accumulation.isChecked
            btn_create_category.isEnabled = isCheckboxesChecked()
        }
        ch_income.setOnClickListener {
            ch_income.isChecked = !ch_income.isChecked
            btn_create_category.isEnabled = isCheckboxesChecked()
        }
        ch_spending.setOnClickListener {
            ch_spending.isChecked = !ch_spending.isChecked
            btn_create_category.isEnabled = isCheckboxesChecked()
        }
        btn_generate_color.setOnClickListener {
            val color = ColorUtils.getColor()
            v_color_container.setBackgroundColor(color)
        }
        btn_create_category.setOnClickListener {
            val color = v_color_container.background
            callback!!.categoryCreated(et_category_name.text.toString(), listOf(), (color as ColorDrawable).color, false)
            callback = null
        }
    }

    fun setCallback(callback: ICategoryCreateCallback) {
        this.callback = callback
    }

    private fun isCheckboxesChecked(): Boolean {
        return (ch_income.isChecked or ch_accumulation.isChecked or ch_spending.isChecked) and et_category_name.text.isNotBlank()
    }

    private var callback: ICategoryCreateCallback? = null

    companion object {
        fun newInstance(): CategoryCreateFragment {
            return CategoryCreateFragment()
        }
    }
}

interface ICategoryCreateCallback {
    fun categoryCreated(categoryName: String, categoryType: List<SpDirection>, color:Int?, isSubCategory: Boolean)
}