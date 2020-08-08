package com.example.holmi_production.money_counter_app.ui.fragments

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.main.BaseFragment
import com.example.holmi_production.money_counter_app.model.Item
import com.example.holmi_production.money_counter_app.ui.adapter.SelectCategoryAdapter
import com.example.holmi_production.money_counter_app.ui.adapter.holder.SelectCategoryHolder
import com.example.holmi_production.money_counter_app.ui.adapter.items.ZeroItem
import com.example.holmi_production.money_counter_app.ui.utils.ViewAnimation
import com.example.holmi_production.money_counter_app.ui.viewModels.SelectCategoryViewModel
import kotlinx.android.synthetic.main.fragment_select_category.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class SelectCategoryFragment : BaseFragment(R.layout.fragment_select_category) {

    private lateinit var fabOpen: Animation
    private lateinit var fabClose: Animation
    private lateinit var rotateForward: Animation
    private lateinit var rotateBackward: Animation

    val selectCategoryViewModel: SelectCategoryViewModel by viewModel()


    private val categoryPickerCallback = object : SelectCategoryHolder.Callback {
        override fun categoryPicked(index: Int, categoryId: String?) {
        }

        override fun createCategorySelected() {
            TODO("Not yet implemented")
        }

        override fun categoryEdited(categoryId: String) {
        }
    }

    val adapter: SelectCategoryAdapter = SelectCategoryAdapter(categoryPickerCallback)

    private var isFabOpen = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectCategoryViewModel.observeCategories()
        fabOpen = AnimationUtils.loadAnimation(requireContext(), R.anim.fab_open)
        fabClose = AnimationUtils.loadAnimation(requireContext(), R.anim.fab_close)
        rotateForward = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_forward)
        rotateBackward = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_backward)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(selectCategoryViewModel) {
            categories.observe(viewLifecycleOwner, Observer(::setCategories))
        }
        categoryList.adapter = adapter
        addFab.setOnClickListener {
            animateFab()
        }
        categoryFab.setOnClickListener {
            val direction = SelectCategoryFragmentDirections.actionSelectCategoryFragmentToCategoryDetailsFragment(null)
            findNavController().navigate(direction)
            animateFab()
        }
        subcategoryFab.setOnClickListener {
            //TODO open subcategory fragment here
        }
    }

    private fun setCategories(categories: List<Item>) {
        adapter.items = if (categories.isEmpty()) {
            listOf(ZeroItem(R.layout.item_category_0data))
        } else {
            categories
        }
        adapter.notifyDataSetChanged()
    }

    private fun animateFab() {
        isFabOpen = if (isFabOpen) {
            addFab.startAnimation(rotateBackward)
            categoryFab.startAnimation(fabClose)
            subcategoryFab.startAnimation(fabClose)
            ViewAnimation.fadeOut(frameBackground)
            frameBackground.isClickable = false
            false
        } else {
            addFab.startAnimation(rotateForward)
            categoryFab.startAnimation(fabOpen)
            subcategoryFab.startAnimation(fabOpen)
            ViewAnimation.fadeIn(frameBackground)
            frameBackground.isClickable = true
            true
        }
        categoryFab.isClickable = isFabOpen
        subcategoryFab.isClickable = isFabOpen
    }

}