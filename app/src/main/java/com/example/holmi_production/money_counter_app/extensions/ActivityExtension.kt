package com.example.holmi_production.money_counter_app.extensions

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

fun Activity.hideKeyboard() {
    val view = this.currentFocus
    if (view != null) {
        (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(view.windowToken,0)
    }
}

fun EditText.hideKeyboardFrom(context: Context){
    val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}

fun Activity.isKeyboardOpen(): Boolean {

    val softKeyboardHeight = 100
    val rect = Rect()
    val rootView = window.decorView

    rootView.getWindowVisibleDisplayFrame(rect)

    val dm = rootView.resources.displayMetrics
    val heightDiff = rootView.bottom - rect.bottom
    return heightDiff > softKeyboardHeight * dm.density || heightDiff == 0

}

fun Activity.isKeyboardClosed(): Boolean {
    return !isKeyboardOpen()
}