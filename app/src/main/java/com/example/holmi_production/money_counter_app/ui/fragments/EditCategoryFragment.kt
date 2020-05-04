package com.example.holmi_production.money_counter_app.ui.fragments

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.di.components.AppComponent
import com.example.holmi_production.money_counter_app.main.BaseFragment
import com.example.holmi_production.money_counter_app.model.entity.SubCategoryEntity
import com.example.holmi_production.money_counter_app.ui.dialogs.CreateSubcategoryDialog
import com.example.holmi_production.money_counter_app.ui.presenters.EditCategoryPresenter
import com.example.holmi_production.money_counter_app.ui.presenters.EditCategoryView
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.dialog_edit_category.*
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class EditCategoryFragment : BaseFragment(R.layout.dialog_edit_category), EditCategoryView {

    lateinit var name: EditText

    @Inject
    lateinit var presenterProvider: Provider<EditCategoryPresenter>

    private val presenter by moxyPresenter { presenterProvider.get() }

    override fun inject() {
        AppComponent.instance.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val categoryDetail = CategoryDetailsFragment.newInstance(requireArguments())
        childFragmentManager.beginTransaction().apply {
            replace(R.id.container, categoryDetail)
            commit()
        }


        val subcategories = arguments?.getParcelableArray("subcategories") as Array<*>
        subcategories.forEach { subcategory ->
            chips_group.addView(buildChip(subcategory as SubCategoryEntity))
        }
        btn_update.setOnClickListener {
            //presenter update here
        }
        btn_create_subcategory.setOnClickListener {
            val dialog =
                CreateSubcategoryDialog {
                    //callback add subcategory
                }
            dialog.show(childFragmentManager, "CreateSubcategoryDialog")
        }
    }

    private fun buildChip(subcategory: SubCategoryEntity): Chip {
        val chip = Chip(context)
        val text = subcategory.description
        chip.text = text
        chip.chipBackgroundColor = ColorStateList.valueOf(subcategory.color)
        chip.textSize = 20f
        chip.isCloseIconVisible = true
        chip.setOnCloseIconClickListener {
            chips_group.removeView(chip)
        }
        return chip
    }

    fun updateSubcategories(subcategories: List<SubCategoryEntity>) {
        chips_group.removeAllViews()
        subcategories.forEach {
            val chip = buildChip(it)
            chips_group.addView(chip)
        }
    }

    companion object {
        fun newInstance(args: Bundle): EditCategoryFragment {
            val fr =
                EditCategoryFragment()
            fr.arguments = args
            return fr
        }
    }

    override fun showState() {

    }
}