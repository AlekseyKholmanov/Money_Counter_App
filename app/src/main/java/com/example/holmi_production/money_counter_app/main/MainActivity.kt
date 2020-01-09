package com.example.holmi_production.money_counter_app.main

import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.holmi_production.money_counter_app.App
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.extensions.hideDelayed
import com.example.holmi_production.money_counter_app.extensions.showDelayed
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppCompatActivity
import com.example.holmi_production.money_counter_app.storage.SettingRepository
import com.example.holmi_production.money_counter_app.ui.charts_fragments.ChartFragment
import com.example.holmi_production.money_counter_app.ui.costs_fragment.CostsFragment
import com.example.holmi_production.money_counter_app.ui.keyboard_fragment.KeyboardFragment
import com.example.holmi_production.money_counter_app.ui.keyboard_fragment.category_picker_fragment.CategoryPickerFragment
import com.example.holmi_production.money_counter_app.ui.limits_fragment.LimitsFragment
import com.example.holmi_production.money_counter_app.ui.topbar_fragment.TopbarFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import leakcanary.AppWatcher
import javax.inject.Inject

class MainActivity : AndroidXMvpAppCompatActivity() {

    @Inject
    lateinit var settingRepository: SettingRepository
    private lateinit var toggle:ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        App.component.inject(this)
//        val navController = findNavController(R.id.mainNavFragment)
//        val graph = navController.graph
//        if (!settingRepository.isOpened())
//            graph.startDestination = R.id.navFirstLaunch
//        else if (settingRepository.getIsEnd())
//            graph.startDestination = R.id.navEndPeriod
//        navController.graph = graph
        initializeDrawers()
        initializeFragments()
        setBottomNavigationController()

        WorkerManager.cancelAll()
        WorkerManager.startBalanceWorker()
        WorkerManager.startNotificationWorker()
        WorkerManager.balancePopulateWork()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean { // The action bar home/up action should open or close the drawer.
        when (item.itemId) {
            R.id.home -> {
                drawer_layout.openDrawer(GravityCompat.START)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initializeDrawers() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        toggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.isDrawerIndicatorEnabled = true
        toggle.syncState()
    }

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
        toggle.syncState()
    }

    private fun initializeFragments() {
        val topbar = TopbarFragment.newInstance()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container_topbar, topbar)
        transaction.commit()
        showMain()
    }

    private fun setBottomNavigationController() {
        main_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.mainFragment -> {
                    showMain()
                }
                R.id.costsFragment -> {
                    loadFragment(CostsFragment.newInstance())
                }
                R.id.chartFragment -> {
                    loadFragment(ChartFragment.newInstance())
                }
                R.id.limitsFragment -> {
                    loadFragment(LimitsFragment.newInstance(), withTopbar = false)
                }
                else -> {
                    throw Exception("uncorrect id")
                }
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    private fun loadFragment(
        fragment: Fragment,
        isAddedToBackstack: Boolean = false,
        withTopbar: Boolean = true,
        withBottomBar: Boolean = true,
        withAppBar: Boolean = false) {
        if (withBottomBar) showBottomNav() else hideBottomNav()
        if (withTopbar) showDatePickerBar() else hideDatePickerBar()
        if (withAppBar) {
            supportActionBar!!.show()
        } else
            supportActionBar!!.hide()

        val transaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
        if (isAddedToBackstack) {
            transaction.addToBackStack(null)
        }
        transaction.replace(R.id.container_main, fragment)
        transaction.commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        AppWatcher.objectWatcher.watch(this)
    }

    override fun onBackPressed() {
        val current = supportFragmentManager.findFragmentById(R.id.container_main)
        if (current is KeyboardFragment) {
            moveTaskToBack(true)
        } else {
            showMain()
            main_navigation.selectedItemId = R.id.mainFragment
        }
    }

    fun showMain(bundle: Bundle? = null) {
        val main = KeyboardFragment.newInstance(bundle)
        loadFragment(main, withTopbar = false, withAppBar = true)
    }

    fun showCategoryPicker() {
        val fragment = CategoryPickerFragment.newInstance()
        loadFragment(fragment, withTopbar = false, withBottomBar = false)
    }

    private fun showDatePickerBar() {
        container_topbar.showDelayed()
    }

    private fun showBottomNav() {
        main_navigation.showDelayed()
    }

    private fun hideBottomNav() {
        main_navigation.hideDelayed()
    }

    private fun hideDatePickerBar() {
        container_topbar.hideDelayed()
    }
}
