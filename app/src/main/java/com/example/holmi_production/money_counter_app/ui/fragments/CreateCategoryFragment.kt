package com.example.holmi_production.money_counter_app.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.main.BaseFragment
import com.example.holmi_production.money_counter_app.main.Navigation
import com.example.holmi_production.money_counter_app.ui.presenters.CreateCategoryPresenter
import com.example.holmi_production.money_counter_app.ui.viewModels.KeyboardViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.part_create_category.*
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel


class CreateCategoryFragment : BaseFragment(R.layout.part_create_category) {

    private val disposables = CompositeDisposable()


    private val createCategoryViewModel: KeyboardViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val categoryDetail = CategoryDetailsFragment.newInstance()
        childFragmentManager.beginTransaction().apply {
            add(R.id.container_detail_category, categoryDetail)
            commit()
        }
        btn_create_category.setOnClickListener {
           val category =  categoryDetail.getCurrentState()
//            presenter.createCategory(category)
        }
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            categoryDetail.isValid
                .asFlow()
                .collect {
                    btn_create_category.isEnabled = it
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.dispose()
    }

    companion object {
        fun newInstance(): CreateCategoryFragment {
            return CreateCategoryFragment()
        }
    }

/*    override fun popUp() {
        findNavController().popBackStack()
    }*/

}