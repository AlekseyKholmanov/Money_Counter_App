package com.example.holmi_production.money_counter_app.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.RecyclerItem
import com.example.holmi_production.money_counter_app.ui.adapter.decorators.FirstLineDecorator
import com.example.holmi_production.money_counter_app.ui.adapter.delegate.dialogFirstItemDelegate
import com.example.holmi_production.money_counter_app.ui.adapter.delegate.dialogHeaderItemDelegate
import com.example.holmi_production.money_counter_app.ui.adapter.delegate.dialogValueGuidItemAdapterDelegate
import com.example.holmi_production.money_counter_app.ui.adapter.items.DialogFirstItem
import com.example.holmi_production.money_counter_app.ui.adapter.items.DialogValueGuidItem
import com.example.holmi_production.money_counter_app.useCases.GetAccountsUseCase
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import kotlinx.android.synthetic.main.bottom_dialog.*
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class AccountPickDialog : BottomSheetDialogFragment() {
    companion object {
        const val REQUEST_FROM_CHART_ACCOUNT_SELECTION = "request_from_chart_account_selection"
    }

    val args: AccountPickDialogArgs by navArgs()

    val getAccountUseCase: GetAccountsUseCase by inject()

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        val delegatesManager = AdapterDelegatesManager<List<RecyclerItem>>().apply {
            addDelegate(dialogHeaderItemDelegate())
            addDelegate(dialogValueGuidItemAdapterDelegate {
                finish(value = it)
                dismiss()
            })
            if (args.withCreateAction) {
                addDelegate(dialogFirstItemDelegate {
                    findNavController().navigate(R.id.action_global_createAccountFragment)
                    dismiss()
                })
            }
        }
        ListDelegationAdapter(delegatesManager)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler.addItemDecoration(FirstLineDecorator(requireContext()))
        recycler.adapter = adapter
        adapter.items = prepareItems()
    }

    private fun prepareItems(): List<RecyclerItem> {
        val items = mutableListOf<RecyclerItem>()
        viewLifecycleOwner.lifecycleScope.launch {
            val accounts = getAccountUseCase.getAccounts()
            accounts.forEachIndexed { index, accountEntity ->
                items.add(
                    DialogValueGuidItem(
                        accountEntity.id,
                        "${accountEntity.description}",
                        accountEntity.id == args.selectedIdString
                    )
                )
            }
            if (args.showValueAll) {
                items.add(0, DialogValueGuidItem("", "All accounts", args.selectedIdString == null))
            }
            if (args.withCreateAction) {
                items.add(0, DialogFirstItem("Add account"))
            }
            adapter.notifyDataSetChanged()
        }
        return items
    }

    private fun finish(value: String) {
        setFragmentResult(
            args.from, bundleOf(
                BottomDialog.TYPE_ACCOUNT_ID to value
            )
        )
    }
}