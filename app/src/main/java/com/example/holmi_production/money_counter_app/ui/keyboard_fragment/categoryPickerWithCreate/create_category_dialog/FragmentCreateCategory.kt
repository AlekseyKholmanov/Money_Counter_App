package com.example.holmi_production.money_counter_app.ui.keyboard_fragment.categoryPickerWithCreate.create_category_dialog

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.extensions.hideKeyboardFrom
import com.example.holmi_production.money_counter_app.model.SpDirection
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppCompatFragment
import com.example.holmi_production.money_counter_app.utils.ColorUtils
import kotlinx.android.synthetic.main.dialog_create_category.*
import leakcanary.AppWatcher

class CategoryCreateFragment : AndroidXMvpAppCompatFragment(), IImagePicker {
    override fun imagePicked(resId: Int) {
        iv_category_image.setImageResource(resId)
        iv_category_image.tag = resId
        iv_category_image.invalidate()
        childFragmentManager.findFragmentByTag(IMAGE_DIALOG_TAG)?.let {
            (it as DialogFragment).dismiss()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_create_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        iv_category_image.setOnClickListener {
            val dialog = ImageCategoryPicker.newInstance()
            dialog.setListener(this)
            dialog.show(childFragmentManager, IMAGE_DIALOG_TAG)
        }

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
            iv_category_image.setBackgroundColor(color)
        }
        btn_create_category.setOnClickListener {
            val background = v_color_container.background
            val color: ColorDrawable? = background as? ColorDrawable
            val imageId = iv_category_image.tag as Int?

            callback!!.categoryCreated(
                et_category_name.text.toString(),
                getDirections(),
                color,
                imageId ?: R.drawable.ic_launcher_foreground
            )
        }
        et_category_name.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                et_category_name?.hideKeyboardFrom(context!!)
                handled = true
            }
            return@OnEditorActionListener handled
        })
        et_category_name.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus)
                et_category_name?.hideKeyboardFrom(context!!)
        }
    }

    fun setCallback(callback: ICategoryCreateCallback) {
        this.callback = callback
    }

    override fun onDestroy() {
        super.onDestroy()
        AppWatcher.objectWatcher.watch(this)
    }

    override fun onPause() {
        super.onPause()
        Log.d("M_FragmentCategory", "Paused")
    }

    override fun onStop() {
        super.onStop()
        Log.d("M_FragmentCategory", "stopped")
    }

    private var callback: ICategoryCreateCallback? = null

    private fun isCheckboxesChecked(): Boolean {
        return (ch_income.isChecked or ch_accumulation.isChecked or ch_spending.isChecked) and et_category_name.text.isNotBlank()
    }

    private fun getDirections(): Collection<SpDirection> {
        val directions = mutableListOf<SpDirection>()
        if (ch_income.isChecked)
            directions.add(SpDirection.INCOME)
        if (ch_accumulation.isChecked)
            directions.add(SpDirection.ACCUMULATION)
        if (ch_spending.isChecked)
            directions.add(SpDirection.SPENDING)
        return directions
    }

    companion object {
        val IMAGE_DIALOG_TAG = "IMAGE_DIALOG_TAG"
        fun newInstance(): CategoryCreateFragment {
            return CategoryCreateFragment()
        }
    }
}

interface ICategoryCreateCallback {
    fun categoryCreated(
        categoryName: String,
        categoryTypes: Collection<SpDirection>,
        color: ColorDrawable?,
        imageId: Int)
}