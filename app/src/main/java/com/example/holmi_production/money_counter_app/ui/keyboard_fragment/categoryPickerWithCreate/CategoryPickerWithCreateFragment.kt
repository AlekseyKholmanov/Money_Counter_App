package com.example.holmi_production.money_counter_app.ui.keyboard_fragment.categoryPickerWithCreate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.holmi_production.money_counter_app.App
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.SpDirection
import com.example.holmi_production.money_counter_app.model.entity.Category
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppCompatFragment
import kotlinx.android.synthetic.main.fragment_category_picker_with_create.*

class CategoryPickerWithCreateFragment : AndroidXMvpAppCompatFragment(), ICategoryCreateCallback, CategoryPickerView,
    ICategoryPickerCallback {
    override fun categoryPicked(categoryId: Int) {
        val bundle = bundleOf("categoryId" to categoryId)
        findNavController().navigate(R.id.action_categoryPickerWithCreateFragment_to_mainFragment, bundle)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_category_picker_with_create, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = CategoryPickerAdapter(
            mutableListOf(), this
        )
        val layoutManager = GridLayoutManager(context, 3)
        rv_categoryList.layoutManager = layoutManager
        rv_categoryList.adapter = adapter

        btn_add_category.setOnClickListener {
            val dialog = DialogCategoryCreate.newInstance()
            dialog.setCallback(this)
            dialog.show(childFragmentManager,"createCategoryDialog")
        }
        presenter.observeCategories()
    }

    override fun showCategories(categories: MutableList<Category>) {
        adapter.setCategory(categories)
    }

    override fun showMessage(show: Boolean, messageResId: Int?) {
        rv_categoryList.visibility = if (show) View.INVISIBLE else View.VISIBLE
        tv_empty_categories.visibility = if (show) View.VISIBLE else View.GONE
        if (show && messageResId!=null)
            tv_empty_categories.text = getString(messageResId)
    }

    override fun categoryCreated(categoryName: String, categoryType: List<SpDirection>, isSubCategory: Boolean) {
        presenter.createCategory(categoryName, categoryType, isSubCategory)
    }

    @ProvidePresenter
    fun initPresenter(): CategoryPickerPresenter {
        return App.component.getCategoryPickerPresenter()
    }

    @InjectPresenter
    lateinit var presenter: CategoryPickerPresenter
    lateinit var adapter: CategoryPickerAdapter

    companion object {
        fun newInstance(): CategoryPickerWithCreateFragment {
            return CategoryPickerWithCreateFragment()
        }
    }

}