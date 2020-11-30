package com.example.holmi_production.money_counter_app.main

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.holmi_production.money_counter_app.ui.custom.CustomBackgroundDrawable
import kotlinx.android.synthetic.main.activity_main.*


/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
abstract class BaseFragment : Fragment {

    constructor() : super()

    constructor(@LayoutRes layoutRes: Int) : super(layoutRes)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val displayMetrics = android.graphics.Point()
        requireActivity().display?.getRealSize(displayMetrics)
        val height = displayMetrics.y
        val width = displayMetrics.x
        val background = CustomBackgroundDrawable(width, height)
        requireActivity().window.setBackgroundDrawable(background)
    }

    protected fun updateToolbarTitle(title: String = "") {
        (requireActivity() as? ToolbarOwner).let {
            it?.toolbar?.title = title
        }
    }
}