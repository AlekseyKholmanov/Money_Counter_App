package com.example.holmi_production.money_counter_app.ui.fragments

import android.content.res.ColorStateList
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.extensions.hideKeyboardFrom
import com.example.holmi_production.money_counter_app.main.BaseFragment
import com.example.holmi_production.money_counter_app.model.CategoryDetails
import com.example.holmi_production.money_counter_app.model.enums.Images
import com.example.holmi_production.money_counter_app.model.entity.CategoryEntity
import com.example.holmi_production.money_counter_app.model.entity.SubCategoryEntity
import com.example.holmi_production.money_counter_app.ui.custom.ColorSeekBar
import com.example.holmi_production.money_counter_app.ui.dialogs.ImageCategoryPicker
import com.example.holmi_production.money_counter_app.ui.viewModels.CategoryDetailsViewModel
import com.example.holmi_production.money_counter_app.utils.ColorUtils
import com.google.android.material.chip.Chip
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.fragment_category_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class CategoryDetailsFragment : BaseFragment(R.layout.fragment_category_details) {


    private val categoryDetailsViewModel: CategoryDetailsViewModel by viewModel()

    private val args: CategoryDetailsFragmentArgs by navArgs()

    private val disposables = CompositeDisposable()

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

        seekBar.setOnColorChangeListener(object: ColorSeekBar.OnColorChangeListener{
            override fun onColorChangeListener(color: Int) {
                categoryImage.setBackgroundColor(color)
            }

        })

        categoryName
            .textChanges()
            .debounce(300, TimeUnit.MILLISECONDS)
            .map { it.isNotBlank() }
            .subscribe {
                saveCategory.isSelected = it
            }
            .addTo(disposables)

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
            categoryDetailsViewModel.createCategory(getCurrentState())
            findNavController().popBackStack()
        }

    }


    private fun getCurrentState(): CategoryEntity {
        val background = categoryImage.background
        val color: ColorDrawable? = background as? ColorDrawable
        val imageId = categoryImage.tag as Int?
        return CategoryEntity(
            id = args.categoryId ?: UUID.randomUUID().toString(),
            imageId = imageId ?: Images.NO_IMAGE,
            color = color?.color ?: ColorUtils.getColor(),
            description = categoryName.text.toString()
        )
    }

    private fun setCategory(categoryDetails: CategoryDetails?) {
        if (categoryDetails?.subcategory == null) {
            chipsContainer.visibility = View.GONE
        } else {
            chipsContainer.visibility = View.GONE
            with(categoryImage) {
                setBackgroundColor(categoryDetails.category.color)
            }
            categoryName.setText(categoryDetails.category.description)
            seekBar.setColor(categoryDetails.category.color.toFloat())

            categoryDetails.subcategory.let {
                chips.removeAllViews()
                it.forEach { subcategory ->
                    chips.addView(buildChip(subcategory))
                }
            }
        }
    }

    private fun buildChip(subcategory: SubCategoryEntity): Chip {
        val chip = Chip(context)
        val text = subcategory.description
        chip.text = text
        chip.chipBackgroundColor = ColorStateList.valueOf(subcategory.color)
        chip.textSize = 20f
        chip.isCloseIconVisible = true
        chip.setOnCloseIconClickListener {
            chips.removeView(chip)
        }
        return chip
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.dispose()
    }
}