package com.example.holmi_production.money_counter_app.ui.fragments

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.GridLayoutManager
import com.example.holmi_production.money_counter_app.App
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.di.components.AppComponent
import com.example.holmi_production.money_counter_app.main.BaseFragment
import com.example.holmi_production.money_counter_app.main.MainActivity
import com.example.holmi_production.money_counter_app.main.Navigation
import com.example.holmi_production.money_counter_app.model.entity.CategoryEntity
import com.example.holmi_production.money_counter_app.model.entity.SubCategoryEntity
import com.example.holmi_production.money_counter_app.ui.adapter.CategoryPickerAdapter
import com.example.holmi_production.money_counter_app.ui.adapter.holder.CategoryPickerHolder
import com.example.holmi_production.money_counter_app.ui.presenters.CategoryPickerPresenter
import com.example.holmi_production.money_counter_app.ui.presenters.CategoryPickerView
import com.example.holmi_production.money_counter_app.ui.presenters.CostsPresenter
import com.example.holmi_production.money_counter_app.ui.utils.ViewAnimation
import kotlinx.android.synthetic.main.fragment_category_picker.*
import kotlinx.android.synthetic.main.include_category_picker_fragment.*
import moxy.ktx.moxyPresenter
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject
import javax.inject.Provider

class CategoryPickerFragment : BaseFragment(R.layout.fragment_category_picker),
    CategoryPickerView {

    private lateinit var fabOpen: Animation
    private lateinit var fabClose: Animation
    private lateinit var rotateForward: Animation
    private lateinit var rotateBackward: Animation

    @Inject
    lateinit var presenterProvider: Provider<CategoryPickerPresenter>

    private val presenter by moxyPresenter { presenterProvider.get() }


    lateinit var adapter: CategoryPickerAdapter


    private val categoryPickerCallback = object : CategoryPickerHolder.Callback {
        override fun categoryPicked(categoryId: Int) {
            val bundle = bundleOf("categoryId" to categoryId)
            (activity as MainActivity).showMain(bundle = bundle)
        }

        override fun categoryEdited(pair: Pair<CategoryEntity, List<SubCategoryEntity>>) {
            val bundle = Bundle()
            bundle.putParcelable("category", pair.first)
            bundle.putParcelableArray("subcategories", pair.second.toTypedArray())
            categoryFab.setOnClickListener {
                (requireActivity() as Navigation).loadFragment(
                    EditCategoryFragment.newInstance(bundle),
                    isAddedToBackstack = true,
                    withBottomBar = false,
                    withTopbar = false,
                    withAppBar = false
                )
                animateFab()
            }
        }
    }

    private var isFabOpen = false

    override fun inject() {
        AppComponent.instance.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fabOpen = AnimationUtils.loadAnimation(requireContext(), R.anim.fab_open)
        fabClose = AnimationUtils.loadAnimation(requireContext(), R.anim.fab_close)
        rotateForward = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_forward)
        rotateBackward = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_backward)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AppComponent.instance.inject(this)
        adapter = CategoryPickerAdapter(categoryPickerCallback)
        val layoutManager = GridLayoutManager(context, 3)

        categoryList.layoutManager = layoutManager
        categoryList.adapter = adapter

        addFab.setOnClickListener {
            animateFab()
        }
        categoryFab.setOnClickListener {
            (requireActivity() as Navigation).loadFragment(
                CategoryCreateFragment.newInstance(),
                isAddedToBackstack = true,
                withBottomBar = false,
                withTopbar = false,
                withAppBar = false
            )
            animateFab()
        }
        subcategoryFab.setOnClickListener {
            //TODO open subcategory fragment here
        }
        presenter.observeCategories()
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

    //Only for update subcategories in edit Dialog
    override fun updateSubcategories(subcategories: List<SubCategoryEntity>) {
        val dialog = childFragmentManager.findFragmentByTag(EDIT_DIALOG_TAG)
        if (dialog != null)
            (dialog as EditCategoryFragment).updateSubcategories(subcategories)
    }

    override fun showToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    override fun showCategories(categories: ArrayList<Pair<CategoryEntity, List<SubCategoryEntity>>>) {
        adapter.setCategory(categories)
        adapter.notifyDataSetChanged()
    }

    override fun showMessage(show: Boolean, messageResId: Int?) {
        categoryList.visibility = if (show) View.INVISIBLE else View.VISIBLE
        tv_empty_categories.visibility = if (show) View.VISIBLE else View.GONE
        if (show && messageResId != null)
            tv_empty_categories.text = getString(messageResId)
    }

    companion object {
        fun newInstance(): CategoryPickerFragment {
            return CategoryPickerFragment()
        }

        private const val CREATE_DIALOG_TAG = "create dialog"
        private const val EDIT_DIALOG_TAG = "edit dialog"
    }

}