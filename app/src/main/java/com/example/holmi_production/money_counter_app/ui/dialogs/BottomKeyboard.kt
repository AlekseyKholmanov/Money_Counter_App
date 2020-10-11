package com.example.holmi_production.money_counter_app.ui.dialogs

import android.animation.LayoutTransition
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.extensions.hideKeyboardFrom
import com.example.holmi_production.money_counter_app.model.Item
import com.example.holmi_production.money_counter_app.model.entity.SubCategoryEntity
import com.example.holmi_production.money_counter_app.model.enums.ButtonType
import com.example.holmi_production.money_counter_app.model.enums.SpDirection
import com.example.holmi_production.money_counter_app.ui.adapter.SelectCategoryAdapter
import com.example.holmi_production.money_counter_app.ui.adapter.holder.SelectCategoryHolder
import com.example.holmi_production.money_counter_app.ui.adapter.items.CategoryItem
import com.example.holmi_production.money_counter_app.ui.adapter.items.ZeroItem
import com.example.holmi_production.money_counter_app.ui.custom.SingleSelectionGridLayutManager
import com.example.holmi_production.money_counter_app.ui.viewModels.BottomKeyboardViewModel
import com.example.holmi_production.money_counter_app.utils.ColorUtils
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.bottom_keyboard_full.*
import kotlinx.android.synthetic.main.bottom_keyboard_full.chipsContainer
import kotlinx.android.synthetic.main.fragment_category_details.*
import kotlinx.android.synthetic.main.view_splitted_button.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
class BottomKeyboard : BottomSheetDialogFragment() {


    private var displayedSum = "0"

    private val viewModel: BottomKeyboardViewModel by viewModel()

    val args: BottomKeyboardArgs by navArgs()


    private val categoryPickerCallback by lazy(LazyThreadSafetyMode.NONE) {
        object : SelectCategoryHolder.Callback {
            override fun categoryPicked(index: Int, categoryId: String?) {
                manager.check(index)
            }

            override fun createCategorySelected() {
                val destination = BottomKeyboardDirections.actionBottomKeyboardToCategoryDetailsFragment(null)
                findNavController().navigate(destination)
            }

            override fun categoryEdited(categoryId: String) {
                val destination = BottomKeyboardDirections.actionBottomKeyboardToCategoryDetailsFragment(categoryId)
                findNavController().navigate(destination)
            }
        }
    }

    private val checkedListener by lazy(LazyThreadSafetyMode.NONE)
    {
        object : SingleSelectionGridLayutManager.OnCheckedListener {
            override fun onCheckedChange(checkedId: Int) {
                viewModel.selectedCategoryId = if(checkedId == View.NO_ID){
                    showSubcategories(listOf())
                    null
                }else {
                    val categoryItem = (adapter.items[checkedId] as CategoryItem)
                    showSubcategories(categoryItem.subcategories)
                    categoryItem.categoryId

                }
                Log.d("M_BottomKeyboard","$checkedId")
            }
        }
    }

    private fun showSubcategories(items: List<SubCategoryEntity>){
        if(items.isNullOrEmpty()){
            chipsContainer.visibility = View.GONE
        } else{
            subcategories.removeAllViews()
                items
                    .filter{!it.isDeleted}
                    .forEach { subcategory ->
                    subcategories.addView(buildChip(subcategory))
                }

            chipsContainer.visibility = View.VISIBLE
        }
    }

    val manager by lazy(LazyThreadSafetyMode.NONE) {
        SingleSelectionGridLayutManager(requireContext()).apply {
            spanCount = 5
        }
    }

    val adapter: SelectCategoryAdapter by lazy(LazyThreadSafetyMode.NONE) {
        SelectCategoryAdapter(categoryPickerCallback)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.observeCategories()
        viewModel.getAccountInfo(args.accountId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_keyboard_full, container, false)
    }

    private fun setCategories(categories: List<Item>) {
        adapter.items = if (categories.isEmpty()) {
            listOf(ZeroItem(R.layout.item_category_0data))
        } else {
            categories
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val lt = LayoutTransition()
        lt.setAnimateParentHierarchy(false)
        numbers_keyboard.layoutTransition = lt
        manager.setListener(checkedListener)
        categories.layoutManager = manager
        with(viewModel) {
            categories.observe(viewLifecycleOwner, Observer(::setCategories))
        }

        categories.adapter = adapter
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
        itemComment.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                itemComment?.hideKeyboardFrom(requireContext())
                handled = true
            }
            return@OnEditorActionListener handled
        })
        itemComment.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                itemComment?.hideKeyboardFrom(requireContext())
            }
        }
        summary.text = displayedSum
        subcategories.clearCheck()
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
        }
        summary.text = displayedSum
    }


    private fun enterPressed(direction: SpDirection) {
        when (displayedSum) {
            "0" -> return
            else -> {
                val text = itemComment.text.toString()
                val checkedId = subcategories.checkedChipId
                val tag: String? = if (checkedId != View.NO_ID) {
                    val chips = subcategories.findViewById<Chip>(checkedId)
                    chips.tag as String
                } else {
                    null
                }
                val spendingSum =
                    if (direction == SpDirection.SPENDING) -1 * displayedSum.toDouble() else displayedSum.toDouble()
                viewModel.saveTransaction(args.accountId, spendingSum, text, tag)

                displayedSum = "0"
                itemComment.setText("")
                subcategories.clearCheck()
            }
        }
    }

    private fun buildChip(subcategory: SubCategoryEntity): Chip {
        val chip = Chip(context)
        chip.isCheckable = true
        chip.isClickable = true
        chip.text = subcategory.description
        chip.textSize = 20f
        chip.tag = subcategory.id
        return chip
    }

}