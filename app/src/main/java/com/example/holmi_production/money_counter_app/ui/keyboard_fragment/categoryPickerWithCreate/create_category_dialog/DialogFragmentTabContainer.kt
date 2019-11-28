package com.example.holmi_production.money_counter_app.ui.keyboard_fragment.categoryPickerWithCreate.create_category_dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.holmi_production.money_counter_app.App
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.entity.Category
import com.example.holmi_production.money_counter_app.model.entity.SubCategory
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppComaptDialogFragment
import com.example.holmi_production.money_counter_app.ui.keyboard_fragment.categoryPickerWithCreate.edit_category_dialog.EditCategoryDialog
import kotlinx.android.synthetic.main.container_category_create_dialog.*
import leakcanary.AppWatcher

class DialogFragmentTabContainer : AndroidXMvpAppComaptDialogFragment(), ISubcategoryCreateCallback,
    ICategoryCreateCallback, ViewCreateCategory {
    override fun dismissDialog() {
        dismiss()
    }

    companion object {
        fun newInstance(args: Bundle): DialogFragmentTabContainer {
            val fragment = DialogFragmentTabContainer()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.container_category_create_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = this.arguments
        val categories = bundle!!.get("categories") as? Array<Category>

        category_create_tabs.setupWithViewPager(category_create_viewPager)
        category_create_viewPager.adapter =
            CreateCategoryAdapter(
                fm = childFragmentManager,
                categoryCallback = this,
                subcategoryCallback = this,
                categories = categories!!
            )
    }

    override fun onDestroy() {
        super.onDestroy()
        AppWatcher.objectWatcher.watch(this)
    }

    override fun subcategoryCreated(categoryName: String, parentId: Int) {
        callback.addSubcategory(SubCategory(description = categoryName, parentId = parentId))
        dismiss()
    }

    override fun categoryUpdated(category:Category) {
        callback.updateCategory(category)
        dismiss()
    }

    fun setListener(callback: EditCategoryDialog.ICategoryEditor){
        this.callback = callback
    }

    @ProvidePresenter
    fun initPresenter(): PresenterCreateCategory = App.component.getCategoryCreatePresenter()

    @InjectPresenter
    lateinit var presenter: PresenterCreateCategory

    lateinit var callback:EditCategoryDialog.ICategoryEditor
}
