package com.example.holmi_production.money_counter_app.ui.fragments

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.view.size
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.extensions.hideKeyboardFrom
import com.example.holmi_production.money_counter_app.main.BaseFragment
import com.example.holmi_production.money_counter_app.model.CategoryDetails
import com.example.holmi_production.money_counter_app.model.entity.SubCategoryEntity
import com.example.holmi_production.money_counter_app.model.enums.Images
import com.example.holmi_production.money_counter_app.ui.custom.ColorSeekBar
import com.example.holmi_production.money_counter_app.ui.dialogs.CreateSubcategoryDialog
import com.example.holmi_production.money_counter_app.ui.dialogs.ImageCategoryPicker
import com.example.holmi_production.money_counter_app.ui.viewModels.CategoryDetailsViewModel
import com.example.holmi_production.money_counter_app.utils.ColorUtils
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_category_details.*
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import reactivecircus.flowbinding.android.widget.textChanges
import java.util.*
import kotlin.random.Random

class CategoryDetailsFragment : BaseFragment(R.layout.fragment_category_details) {


    private val categoryDetailsViewModel: CategoryDetailsViewModel by viewModel()

    private val args: CategoryDetailsFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoryDetailsViewModel.observeCategoryById(args.categoryId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryDetailsViewModel.categories.observe(viewLifecycleOwner, Observer(::setCategory))

        categoryImage.setOnClickListener {
            val dialog =
                ImageCategoryPicker(
                    requireContext()
                ) { imageId ->
                    val image = Images.getImageById(imageId)
                    categoryImage.setImageResource(image)

                    categoryImage.tag = imageId
                    categoryImage.invalidate()
                }
            dialog.show()
        }
        addSubcategory.setOnClickListener {
            CreateSubcategoryDialog() {
                val chip = buildChip(it)
                chips.addView(chip)
            }.show(childFragmentManager, "CreateSubcategory")
        }

        seekBar.setOnColorChangeListener(object : ColorSeekBar.OnColorChangeListener {
            override fun onColorChangeListener(color: Int) {
                categoryImage.setBackgroundColor(color)
            }

        })

        categoryName
            .textChanges()
            .debounce(300)
            .map { it.isNotBlank() }
            .onEach {
                saveCategory.isSelected = it
            }
            .launchIn(lifecycleScope)

        generateColor.setOnClickListener {
            val rand = Random.nextInt(0, seekBar.width)
            seekBar.setColor(rand.toFloat())
        }
        with(categoryName) {
            setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    categoryName?.hideKeyboardFrom(requireContext())
                }
                false
            }
            setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus)
                    categoryName?.hideKeyboardFrom(requireContext())
            }
        }
        saveCategory.setOnClickListener {
            categoryDetailsViewModel.createCategory(
                imageId = (categoryImage.tag as Int?) ?: Images.NO_IMAGE,
                color = (categoryImage.background as? ColorDrawable)?.color
                    ?: ColorUtils.getColor(),
                description = categoryName.text.toString(),
                subcategories = getChips()

            )
            findNavController().popBackStack()
        }
    }

    private fun setCategory(categoryDetails: CategoryDetails?) {
        categoryDetails?.category?.let {
            categoryImage.setBackgroundColor(it.color)
            categoryName.setText(it.description)
            seekBar.setColor(it.color.toFloat())
        }
        categoryDetails?.subcategory?.let {
            chips.removeAllViews()
            it.forEach { subcategory ->
                chips.addView(buildChip(subcategory.description))
            }
        }
    }

    private fun getChips(): MutableList<SubCategoryEntity> {
        val count = chips.size
        val items = mutableListOf<SubCategoryEntity>()
        for (i in 0 until count){
            val chip = chips.getChildAt(i) as Chip
            items.add(SubCategoryEntity(id = chip.tag as String,
            description = chip.text.toString(),
            categoryId = categoryDetailsViewModel.categoryId))
        }
        return items
    }

    private fun buildChip(subcategoryName: String, subcategoryId: String?): Chip {
        val chip = Chip(context)
        chip.text = subcategoryName
        chip.tag = subcategoryId ?: UUID.randomUUID().toString()
        chip.textSize = 20f
        chip.isCloseIconVisible = true
        chip.setOnCloseIconClickListener {
            chips.removeView(chip)
        }
        return chip
    }
}