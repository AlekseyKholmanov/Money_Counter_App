package com.example.holmi_production.money_counter_app.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.util.Log
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
        Log.d("M_BottomKeyboard", "setChecked")
        this.checkedId = checkedId
        listener?.onCheckedChange(checkedId)
    }

    override fun addView(child: View?) {
        if (child is MCheckable) {
            super.addView(child)
        }
    }

    override fun addView(child: View?, index: Int) {
        if (child is MCheckable) {
            super.addView(child,index)
        }
    }

    fun check(id: Int) {
        Log.d("M_BottomKeyboard", "check $id")
        if (id == checkedId) {
            setCheckedStateForView(id, false)
            checkedId = View.NO_ID
            listener?.onCheckedChange(View.NO_ID)
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
        Log.d("M_BottomKeyboard", "checkedState $viewId")
        val checkedView: View? = getChildAt(viewId)
        if (checkedView is MCheckable) {
            checkedView.setChecked(checked)
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