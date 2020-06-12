package com.example.holmi_production.money_counter_app.ui.dialogs

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import coil.api.load
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.extensions.hideKeyboardFrom
import com.example.holmi_production.money_counter_app.model.CategoryDetails
import com.example.holmi_production.money_counter_app.model.entity.SubCategoryEntity
import com.example.holmi_production.money_counter_app.model.enums.ButtonType
import com.example.holmi_production.money_counter_app.model.enums.SpDirection
import com.example.holmi_production.money_counter_app.ui.viewModels.BottomKeyboardViewModel
import com.example.holmi_production.money_counter_app.utils.ColorUtils
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.button_with_description.*
import kotlinx.android.synthetic.main.part_fragment_keyboard_const.*
import kotlinx.android.synthetic.main.view_splitted_button.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
class BottomKeyboard : BottomSheetDialogFragment() {


    private var displayedSum = "0"

    private val bottomViewModel: BottomKeyboardViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bottomViewModel.observeCategory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.part_fragment_keyboard_const, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomViewModel.categoryLiveData.observe(viewLifecycleOwner, Observer(::setCategory))
        key0.setOnClickListener { pressed(ButtonType.ZERO, "0") }
        key1.setOnClickListener { pressed(ButtonType.NUMERIC, "1") }
        key2.setOnClickListener { pressed(ButtonType.NUMERIC, "2") }
        key3.setOnClickListener { pressed(ButtonType.NUMERIC, "3") }
        key4.setOnClickListener { pressed(ButtonType.NUMERIC, "4") }
        key5.setOnClickListener { pressed(ButtonType.NUMERIC, "5") }
        key6.setOnClickListener { pressed(ButtonType.NUMERIC, "6") }
        key7.setOnClickListener { pressed(ButtonType.NUMERIC, "7") }
        key_8.setOnClickListener { pressed(ButtonType.NUMERIC, "8") }
        key9.setOnClickListener { pressed(ButtonType.NUMERIC, "9") }
        keyDivider.setOnClickListener { pressed(ButtonType.DIVIDER, ".") }
        keyDelete.setOnClickListener { pressed(ButtonType.DELETE) }
        keySpending.setOnClickListener { pressed(ButtonType.ENTER_UP) }
        keyIncome.setOnClickListener { pressed(ButtonType.ENTER_DOWN) }
        category.setOnClickListener { pressed(ButtonType.CATEGORY) }
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
        summary.text = displayedSum
        cg_subcategory_group.clearCheck()
    }

    private fun setCategory(categoryDetails: CategoryDetails?) {

        //ToDO show no categoryImage
        if (categoryDetails == null) {
            categoryImage.visibility = View.GONE
            categoryDescription.visibility = View.VISIBLE
            categoryDescription.text = "Категории не созданы"
        } else {
            categoryImage.visibility = View.VISIBLE
            categoryDescription.visibility = View.GONE
            category.setBackgroundColor(categoryDetails.category.color)
            categoryImage.load(
                ContextCompat.getDrawable(
                    requireContext(),
                    categoryDetails.category.imageId ?: R.drawable.ic_launcher_background
                )
            )
        }
    }

    private fun pressed(type: ButtonType, value: String? = null) {
        when (type) {
            ButtonType.DELETE -> {
                if (displayedSum == "0") return
                else {
                    displayedSum = displayedSum.dropLast(1)
                    if (displayedSum.takeLast(1) == ".") {
                        displayedSum = displayedSum.dropLast(1)
                    }
                    if (displayedSum.isEmpty())
                        displayedSum = "0"
                }
            }
            ButtonType.DIVIDER -> {
                when {
                    value == "." && displayedSum.contains(".") -> return
                    displayedSum == "" -> displayedSum = "0."
                    else -> displayedSum += value
                }
            }
            ButtonType.ZERO -> {
                if (displayedSum == "0") return
                if (displayedSum.contains(Regex("[.].*"))) return
                else displayedSum += value

            }
            ButtonType.ENTER_UP -> {
                enterPressed(SpDirection.SPENDING)
            }
            ButtonType.ENTER_DOWN -> {
                enterPressed(SpDirection.INCOME)
            }
            ButtonType.NUMERIC -> {
                if (displayedSum == "0")
                    displayedSum = ""
                if (displayedSum.contains('.') && displayedSum.takeLast(1) != ".")
                    displayedSum = displayedSum.dropLast(1)
                displayedSum += value
            }
            ButtonType.CATEGORY -> {
                findNavController().navigate(R.id.selectCategoryFragment)
            }
        }
        summary.text = displayedSum
    }


    private fun enterPressed(direction: SpDirection) {
        when (displayedSum) {
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
                bottomViewModel.saveSending(displayedSum.toDouble(), direction, text, tag)

                displayedSum = "0"
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

}