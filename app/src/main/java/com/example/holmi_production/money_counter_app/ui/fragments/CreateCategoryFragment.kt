package com.example.holmi_production.money_counter_app.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.holmi_production.money_counter_app.App
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.di.components.AppComponent
import com.example.holmi_production.money_counter_app.main.BaseFragment
import com.example.holmi_production.money_counter_app.main.Navigation
import com.example.holmi_production.money_counter_app.model.entity.CategoryEntity
import com.example.holmi_production.money_counter_app.ui.presenters.CostsPresenter
import com.example.holmi_production.money_counter_app.ui.presenters.CreateCategoryPresenter
import com.example.holmi_production.money_counter_app.ui.presenters.CreateCategoryView
import com.example.holmi_production.money_counter_app.ui.presenters.EditCategoryPresenter
import kotlinx.android.synthetic.main.part_create_category.*
import moxy.ktx.moxyPresenter
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject
import javax.inject.Provider

class CategoryCreateFragment : BaseFragment(R.layout.part_create_category), CreateCategoryView {

    override fun inject() {
        AppComponent.instance.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val categoryDetail = CategoryDetailFragment.newInstance()
        childFragmentManager.beginTransaction().apply {
            add(R.id.container_detail_category, categoryDetail)
            commit()
        }
        btn_create_category.setOnClickListener {
           val category =  categoryDetail.getCurrentState()
            presenter.createCategory(category)
        }
        btn_create_category.isEnabled = categoryDetail.isValidState
    }

    @Inject
    lateinit var presenterProvider: Provider<CreateCategoryPresenter>

    private val presenter by moxyPresenter { presenterProvider.get() }

    companion object {
        fun newInstance(): CategoryCreateFragment {
            return CategoryCreateFragment()
        }
    }

    override fun popUp() {
        (requireActivity() as Navigation).popUp()
    }

}