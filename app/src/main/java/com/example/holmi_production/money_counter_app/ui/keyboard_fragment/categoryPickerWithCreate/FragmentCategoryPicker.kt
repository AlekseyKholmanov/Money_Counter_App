package com.example.holmi_production.money_counter_app.ui.keyboard_fragment.categoryPickerWithCreate

import android.os.Bundle
import android.util.Log
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
import com.example.holmi_production.money_counter_app.model.entity.Category
import com.example.holmi_production.money_counter_app.model.entity.SubCategory
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppCompatFragment
import com.example.holmi_production.money_counter_app.ui.keyboard_fragment.categoryPickerWithCreate.create_category_dialog.DialogFragmentTabContainer
import com.example.holmi_production.money_counter_app.ui.keyboard_fragment.categoryPickerWithCreate.edit_category_dialog.EditCategoryDialog
import com.example.holmi_production.money_counter_app.ui.keyboard_fragment.categoryPickerWithCreate.edit_category_dialog.EditCategoryDialog.ICategoryEditor
import kotlinx.android.synthetic.main.fragment_category_picker_with_create.*
import leakcanary.AppWatcher

class FragmentCategoryPicker : AndroidXMvpAppCompatFragment(),
    ViewCategoryPicker,
    ICategoryPickerCallback, ICategoryEditor
{
    override fun addSubcategory(subcategory: SubCategory) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteSubcategory(subcategory: SubCategory) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateCategory(category: Category) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun categoryEdited(pair: Pair<Category, List<SubCategory>>) {
        val bundle = Bundle()
        bundle.putParcelable("category",pair.first)
        bundle.putParcelableArray("subcategories", pair.second.toTypedArray())
        val dialog = EditCategoryDialog.newInstance(args = bundle)
        dialog.setListener(this)
        dialog.show(childFragmentManager, "editCategoryDialog")
    }

    override fun showCreateDialog(it: Array<Category>) {
        val bundle = Bundle()
        bundle.putParcelableArray("categories",it)
        val dialog = DialogFragmentTabContainer.newInstance(args = bundle)
        dialog.show(childFragmentManager, "createCategoryDialog")
    }

    override fun categoryPicked(categoryId: Int) {
        val bundle = bundleOf("categoryId" to categoryId)
        findNavController().navigate(R.id.action_categoryPickerWithCreateFragment_to_mainFragment, bundle)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_category_picker_with_create, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = CategoryPickerAdapter( callback = this)
        val layoutManager = GridLayoutManager(context, 3)
        rv_categoryList.layoutManager = layoutManager
        rv_categoryList.adapter = adapter

        btn_add_category.setOnClickListener {
            presenter.getDialogData()
        }
        presenter.observeCategories()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("M_FragmentCatPicker","CAtegoryPicker Destroyed")
        AppWatcher.objectWatcher.watch(this)
    }

    override fun showCategories(categories: ArrayList<Pair<Category, List<SubCategory>>>) {
        adapter.setCategory(categories)
        adapter.notifyDataSetChanged()
    }

    override fun showMessage(show: Boolean, messageResId: Int?) {
        rv_categoryList.visibility = if (show) View.INVISIBLE else View.VISIBLE
        tv_empty_categories.visibility = if (show) View.VISIBLE else View.GONE
        if (show && messageResId != null)
            tv_empty_categories.text = getString(messageResId)
    }

    @ProvidePresenter
    fun initPresenter(): PresenterCategoryPicker {
        return App.component.getCategoryPickerPresenter()
    }

    @InjectPresenter
    lateinit var presenter: PresenterCategoryPicker
    lateinit var adapter: CategoryPickerAdapter

    companion object {
        fun newInstance(): FragmentCategoryPicker {
            return FragmentCategoryPicker()
        }
    }

}