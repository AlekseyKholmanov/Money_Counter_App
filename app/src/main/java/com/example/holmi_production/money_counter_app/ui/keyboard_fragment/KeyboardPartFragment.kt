package com.example.holmi_production.money_counter_app.ui.keyboard_fragment

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
import com.example.holmi_production.money_counter_app.model.ButtonTypes
import com.example.holmi_production.money_counter_app.model.SpDirection
import com.example.holmi_production.money_counter_app.model.SquareImageView
import com.example.holmi_production.money_counter_app.model.entity.Category
import com.example.holmi_production.money_counter_app.model.entity.SubCategory
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppCompatFragment
import com.example.holmi_production.money_counter_app.utils.ColorUtils
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_keyboard_part.*
import javax.inject.Inject

class KeyboardPartFragment private constructor() : AndroidXMvpAppCompatFragment() {
    companion object {
        fun newInstance(): KeyboardPartFragment {
            return KeyboardPartFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_keyboard_part, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        key_progress_bar.max = 100f
        key_progress_bar.progress = 0f
        key_0.setOnClickListener { pressed(ButtonTypes.ZERO, "0") }
        key_1.setOnClickListener { pressed(ButtonTypes.NUMERIC, "1") }
        key_2.setOnClickListener { pressed(ButtonTypes.NUMERIC, "2") }
        key_3.setOnClickListener { pressed(ButtonTypes.NUMERIC, "3") }
        key_4.setOnClickListener { pressed(ButtonTypes.NUMERIC, "4") }
        key_5.setOnClickListener { pressed(ButtonTypes.NUMERIC, "5") }
        key_6.setOnClickListener { pressed(ButtonTypes.NUMERIC, "6") }
        key_7.setOnClickListener { pressed(ButtonTypes.NUMERIC, "7") }
        key_8.setOnClickListener { pressed(ButtonTypes.NUMERIC, "8") }
        key_9.setOnClickListener { pressed(ButtonTypes.NUMERIC, "9") }
        key_divider.setOnClickListener { pressed(ButtonTypes.DIVIDER, ".") }
        key_delete.setOnClickListener { pressed(ButtonTypes.DELETE) }
        key_spending.setOnClickListener { pressed(ButtonTypes.ENTER_UP) }
        key_income.setOnClickListener { pressed(ButtonTypes.ENTER_DOWN) }
        key_category.setOnClickListener { pressed(ButtonTypes.CATEGORY) }
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
        App.component.inject(this)
    }

    fun setListener(mKeyboardListener: IKeyboardListener?) {
        this.mKeyboardListener = mKeyboardListener
    }

    fun updateCategoryButton(category: Category?) {
        val text = key_category.findViewById<TextView>(R.id.tv_category_btn_placeholder)
        val image = key_category.findViewById<SquareImageView>(R.id.iv_category)

        if (category == null) {
            image.visibility = View.GONE
            text.visibility = View.VISIBLE
            text.text = "Категории не созданы"
        } else {
            image.visibility = View.VISIBLE
            text.visibility = View.GONE
            key_category.setBackgroundColor(category.color ?: ColorUtils.getColor())
            image.setImageResource(category.imageId ?: R.drawable.ic_launcher_foreground)
        }


        key_progress_bar.setBackgroundColor(category?.color ?: ColorUtils.getColor())
        key_progress_bar.progressColor = ColorUtils.getColor()
        key_progress_bar.progress = 55f
        key_progress_bar.invalidate()
    }

    fun showChipsContainer(subcategories: List<SubCategory>, color: Int) {
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
        key_income.visibility =
            if (directions.contains(SpDirection.INCOME)) View.VISIBLE else View.GONE
        key_spending.visibility =
            if (directions.contains(SpDirection.SPENDING)) View.VISIBLE else View.GONE
        key_accumulation.visibility =
            if (directions.contains(SpDirection.ACCUMULATION)) View.VISIBLE else View.GONE

    }

    private fun buildChip(subcategory: SubCategory, color: Int, alpha: Int): Chip {
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

    private fun pressed(type: ButtonTypes, value: String? = null) {
        vibrator.vibrate(50)

        when (type) {
            ButtonTypes.DELETE -> {
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
            ButtonTypes.DIVIDER -> {
                when {
                    value == "." && purshaseSum.contains(".") -> return
                    purshaseSum == "" -> purshaseSum = "0."
                    else -> purshaseSum += value
                }
            }
            ButtonTypes.ZERO -> {
                if (purshaseSum == "0") return
                if (purshaseSum.contains(Regex("[.].*"))) return
                else purshaseSum += value

            }
            ButtonTypes.ENTER_UP -> {
                enterPressed(SpDirection.SPENDING)
            }
            ButtonTypes.ENTER_DOWN -> {
                enterPressed(SpDirection.INCOME)
            }
            ButtonTypes.NUMERIC -> {
                if (purshaseSum == "0")
                    purshaseSum = ""
                if (purshaseSum.contains('.') && purshaseSum.takeLast(1) != ".")
                    purshaseSum = purshaseSum.dropLast(1)
                purshaseSum += value
            }
            ButtonTypes.CATEGORY -> {
                mKeyboardListener!!.showCategoryDialog()
            }
        }

        purshace_sum_textview.text = purshaseSum
        mKeyboardListener!!.moneyUpdated(purshaseSum.toDouble())
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("M_KeyboardPart", "onDestroy")
        super.onDestroy()
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
}

interface IKeyboardListener {
    fun enterPressed(money: Double, comment: String, isSpending: SpDirection, subcategoryId: Int?)
    fun moneyUpdated(money: Double)
    fun showCategoryDialog()
}