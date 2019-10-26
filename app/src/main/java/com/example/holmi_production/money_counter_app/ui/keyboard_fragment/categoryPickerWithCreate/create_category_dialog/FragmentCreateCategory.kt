package com.example.holmi_production.money_counter_app.ui.keyboard_fragment.categoryPickerWithCreate.create_category_dialog

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.SpDirection
import com.example.holmi_production.money_counter_app.utils.ColorUtils
import kotlinx.android.synthetic.main.dialog_create_category.*
import leakcanary.AppWatcher

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
            val background = v_color_container.background
            val color:ColorDrawable? = background as? ColorDrawable
            callback!!.categoryCreated(et_category_name.text.toString(), listOf(), color)
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

    override fun onDestroy() {
        super.onDestroy()
        AppWatcher.objectWatcher.watch(this)
    }

    override fun onPause() {
        super.onPause()
        Log.d("M_FragmentCategory","Paused")
    }

    override fun onStop() {
        super.onStop()
        Log.d("M_FragmentCategory","stopped")
    }
}

interface ICategoryCreateCallback {
    fun categoryCreated(categoryName: String, categoryType: List<SpDirection>, color: ColorDrawable?)
}