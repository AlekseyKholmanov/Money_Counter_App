package com.example.holmi_production.money_counter_app.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.GridLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.holmi_production.money_counter_app.App
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.main.MainActivity
import com.example.holmi_production.money_counter_app.model.entity.Category
import com.example.holmi_production.money_counter_app.model.entity.SubCategory
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppCompatFragment
import com.example.holmi_production.money_counter_app.ui.adapter.CategoryPickerAdapter
import com.example.holmi_production.money_counter_app.ui.adapter.holder.CategoryPickerHolder
import com.example.holmi_production.money_counter_app.ui.dialogs.EditCategoryDialog
import com.example.holmi_production.money_counter_app.ui.dialogs.EditCategoryDialog.ICategoryEditor
import com.example.holmi_production.money_counter_app.ui.presenters.CategoryPickerPresenter
import com.example.holmi_production.money_counter_app.ui.presenters.CategoryPickerView
import com.example.holmi_production.money_counter_app.ui.utils.ViewAnimation
import kotlinx.android.synthetic.main.fragment_category_picker.*
import kotlinx.android.synthetic.main.include_category_picker_fragment.*

class CategoryPickerFragment : AndroidXMvpAppCompatFragment(),
    CategoryPickerView, ICategoryEditor {

    private lateinit var fabOpen: Animation
    private lateinit var fabClose: Animation
    private lateinit var rotateForward: Animation
    private lateinit var rotateBackward: Animation


    private val categoryPickerCallback = object : CategoryPickerHolder.Callback {
        override fun categoryPicked(categoryId: Int) {
            val bundle = bundleOf("categoryId" to categoryId)
            (activity as MainActivity).showMain(bundle = bundle)
        }

        override fun categoryEdited(pair: Pair<Category, List<SubCategory>>) {
            val bundle = Bundle()
            bundle.putParcelable("category", pair.first)
            bundle.putParcelableArray("subcategories", pair.second.toTypedArray())
            Log.d("M_FrCategoryPicker", "edit category ${pair.first.id} ${pair.first.description}")
            val dialog = EditCategoryDialog.newInstance(args = bundle)
            dialog.setListener(this@CategoryPickerFragment)
            dialog.show(
                childFragmentManager,
                EDIT_DIALOG_TAG
            )
        }
    }

    private var isFabOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fabOpen = AnimationUtils.loadAnimation(requireContext(), R.anim.fab_open)
        fabClose = AnimationUtils.loadAnimation(requireContext(), R.anim.fab_close)
        rotateForward = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_forward)
        rotateBackward = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_backward)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_category_picker, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = CategoryPickerAdapter(categoryPickerCallback)
        val layoutManager = GridLayoutManager(context, 3)

        categoryList.layoutManager = layoutManager
        categoryList.adapter = adapter

        addFab.setOnClickListener {
            animateFab()
        }
        categoryFab.setOnClickListener {
            presenter.getDialogData()
            animateFab()
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
    override fun updateSubcategories(subcategories: List<SubCategory>) {
        val dialog = childFragmentManager.findFragmentByTag(EDIT_DIALOG_TAG)
        if (dialog != null)
            (dialog as EditCategoryDialog).updateSubcategories(subcategories)
    }

    override fun showToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    override fun addSubcategory(subcategory: SubCategory) {
        presenter.createSubCategory(subcategory)
    }

    override fun deleteSubcategory(subcategory: SubCategory) {
        presenter.deleteSubcategory(subcategory)
    }

    override fun updateCategory(category: Category) {
        Log.d("M_FrCategoryPicker", "category: id:${category.id} desc: ${category.description}")
        presenter.insertCategory(category)
    }

    override fun showCreateDialog(it: Array<Category>) {
        val bundle = Bundle()
        bundle.putParcelableArray("categories", it)
        val dialog = DialogFragmentTabContainer.newInstance(args = bundle)
        dialog.setListener(this)
        dialog.show(
            childFragmentManager,
            CREATE_DIALOG_TAG
        )
    }

    override fun showCategories(categories: ArrayList<Pair<Category, List<SubCategory>>>) {
        adapter.setCategory(categories)
        adapter.notifyDataSetChanged()
    }

    override fun showMessage(show: Boolean, messageResId: Int?) {
        categoryList.visibility = if (show) View.INVISIBLE else View.VISIBLE
        tv_empty_categories.visibility = if (show) View.VISIBLE else View.GONE
        if (show && messageResId != null)
            tv_empty_categories.text = getString(messageResId)
    }

    @InjectPresenter
    lateinit var presenter: CategoryPickerPresenter
    lateinit var adapter: CategoryPickerAdapter

    @ProvidePresenter
    fun initPresenter(): CategoryPickerPresenter {
        return App.component.getCategoryPickerPresenter()
    }

    companion object {
        fun newInstance(): CategoryPickerFragment {
            return CategoryPickerFragment()
        }

        private const val CREATE_DIALOG_TAG = "create dialog"
        private const val EDIT_DIALOG_TAG = "edit dialog"
    }

}