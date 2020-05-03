package com.example.holmi_production.money_counter_app.ui.fragments

import android.os.Bundle
import android.view.View
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.di.components.AppComponent
import com.example.holmi_production.money_counter_app.main.BaseFragment
import com.example.holmi_production.money_counter_app.main.Navigation
import com.example.holmi_production.money_counter_app.ui.presenters.CreateCategoryPresenter
import com.example.holmi_production.money_counter_app.ui.presenters.CreateCategoryView
import kotlinx.android.synthetic.main.part_create_category.*
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class CreateCategoryFragment : BaseFragment(R.layout.part_create_category), CreateCategoryView {

    override fun inject() {
        AppComponent.instance.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val categoryDetail = CategoryDetailsFragment.newInstance()
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
        fun newInstance(): CreateCategoryFragment {
            return CreateCategoryFragment()
        }
    }

    override fun popUp() {
        (requireActivity() as Navigation).popUp()
    }

}