package com.example.holmi_production.money_counter_app.costs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.holmi_production.money_counter_app.App
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.costs.adapter.CostsAdapter
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppCompatFragment
import kotlinx.android.synthetic.main.costst_fragment.*

class CostsFragment : AndroidXMvpAppCompatFragment(), CostsView {

    private lateinit var adapter: CostsAdapter
    @InjectPresenter
    lateinit var presenter: CostsPresenter

    @ProvidePresenter
    fun providePresenter() = App.component.getCostsPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.costst_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = CostsAdapter()
        spendingList.layoutManager = LinearLayoutManager(requireContext())
        spendingList.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.getCount()
        presenter.loadCosts()
    }

    override fun onError(error: Throwable) {
            showEmptyPlaceholder()
    }

    override fun showSpending(spending: List<ListItem>) {
        adapter.items = spending
        if (spending.isEmpty())
            showEmptyPlaceholder()
        else {
            emptyPlaceholder.isVisible = false
            spendingList.isVisible = true
            adapter.notifyDataSetChanged()
        }
    }

    private fun showEmptyPlaceholder() {
        emptyPlaceholder.isVisible = true
        spendingList.isVisible = false
    }

}