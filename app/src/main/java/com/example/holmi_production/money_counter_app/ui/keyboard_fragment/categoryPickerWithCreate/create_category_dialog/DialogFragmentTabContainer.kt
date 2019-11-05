package com.example.holmi_production.money_counter_app.ui.keyboard_fragment.categoryPickerWithCreate.create_category_dialog

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.holmi_production.money_counter_app.App
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.SpDirection
import com.example.holmi_production.money_counter_app.model.entity.Category
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppComaptDialogFragment
import kotlinx.android.synthetic.main.base_category_create_dialog.*

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
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.base_category_create_dialog, container, false)
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

    override fun subcategoryCreated(categoryName: String, parentId: Int) {
        presenter.createSubCategory(categoryName,parentId)
        dismiss()
    }

    override fun categoryCreated(
        categoryName: String,
        directions: Collection<SpDirection>,
        color: ColorDrawable?) {
        presenter.createCategory(categoryName, directions.toList(), color)
    }

    @ProvidePresenter
    fun initPresenter(): PresenterCreateCategory = App.component.getCategoryCreatePresenter()

    @InjectPresenter
    lateinit var presenter: PresenterCreateCategory
}
