package com.example.holmi_production.money_counter_app.ui.keyboard_fragment.categoryPickerWithCreate.create_category_dialog

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.holmi_production.money_counter_app.App
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.SpDirection
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppComaptDialogFragment
import kotlinx.android.synthetic.main.base_category_create_dialog.*

class DialogFragmentTabContainer : AndroidXMvpAppComaptDialogFragment(), ISubcategoryCreateCallback, ICategoryCreateCallback, ViewCreateCategory {
    override fun dismissDialog() {
        dismiss()
    }

    companion object {

        fun newInstance(): DialogFragmentTabContainer {
            return DialogFragmentTabContainer()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.base_category_create_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        category_create_tabs.setupWithViewPager(category_create_viewPager)
        category_create_viewPager.adapter =
            CategoryFragmentManager(fm = childFragmentManager, categoryCallback = this, subcategoryCallback = this)
    }

    override fun subcategoryCreated() {
       presenter.createSubCategory()
    }

    override fun categoryCreated(
        categoryName: String,
        categoryType: List<SpDirection>,
        color: ColorDrawable?) {
        presenter.createCategory(categoryName, listOf(), color)
    }

    @ProvidePresenter
    fun initPresenter(): PresenterCreateCategory  = App.component.getCategoryCreatePresenter()

    @InjectPresenter
    lateinit var presenter: PresenterCreateCategory
}
