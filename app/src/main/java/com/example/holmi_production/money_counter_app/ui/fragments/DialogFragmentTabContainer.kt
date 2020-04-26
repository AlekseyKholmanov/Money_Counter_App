package com.example.holmi_production.money_counter_app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.holmi_production.money_counter_app.App
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.entity.CategoryEntity
import com.example.holmi_production.money_counter_app.model.entity.SubCategoryEntity
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppComaptDialogFragment
import com.example.holmi_production.money_counter_app.ui.adapter.CreateCategoryAdapter
import com.example.holmi_production.money_counter_app.ui.dialogs.EditCategoryDialog
import com.example.holmi_production.money_counter_app.ui.presenters.CreateCategoryPresenter
import com.example.holmi_production.money_counter_app.ui.presenters.CreateCategoryView
import kotlinx.android.synthetic.main.container_category_create_dialog.*
import javax.inject.Inject


class DialogFragmentTabContainer : AndroidXMvpAppComaptDialogFragment(),
    ISubcategoryCreateCallback,
    ICategoryCreateCallback,
    CreateCategoryView {
    override fun dismissDialog() {
        dismiss()
    }

    companion object {
        fun newInstance(args: Bundle): DialogFragmentTabContainer {
            val fragment =
                DialogFragmentTabContainer()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.container_category_create_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = this.arguments
        val categories = bundle!!.get("categories") as? Array<CategoryEntity>

        category_create_tabs.setupWithViewPager(category_create_viewPager)
        category_create_viewPager.adapter =
            CreateCategoryAdapter(
                fm = childFragmentManager,
                categoryCallback = this,
                subcategoryCallback = this,
                categories = categories!!
            )
    }

    override fun subcategoryCreated(categoryName: String, parentId: Int, color: Int) {
        callback.addSubcategory(
            SubCategoryEntity(
                description = categoryName,
                parentId = parentId,
                color = color
            )
        )
        dismiss()
    }

    override fun categoryUpdated(category: CategoryEntity) {
        callback.updateCategory(category)
        dismiss()
    }

    fun setListener(callback: EditCategoryDialog.ICategoryEditor) {
        this.callback = callback
    }

    @Inject
    @InjectPresenter
    lateinit var presenter: CreateCategoryPresenter

    @ProvidePresenter
    fun initPresenter(): CreateCategoryPresenter = App.component.getCategoryCreatePresenter()

    lateinit var callback: EditCategoryDialog.ICategoryEditor
}
