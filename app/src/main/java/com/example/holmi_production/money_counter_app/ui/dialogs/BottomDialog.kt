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
import com.example.holmi_production.money_counter_app.model.enums.CurrencyType
import com.example.holmi_production.money_counter_app.ui.adapter.decorators.AsyncBaseDecorator
import com.example.holmi_production.money_counter_app.ui.adapter.decorators.ListDelegationDecorator
import com.example.holmi_production.money_counter_app.ui.adapter.delegate.*
import com.example.holmi_production.money_counter_app.ui.adapter.items.DialogFirstItem
import com.example.holmi_production.money_counter_app.ui.adapter.items.DialogValueGuidItem
import com.example.holmi_production.money_counter_app.ui.adapter.items.DialogValueTextItem
import com.example.holmi_production.money_counter_app.useCases.GetAccountsUseCase
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import kotlinx.android.synthetic.main.bottom_dialog.*
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class BottomDialog : BottomSheetDialogFragment() {

    val args: BottomDialogArgs by navArgs()

    val getAccountUseCase: GetAccountsUseCase by inject()

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        ListDelegationAdapter(
            dialogHeaderItemDelegate(),
            dialogValueItemAdapterDelegate() {
                finish(value = it)
                dismiss()
            },
            dialogValueTextItemAdapterDelegate {
                finish(value = it)
                dismiss()
            },
            dialogValueGuidItemAdapterDelegate {
                finish(value = it)
                dismiss()
            },
            dialogFirstItemDelegate {
                findNavController().navigate(R.id.action_global_createAccountFragment2)
                dismiss()
            }
        )
    }

    companion object {
        val REQUEST_FROM_ACCOUNT_CURRENCY_TYPE = "request_from_account_currency_type"
        val REQUEST_FROM_ACCOUNT_SELECTION = "request_from_account_selection"
        val TYPE_ACCOUNT_CURRENCY = "currency_type"
        val TYPE_ACCOUNT_SELECTION = "account_selection"

        val ARGS_SELECTED_ID_STRING = "selectedIdString"
        val ARGS_SELECTED_ID_INT = "selectedIdInt"
        val ARGS_DIALOG_TYPE = "dialogType"
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
        recycler.addItemDecoration(ListDelegationDecorator(requireContext()))
        recycler.adapter = adapter
        adapter.items = prepareItems()
    }

    private fun prepareItems(): List<RecyclerItem> {
        val items = mutableListOf<RecyclerItem>()
        when (args.dialogType) {
            TYPE_ACCOUNT_CURRENCY -> {
                CurrencyType.values().forEach {
                    items.add(
                        DialogValueTextItem(
                            it.ordinal,
                            "${it.name}: ${it.icon}",
                            it.ordinal == args.selectedIdInt
                        )
                    )
                }
            }
            TYPE_ACCOUNT_SELECTION -> {
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
                    items.add(0, DialogFirstItem("Add account"))
                    adapter.notifyDataSetChanged()
                }
            }
        }
        return items
    }

    private fun finish(value: Any) {
        when (args.dialogType) {
            TYPE_ACCOUNT_CURRENCY -> {
                setFragmentResult(
                    REQUEST_FROM_ACCOUNT_CURRENCY_TYPE, bundleOf(
                        TYPE_ACCOUNT_CURRENCY to value as Int
                    )
                )
            }
            TYPE_ACCOUNT_SELECTION -> {
                setFragmentResult(
                    REQUEST_FROM_ACCOUNT_SELECTION, bundleOf(
                        TYPE_ACCOUNT_SELECTION to value as String
                    )
                )
            }
        }
    }

}