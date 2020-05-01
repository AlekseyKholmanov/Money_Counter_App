package com.example.holmi_production.money_counter_app.ui.fragments

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.example.holmi_production.money_counter_app.App
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.Vibrator
import com.example.holmi_production.money_counter_app.extensions.hideKeyboardFrom
import com.example.holmi_production.money_counter_app.model.ButtonTypeEnums
import com.example.holmi_production.money_counter_app.model.SpDirection
import com.example.holmi_production.money_counter_app.model.SquareImageView
import com.example.holmi_production.money_counter_app.model.entity.CategoryEntity
import com.example.holmi_production.money_counter_app.model.entity.SubCategoryEntity
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppCompatFragment
import com.example.holmi_production.money_counter_app.utils.ColorUtils
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.part_fragment_keyboard.*
import kotlinx.android.synthetic.main.view_splitted_button.*
import javax.inject.Inject

class KeyboardPartFragment : AndroidXMvpAppCompatFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.part_fragment_keyboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        App.component.inject(this)
        numbers_keyboard.visibility = View.GONE
        key_progress_bar.max = 100f
        key0.setOnClickListener { pressed(ButtonTypeEnums.ZERO, "0") }
        key1.setOnClickListener { pressed(ButtonTypeEnums.NUMERIC, "1") }
        key2.setOnClickListener { pressed(ButtonTypeEnums.NUMERIC, "2") }
        key3.setOnClickListener { pressed(ButtonTypeEnums.NUMERIC, "3") }
        key4.setOnClickListener { pressed(ButtonTypeEnums.NUMERIC, "4") }
        key5.setOnClickListener { pressed(ButtonTypeEnums.NUMERIC, "5") }
        key6.setOnClickListener { pressed(ButtonTypeEnums.NUMERIC, "6") }
        key7.setOnClickListener { pressed(ButtonTypeEnums.NUMERIC, "7") }
        key_8.setOnClickListener { pressed(ButtonTypeEnums.NUMERIC, "8") }
        key9.setOnClickListener { pressed(ButtonTypeEnums.NUMERIC, "9") }
        keyDivider.setOnClickListener { pressed(ButtonTypeEnums.DIVIDER, ".") }
        keyDelete.setOnClickListener { pressed(ButtonTypeEnums.DELETE) }
        key_spending.setOnClickListener { pressed(ButtonTypeEnums.ENTER_UP) }
        key_income.setOnClickListener { pressed(ButtonTypeEnums.ENTER_DOWN) }
        key_category.setOnClickListener { pressed(ButtonTypeEnums.CATEGORY) }
        comment.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                comment?.hideKeyboardFrom(context!!)
                handled = true
            }
            return@OnEditorActionListener handled
        })
        comment.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                comment?.hideKeyboardFrom(context!!)
            }
        }
        purshace_sum_textview.text = purshaseSum
        cg_subcategory_group.clearCheck()
    }

    fun setListener(mKeyboardListener: IKeyboardListener?) {
        this.mKeyboardListener = mKeyboardListener
    }

    fun updateCategoryButton(category: CategoryEntity?) {
        val text = key_category.findViewById<TextView>(R.id.tv_category_btn_placeholder)
        val image = key_category.findViewById<SquareImageView>(R.id.iv_category)

        if (category == null) {
            image.visibility = View.GONE
            text.visibility = View.VISIBLE
            key_progress_bar.visibility = View.GONE
            text.text = "Категории не созданы"
        } else {
            image.visibility = View.VISIBLE
            text.visibility = View.GONE
            key_progress_bar.visibility = View.VISIBLE
            key_category.setBackgroundColor(category.color)
            image.setImageResource(category.imageId ?: R.drawable.ic_launcher_foreground)
            key_progress_bar.apply {
                setBackgroundColor(category.color)
                progress = 55f
                progressColor = category.color
                invalidate()
            }
        }
        numbers_keyboard.visibility = View.VISIBLE
    }

    fun showChipsContainer(subcategories: List<SubCategoryEntity>, color: Int) {
        if (subcategories.isNullOrEmpty()) {
            container_chips.visibility = View.GONE
        } else {
            container_chips.visibility = View.VISIBLE
            for (i in subcategories.indices) {
                val alpha = 255 - (i + 1) * 35
                cg_subcategory_group.addView(buildChip(subcategories[i], color, alpha) as View)
            }
        }
    }

    fun showActionButtons(directions: List<SpDirection>) {
        splittedButton.changeButtonState(directions)
    }

    private fun buildChip(subcategory: SubCategoryEntity, color: Int, alpha: Int): Chip {
        val chip = Chip(context)
        chip.isCheckable = true
        chip.isClickable = true
        chip.setTextColor(ColorUtils.getFontColor(color))
        chip.text = subcategory.description
        chip.background.alpha = alpha
        chip.chipBackgroundColor = ColorStateList.valueOf(color)
        chip.textSize = 20f
        chip.tag = subcategory.id
        return chip
    }

    private fun pressed(type: ButtonTypeEnums, value: String? = null) {
        vibrator.vibrate(50)

        when (type) {
            ButtonTypeEnums.DELETE -> {
                if (purshaseSum == "0") return
                else {
                    purshaseSum = purshaseSum.dropLast(1)
                    if (purshaseSum.takeLast(1) == ".") {
                        purshaseSum = purshaseSum.dropLast(1)
                    }
                    if (purshaseSum.isEmpty())
                        purshaseSum = "0"
                }
            }
            ButtonTypeEnums.DIVIDER -> {
                when {
                    value == "." && purshaseSum.contains(".") -> return
                    purshaseSum == "" -> purshaseSum = "0."
                    else -> purshaseSum += value
                }
            }
            ButtonTypeEnums.ZERO -> {
                if (purshaseSum == "0") return
                if (purshaseSum.contains(Regex("[.].*"))) return
                else purshaseSum += value

            }
            ButtonTypeEnums.ENTER_UP -> {
                enterPressed(SpDirection.SPENDING)
            }
            ButtonTypeEnums.ENTER_DOWN -> {
                enterPressed(SpDirection.INCOME)
            }
            ButtonTypeEnums.NUMERIC -> {
                if (purshaseSum == "0")
                    purshaseSum = ""
                if (purshaseSum.contains('.') && purshaseSum.takeLast(1) != ".")
                    purshaseSum = purshaseSum.dropLast(1)
                purshaseSum += value
            }
            ButtonTypeEnums.CATEGORY -> {
                mKeyboardListener!!.showCategoryDialog()
            }
        }

        purshace_sum_textview.text = purshaseSum
        mKeyboardListener!!.moneyUpdated(purshaseSum.toDouble())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mKeyboardListener = null
        Log.d("M_KeyboardPart", "onDestroy view")
    }

    private fun enterPressed(isSpending: SpDirection) {
        when (purshaseSum) {
            "0" -> return
            else -> {
                val text = comment.text.toString()
                val checkedId = cg_subcategory_group.checkedChipId
                var tag: Int? = null
                if (checkedId != View.NO_ID) {
                    val chips = cg_subcategory_group.findViewById<Chip>(checkedId)
                    tag = chips.tag as Int
                }

                mKeyboardListener!!.enterPressed(purshaseSum.toDouble(), text, isSpending, tag)
                purshaseSum = "0"
                clearCommentField()
                uncheckChips()
            }
        }
    }

    private fun uncheckChips() {
        cg_subcategory_group.clearCheck()
    }

    private fun clearCommentField() {
        comment.setText("")
    }

    @Inject
    lateinit var vibrator: Vibrator

    private var purshaseSum = "0"
    private var mKeyboardListener: IKeyboardListener? = null

    companion object {
        fun newInstance(): KeyboardPartFragment {
            return KeyboardPartFragment()
        }
    }
}

interface IKeyboardListener {
    fun enterPressed(money: Double, comment: String, isSpending: SpDirection, subcategoryId: Int?)
    fun moneyUpdated(money: Double)
    fun showCategoryDialog()
}