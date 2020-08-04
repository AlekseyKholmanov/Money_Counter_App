package com.example.holmi_production.money_counter_app.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Checkable
import androidx.recyclerview.widget.GridLayoutManager

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
class SingleSelectionGridLayutManager @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : GridLayoutManager(context, attrs, defStyle, defStyleRes) {

    interface OnCheckedListener {
        fun onCheckedChange(checkedId: Int)
    }

    private var listener: OnCheckedListener? = null
    private var checkedId = View.NO_ID

    fun setListener(listener: OnCheckedListener) {
        this.listener = listener
    }

    private fun setChecked(checkedId: Int) {
        this.checkedId = checkedId
        listener?.onCheckedChange(checkedId)
    }

    override fun addView(child: View?) {
        super.addView(child)
        (child as Checkable).isChecked = false
    }

    override fun addView(child: View?, index: Int) {
        super.addView(child, index)
        (child as Checkable).isChecked = false
    }

    fun check(id: Int) {
        if (id == checkedId) {
            return
        }
        if (checkedId != View.NO_ID) {
            setCheckedStateForView(checkedId, false)
            setCheckedStateForView(id, true)
        } else {
            setCheckedStateForView(id, true)
        }
        setChecked(id)
    }

    private fun setCheckedStateForView(
        viewId: Int,
        checked: Boolean
    ) {
        val checkedView: View? = getChildAt(viewId)
        if (checkedView is Checkable) {
            checkedView.isChecked = checked
        }
    }

    fun clearCheck() {
        for (i in 0..childCount) {
            val view = getChildAt(i)
            if (view is Checkable) {
                view.isChecked = false
            }
        }
        checkedId = View.NO_ID
    }
}