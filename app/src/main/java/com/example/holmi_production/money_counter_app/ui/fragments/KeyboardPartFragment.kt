package com.example.holmi_production.money_counter_app.ui.fragments

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import coil.api.load
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.extensions.hideKeyboardFrom
import com.example.holmi_production.money_counter_app.main.BaseFragment
import com.example.holmi_production.money_counter_app.model.ButtonTypeEnums
import com.example.holmi_production.money_counter_app.model.CategoryDetails
import com.example.holmi_production.money_counter_app.model.SpDirection
import com.example.holmi_production.money_counter_app.model.entity.CategoryEntity
import com.example.holmi_production.money_counter_app.model.entity.SubCategoryEntity
import com.example.holmi_production.money_counter_app.ui.view_models.KeyboardViewModel
import com.example.holmi_production.money_counter_app.utils.ColorUtils
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.button_with_description.*
import kotlinx.android.synthetic.main.part_fragment_keyboard_const.*
import kotlinx.android.synthetic.main.view_splitted_button.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
class KeyboardPartFragment : BaseFragment(R.layout.part_fragment_keyboard_const) {
    override fun inject() {
    }

    private val keyboardViewModel: KeyboardViewModel by viewModel()

    private var purshaseSum = "0"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(keyboardViewModel) {
            categoryLiveData.observe(viewLifecycleOwner, Observer(::setCategory))
        }
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
                comment?.hideKeyboardFrom(requireContext())
                handled = true
            }
            return@OnEditorActionListener handled
        })
        comment.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                comment?.hideKeyboardFrom(requireContext())
            }
        }
        summary.text = purshaseSum
        cg_subcategory_group.clearCheck()
    }

    private fun setCategory(categoryDetails: CategoryDetails?) {
        setCategory(categoryDetails?.category)
        if (categoryDetails?.subcategory?.isNotEmpty() ?: return) {
            showChipsContainer(categoryDetails.subcategory, categoryDetails.category.color)
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
                findNavController().navigate(R.id.selectCategoryFragment)
            }
        }
        summary.text = purshaseSum
    }

    private fun setCategory(entity: CategoryEntity?) {
        //ToDO show no categoryImage
        if (entity == null) {
            categoryImage.visibility = View.GONE
            categoryDescription.visibility = View.VISIBLE
            categoryDescription.text = "Категории не созданы"
        } else {
            categoryImage.visibility = View.VISIBLE
            categoryDescription.visibility = View.GONE
            category.setBackgroundColor(entity.color)
            categoryImage.load(R.drawable.ic_launcher_foreground)
        }
    }

    private fun enterPressed(direction: SpDirection) {
        when (purshaseSum) {
            "0" -> return
            else -> {
                val text = comment.text.toString()
                val checkedId = cg_subcategory_group.checkedChipId
                val tag: Int? = if (checkedId != View.NO_ID) {
                    val chips = cg_subcategory_group.findViewById<Chip>(checkedId)
                    chips.tag as Int
                } else {
                    null
                }
                keyboardViewModel.saveSending(purshaseSum.toDouble(), direction, text, tag)

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

    private fun showChipsContainer(subcategories: List<SubCategoryEntity>, color: Int) {
        if (subcategories.isNullOrEmpty()) {
            chipsContainer.visibility = View.GONE
        } else {
            chipsContainer.visibility = View.VISIBLE
            for (i in subcategories.indices) {
                val alpha = 255 - (i + 1) * 35
                cg_subcategory_group.addView(
                    buildChip(
                        subcategories[i], color, alpha
                    ) as View
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("M_KeyboardPartFragment", "on destroy view")
    }
}