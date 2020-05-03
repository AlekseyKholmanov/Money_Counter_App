package com.example.holmi_production.money_counter_app.ui.fragments

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.custom.ColorSeekBar
import com.example.holmi_production.money_counter_app.di.components.AppComponent
import com.example.holmi_production.money_counter_app.extensions.hideKeyboardFrom
import com.example.holmi_production.money_counter_app.main.BaseFragment
import com.example.holmi_production.money_counter_app.model.SpDirection
import com.example.holmi_production.money_counter_app.model.entity.CategoryEntity
import com.example.holmi_production.money_counter_app.ui.dialogs.ImageCategoryPicker
import com.example.holmi_production.money_counter_app.ui.presenters.CategoryDetailPresenter
import com.example.holmi_production.money_counter_app.utils.ColorUtils
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Function4
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.container_category_detail.*
import moxy.ktx.moxyPresenter
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Provider
import kotlin.random.Random

class CategoryDetailFragment :
    BaseFragment(R.layout.container_category_detail) {

    var isValidState = false

    private val disposables = CompositeDisposable()

    override fun inject() {
        AppComponent.instance.inject(this)
    }

    @Inject
    lateinit var presenterProvider: Provider<CategoryDetailPresenter>

    private val presenter by moxyPresenter { presenterProvider.get() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val category = arguments?.getParcelable("category") as CategoryEntity?

        if (category != null) {
            category.color.let { iv_category_image.setBackgroundColor(it) }
            category.imageId?.let { iv_category_image.setImageResource(it) }
            et_category_name.setText(category.description)
            iv_category_image.tag = category.imageId
            for (i in category.spendingDirection) {
                if (i == SpDirection.INCOME)
                    ch_income.isChecked = true
                if (i == SpDirection.SPENDING)
                    ch_spending.isChecked = true
                if (i == SpDirection.ACCUMULATION)
                    ch_accumulation.isChecked = true
            }
        }
        color_seek_bar.setOnColorChangeListener(object : ColorSeekBar.OnColorChangeListener {
            override fun onColorChangeListener(color: Int) {
                //gives the selected color
                iv_category_image.setBackgroundColor(color)
            }
        })

        iv_category_image.setOnClickListener {
            val dialog =
                ImageCategoryPicker(
                    requireContext()
                ) { imageArrayPosition ->
                    val imageId = requireContext().resources.obtainTypedArray(R.array.images)
                    val id = imageId.getResourceId(imageArrayPosition, -1)
                    iv_category_image.setImageResource(id)

                    iv_category_image.tag = imageArrayPosition
                    iv_category_image.invalidate()
                    imageId.recycle()
                }

            dialog.show()
        }

        val categoryNameObservable = et_category_name
            .textChanges()
            .debounce(300, TimeUnit.MILLISECONDS)
            .map { it.isNotBlank() }

        val accumulationObservable = ch_accumulation
            .clicks()
            .map {
                ch_accumulation.isChecked
            }
        val incomeObservable = ch_income
            .clicks()
            .map {
                ch_income.isChecked
            }
        val spendingObservable = ch_spending
            .clicks()
            .map { ch_spending.isChecked }

        Observable.combineLatest(
            categoryNameObservable, accumulationObservable, incomeObservable,
            spendingObservable,
            Function4 { categoryNameOk: Boolean, incomeChecked: Boolean, accumulationChecked: Boolean, spendingChecked: Boolean ->
                categoryNameOk && incomeChecked && accumulationChecked && spendingChecked
            })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                isValidState = it
            }
            .addTo(disposables)


        btn_generate_color.setOnClickListener {
            val rand = Random.nextInt(0, color_seek_bar.width)
            color_seek_bar.setColor(rand.toFloat())

//            val color = ColorUtils.getColor()
//            v_color_container.setBackgroundColor(color)
//            iv_category_image.setBackgroundColor(color)
        }
        et_category_name.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                et_category_name?.hideKeyboardFrom(requireContext())
                handled = true
            }
            return@OnEditorActionListener handled
        })
        et_category_name.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus)
                et_category_name?.hideKeyboardFrom(requireContext())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.dispose()
    }

    fun getCurrentState(): CategoryEntity {
        val background = iv_category_image.background
        val color: ColorDrawable? = background as? ColorDrawable
        val imageId = iv_category_image.tag as Int?
        val category = (arguments?.getParcelable("category") as CategoryEntity?)
        val categoryId = category?.id ?: 0
        Log.d("M_CatDetailFragment", "dsada")
        return CategoryEntity(
            id = categoryId,
            imageId = imageId,
            color = color?.color ?: ColorUtils.getColor(),
            description = et_category_name.text.toString(),
            isDeleted = false,
            spendingDirection = getDirections()
        )
    }

    fun isCheckboxesChecked(): Boolean {
        return (ch_income.isChecked or ch_accumulation.isChecked or ch_spending.isChecked) and et_category_name.text.isNotBlank()
    }

    private fun getDirections(): List<SpDirection> {
        val directions = mutableListOf<SpDirection>()
        if (ch_income.isChecked)
            directions.add(SpDirection.INCOME)
        if (ch_accumulation.isChecked)
            directions.add(SpDirection.ACCUMULATION)
        if (ch_spending.isChecked)
            directions.add(SpDirection.SPENDING)
        return directions
    }

    companion object {
        fun newInstance(): CategoryDetailFragment {
            val fr =
                CategoryDetailFragment()
            fr.arguments = null
            return fr
        }

        fun newInstance(bundle: Bundle): CategoryDetailFragment {
            val fr =
                CategoryDetailFragment()
            fr.arguments = bundle
            return fr
        }
    }
}