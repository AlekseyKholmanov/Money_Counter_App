package com.example.holmi_production.money_counter_app.ui.fragments

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.main.BaseFragment
import com.example.holmi_production.money_counter_app.main.Navigation
import com.example.holmi_production.money_counter_app.model.Item
import com.example.holmi_production.money_counter_app.ui.adapter.SelectCategoryAdapter
import com.example.holmi_production.money_counter_app.ui.adapter.holder.SelectCategoryHolder
import com.example.holmi_production.money_counter_app.ui.adapter.items.ZeroItem
import com.example.holmi_production.money_counter_app.ui.utils.ViewAnimation
import com.example.holmi_production.money_counter_app.ui.viewModels.SelectCategoryViewModel
import kotlinx.android.synthetic.main.fragment_select_category.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class SelectCategoryFragment : BaseFragment(R.layout.fragment_select_category) {

    private lateinit var fabOpen: Animation
    private lateinit var fabClose: Animation
    private lateinit var rotateForward: Animation
    private lateinit var rotateBackward: Animation

//    @Inject
//    lateinit var vmFactory : ViewModelProvider.Factory

    val selectCategoryViewModel: SelectCategoryViewModel by viewModel()

    lateinit var adapter: SelectCategoryAdapter


    private val categoryPickerCallback = object : SelectCategoryHolder.Callback {
        override fun categoryPicked(categoryId: Int) {
            selectCategoryViewModel.categorySelected(categoryId)
            findNavController().popBackStack()
        }

        override fun categoryEdited(categoryId: Int) {
            val bundle = Bundle()
            bundle.putInt("categoryId", categoryId)
            categoryFab.setOnClickListener {
                (requireActivity() as Navigation).loadFragment(
                    EditCategoryFragment.newInstance(bundle),
                    isAddedToBackstack = true,
                    withBottomBar = false,
                    withDatePickerFragment = false
                )
                animateFab()
            }
        }
    }

    private var isFabOpen = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fabOpen = AnimationUtils.loadAnimation(requireContext(), R.anim.fab_open)
        fabClose = AnimationUtils.loadAnimation(requireContext(), R.anim.fab_close)
        rotateForward = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_forward)
        rotateBackward = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_backward)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar("Выберите категорию")
        with(selectCategoryViewModel) {
            categories.observe(viewLifecycleOwner, Observer(::setCategories))
        }
        adapter = SelectCategoryAdapter(categoryPickerCallback)
        val layoutManager = GridLayoutManager(context, 3)

        categoryList.layoutManager = layoutManager
        categoryList.adapter = adapter

        addFab.setOnClickListener {
            animateFab()
        }
        categoryFab.setOnClickListener {
            findNavController().navigate(R.id.createCategoryFragment)
            animateFab()
        }
        subcategoryFab.setOnClickListener {
            //TODO open subcategory fragment here
        }
    }

    private fun setCategories(categories: List<Item>) {
        adapter.items = if (categories.isEmpty()) {
            listOf(ZeroItem(R.layout.item_category_0data))
        } else {
            categories
        }
    }

    private fun animateFab() {
        isFabOpen = if (isFabOpen) {
            addFab.startAnimation(rotateBackward)
            categoryFab.startAnimation(fabClose)
            subcategoryFab.startAnimation(fabClose)
            ViewAnimation.fadeOut(frameBackground)
            frameBackground.isClickable = false
            false
        } else {
            addFab.startAnimation(rotateForward)
            categoryFab.startAnimation(fabOpen)
            subcategoryFab.startAnimation(fabOpen)
            ViewAnimation.fadeIn(frameBackground)
            frameBackground.isClickable = true
            true
        }
        categoryFab.isClickable = isFabOpen
        subcategoryFab.isClickable = isFabOpen
    }

//    override fun showToast(text: String) {
//        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
//    }
//
//    override fun popUp() {
//        findNavController().popBackStack()
//    }
//
//    override fun showCategories(categories: ArrayList<Pair<CategoryEntity, List<SubCategoryEntity>>>) {
//        adapter.setCategory(categories)
//        adapter.notifyDataSetChanged()
//    }
//
//    override fun showMessage(show: Boolean, messageResId: Int?) {
//        categoryList.visibility = if (show) View.INVISIBLE else View.VISIBLE
//        tv_empty_categories.visibility = if (show) View.VISIBLE else View.GONE
//        if (show && messageResId != null)
//            tv_empty_categories.text = getString(messageResId)
//    }

}