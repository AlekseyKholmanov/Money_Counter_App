package com.example.holmi_production.money_counter_app.ui.custom

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import coil.api.load
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.extensions.hideKeyboardFrom
import com.example.holmi_production.money_counter_app.model.ButtonTypeEnums
import com.example.holmi_production.money_counter_app.model.SpDirection
import com.example.holmi_production.money_counter_app.model.SquareImageView
import com.example.holmi_production.money_counter_app.model.entity.CategoryEntity
import com.example.holmi_production.money_counter_app.model.entity.SubCategoryEntity
import com.example.holmi_production.money_counter_app.utils.ColorUtils
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.part_fragment_keyboard_const.view.*
import kotlinx.android.synthetic.main.view_splitted_button.view.*


/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
class KeyboardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyle, defStyleRes) {

    private var purshaseSum = "0"

//    @Inject
//    lateinit var vibrator: Vibrator


    private var mKeyboardListener: IKeyboardListener? = null

    init {
        View.inflate(context, R.layout.part_fragment_keyboard_const, this)
        chipsContainer.visibility = View.GONE
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
        keySpending.setOnClickListener { pressed(ButtonTypeEnums.ENTER_UP) }
        keyIncome.setOnClickListener { pressed(ButtonTypeEnums.ENTER_DOWN) }
        category.setOnClickListener { pressed(ButtonTypeEnums.CATEGORY) }
        comment.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                comment?.hideKeyboardFrom(context)
                handled = true
            }
            return@OnEditorActionListener handled
        })
        comment.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                comment?.hideKeyboardFrom(context)
            }
        }
        summary.text = purshaseSum
        cg_subcategory_group.clearCheck()
    }

    fun setListener(listener: IKeyboardListener?) {
        mKeyboardListener = listener
    }

    fun setCategory(entity: CategoryEntity?) {
        val text = category.findViewById<TextView>(R.id.categoryDescription)
        val image = category.findViewById<SquareImageView>(R.id.categoryImage)
        //ToDO show no categoryImage
        if (entity == null) {
            image.visibility = View.GONE
            text.visibility = View.VISIBLE
//            limitBar.visibility = View.GONE
            text.text = "Категории не созданы"
        } else {
            image.visibility = View.VISIBLE
            text.visibility = View.GONE
//            limitBar.visibility = View.VISIBLE
            category.setBackgroundColor(entity.color)
            image.load(R.drawable.ic_launcher_foreground)
//            limitBar.apply {
//                setBackgroundColor(entity.color)
//                progress = 55f
//                progressColor = entity.color
//                invalidate()
//            }
        }
    }

    fun showActionButtons(directions: List<SpDirection>?) {
        if(directions != null){

            splittedButton.changeButtonState(directions)
        }
    }

    fun showChipsContainer(subcategories: List<SubCategoryEntity>, color: Int) {
        if (subcategories.isNullOrEmpty()) {
            chipsContainer.visibility = View.GONE
        } else {
            chipsContainer.visibility = View.VISIBLE
            for (i in subcategories.indices) {
                val alpha = 255 - (i + 1) * 35
                cg_subcategory_group.addView(buildChip(subcategories[i], color, alpha) as View)
            }
        }
    }

    private fun pressed(type: ButtonTypeEnums, value: String? = null) {

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
                mKeyboardListener?.showCategoryDialog()
            }
        }
        summary.text = purshaseSum
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

                mKeyboardListener?.enterPressed(purshaseSum.toDouble(), text, isSpending, tag)

                purshaseSum = "0"
                comment.setText("")
                cg_subcategory_group.clearCheck()
            }
        }
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
}

interface IKeyboardListener {
    fun enterPressed(money: Double, comment: String, isSpending: SpDirection, subcategoryId: Int?)
    fun showCategoryDialog()
}