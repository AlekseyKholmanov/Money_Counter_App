package com.example.holmi_production.money_counter_app.ui.keyboard_fragment.categoryPickerWithCreate.create_category_dialog

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.custom.ColorSeekBar
import com.example.holmi_production.money_counter_app.extensions.hideKeyboardFrom
import com.example.holmi_production.money_counter_app.model.SpDirection
import com.example.holmi_production.money_counter_app.model.entity.Category
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppCompatFragment
import com.example.holmi_production.money_counter_app.utils.ColorUtils
import kotlinx.android.synthetic.main.container_category_detail.*
import kotlin.random.Random

class CategoryDetailFragment private constructor() : AndroidXMvpAppCompatFragment(), IImagePicker {
    override fun imagePicked(resId: Int) {
        iv_category_image.setImageResource(resId)
        iv_category_image.tag = resId
        iv_category_image.invalidate()
        childFragmentManager.findFragmentByTag(CategoryCreateFragment.IMAGE_DIALOG_TAG)?.let {
            (it as DialogFragment).dismiss()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.container_category_detail, container, false)
    }

    private lateinit var callback: ICategoryStateListener

    fun setListener(callback: ICategoryStateListener) {
        this.callback = callback
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val category = arguments?.getParcelable("category") as Category?

        if (category != null) {
            category.color?.let { iv_category_image.setBackgroundColor(it) }
            category.imageId?.let { iv_category_image.setImageResource(it) }
            et_category_name.setText(category.description)
            iv_category_image.tag = category.imageId
            for (i in category.spendingDirection) {
                if (i == SpDirection.INCOME)
                    ch_income.isChecked = true
                if (i == SpDirection.SPENDING)
                    ch_spending.isChecked = true
                if (i == SpDirection.ACCUMULATION)
                    ch_accumulation.isChecked = true
            }
        }
        color_seek_bar.setOnColorChangeListener(object : ColorSeekBar.OnColorChangeListener {
            override fun onColorChangeListener(color: Int) {
                //gives the selected color
                iv_category_image.setBackgroundColor(color)
            }
        })

        iv_category_image.setOnClickListener {
            val dialog = ImageCategoryPicker.newInstance()
            dialog.setListener(this)
            dialog.show(childFragmentManager, CategoryCreateFragment.IMAGE_DIALOG_TAG)
        }

        et_category_name.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (count == 0)
                    callback.updateStateButton(false)
                else
                    callback.updateStateButton(isCheckboxesChecked())
            }
        })

        ch_accumulation.setOnClickListener {
            ch_accumulation.isChecked = !ch_accumulation.isChecked
            callback.updateStateButton(isCheckboxesChecked())
        }
        ch_income.setOnClickListener {
            ch_income.isChecked = !ch_income.isChecked
            callback.updateStateButton(isCheckboxesChecked())
        }
        ch_spending.setOnClickListener {
            ch_spending.isChecked = !ch_spending.isChecked
            callback.updateStateButton(isCheckboxesChecked())
        }
        btn_generate_color.setOnClickListener {
            val rand = Random.nextInt(0, color_seek_bar.width)
            color_seek_bar.setColor(rand.toFloat())

//            val color = ColorUtils.getColor()
//            v_color_container.setBackgroundColor(color)
//            iv_category_image.setBackgroundColor(color)
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

    fun getCurrentState(): Category {
        val background = iv_category_image.background
        val color: ColorDrawable? = background as? ColorDrawable
        val imageId = iv_category_image.tag as Int?
        val category = (arguments?.getParcelable("category") as Category?)
        val categoryId = category?.id
        Log.d("M_CatDetailFragment", "dsada")
        return Category(
            id = categoryId,
            imageId = imageId ?: R.drawable.ic_launcher_foreground,
            color = color?.color ?: ColorUtils.getColor(),
            description = et_category_name.text.toString(),
            isDelete = false,
            spendingDirection = getDirections()
        )
    }

    fun isCheckboxesChecked(): Boolean {
        return (ch_income.isChecked or ch_accumulation.isChecked or ch_spending.isChecked) and et_category_name.text.isNotBlank()
    }

    private fun getDirections(): List<SpDirection> {
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
        fun newInstance(): CategoryDetailFragment {
            val fr = CategoryDetailFragment()
            fr.arguments = null
            return fr
        }

        fun newInstance(bundle: Bundle): CategoryDetailFragment {
            val fr = CategoryDetailFragment()
            fr.arguments = bundle
            return fr
        }
    }
}

interface ICategoryStateListener {
    fun updateStateButton(isEnable: Boolean)
}