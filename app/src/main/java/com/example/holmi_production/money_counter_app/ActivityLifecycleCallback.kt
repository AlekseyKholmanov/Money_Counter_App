package com.example.holmi_production.money_counter_app

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.example.holmi_production.money_counter_app.main.WithoutToolbar
import com.example.holmi_production.money_counter_app.ui.MainActivity
import kotlinx.android.synthetic.main.activity_main.*

class ActivityLifecycleCallback :
    Application.ActivityLifecycleCallbacks,
    FragmentManager.FragmentLifecycleCallbacks() {

    override fun onActivityCreated(
        activity: Activity,
        savedInstanceState: Bundle?
    ) {
        (activity as? FragmentActivity)
            ?.supportFragmentManager
            ?.registerFragmentLifecycleCallbacks(this, true)
    }

    override fun onActivityStarted(activity: Activity) = Unit

    override fun onActivityResumed(activity: Activity) = Unit

    override fun onActivityPaused(activity: Activity) = Unit

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) = Unit

    override fun onActivityStopped(activity: Activity) = Unit

    override fun onActivityDestroyed(activity: Activity) = Unit

    override fun onFragmentViewCreated(
        fm: FragmentManager,
        f: Fragment,
        v: View,
        savedInstanceState: Bundle?
    ) {
        if (f.requireActivity() is MainActivity) {
            if (f is WithoutToolbar) {
                if (f.requireActivity() is MainActivity) {
                    (f.requireActivity() as MainActivity).toolbar.visibility = View.GONE
                }
            } else {
                (f.requireActivity() as MainActivity).toolbar.visibility = View.VISIBLE
            }
        }
    }
}