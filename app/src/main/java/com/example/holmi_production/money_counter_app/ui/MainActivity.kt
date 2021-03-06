package com.example.holmi_production.money_counter_app.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.onNavDestinationSelected
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.extensions.simpleFormat
import com.example.holmi_production.money_counter_app.model.entity.FilterPeriodEntity
import com.example.holmi_production.money_counter_app.model.enums.PeriodType
import com.example.holmi_production.money_counter_app.storage.db.AppPreference
import com.example.holmi_production.money_counter_app.ui.dialogs.BottomDialog
import com.example.holmi_production.money_counter_app.ui.dialogs.DateSelectorDialog
import com.example.holmi_production.money_counter_app.ui.viewModels.MainViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.android.synthetic.main.activity_main.*
import org.joda.time.DateTime
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    lateinit var appBarConfiguration: AppBarConfiguration

    private val viewModel: MainViewModel by viewModel()

    val navController by lazy {
        val host = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        host.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initNavController()
        initializeNavigation()
        initView()
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if (destination.id == R.id.onBoardingFragment) {
                backLayout.visibility = View.GONE
            } else {
                backLayout.visibility = View.VISIBLE
            }
        }
        viewModel.activePeriod.observe(this, Observer(::updatePeriod))
    }

    private fun updatePeriod(filterPeriodEntity: FilterPeriodEntity?) {
        period.text =
            "${filterPeriodEntity?.from?.simpleFormat()} - ${filterPeriodEntity?.to?.simpleFormat()}"
    }

    private fun initView() {
        period.setOnClickListener {

            val dialog = DateSelectorDialog() { type ->
                val selected = PeriodType.values()[type]
                if (selected == PeriodType.CUSTOM) {
                    val dialog = MaterialDatePicker.Builder
                        .dateRangePicker()
                        .setTheme(R.style.AppTheme_RangeDatePicker)
                        .build()

                    dialog.show(supportFragmentManager, "DATE_PICKER")
                    dialog.addOnPositiveButtonClickListener {
                        val to = DateTime(it.first)
                        val from = DateTime(it.second)
                        viewModel.updateSelectedPeriod(selected, to, from)
                    }
                } else {
                    viewModel.updateSelectedPeriod(selected)
                }
            }
            dialog.arguments =
                bundleOf(
                    BottomDialog.ARGS_DIALOG_TYPE to DateSelectorDialog.TYPE_TOOLBAR_PERIOD,
                    BottomDialog.ARGS_SELECTED_ID_INT to viewModel.activePeriod.value!!.type.ordinal
                )
            dialog.show(supportFragmentManager, "DATE_PICKER")
        }
    }

    override fun onBackPressed() {
        if (!navController.popBackStack()) {
            super.onBackPressed()
        }
    }

    private fun initNavController() {
        val mainGraph = navController.navInflater.inflate(R.navigation.main_graph)
        val appPrefs: AppPreference = get()
        if (appPrefs.isOnboardingCompleted.not()) {
            val onBoardingGraph = navController.navInflater.inflate(R.navigation.onboarding_graph)
            mainGraph.addAll(onBoardingGraph)
            mainGraph.startDestination = R.id.onBoardingFragment
        } else {
            mainGraph.startDestination = R.id.dashboardFragment
        }
        navController.graph = mainGraph
    }

    private fun initializeNavigation() {
        NavigationUI.setupWithNavController(navigationView, navController)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController(R.id.navHostFragment))
                || super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.navHostFragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
