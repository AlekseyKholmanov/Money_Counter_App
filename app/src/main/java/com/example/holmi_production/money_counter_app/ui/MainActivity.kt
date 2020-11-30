package com.example.holmi_production.money_counter_app.ui

import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.extensions.simpleFormat
import com.example.holmi_production.money_counter_app.main.AndroidBug5497Workaround
import com.example.holmi_production.money_counter_app.main.ToolbarOwner
import com.example.holmi_production.money_counter_app.model.entity.FilterPeriodEntity
import com.example.holmi_production.money_counter_app.model.enums.PeriodType
import com.example.holmi_production.money_counter_app.ui.dialogs.BottomDialog
import com.example.holmi_production.money_counter_app.ui.dialogs.DateSelectorDialog
import com.example.holmi_production.money_counter_app.ui.viewModels.MainViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.android.synthetic.main.activity_main.*
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener
import org.joda.time.DateTime
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity(), ToolbarOwner {


    private val viewModel: MainViewModel by viewModel()

    private val navController by lazy {
        val host = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        host.navController
    }

    override val toolbar: Toolbar by lazy { findViewById(R.id.toolbar) }

    private val topLevelDestinations: Set<Int>
        get() = setOf(
            R.id.navigationDashboard,
            R.id.limitsFragment,
            R.id.transactionFragment,
            R.id.settingsFragment
        )

    private val appBarConfiguration: AppBarConfiguration by lazy(LazyThreadSafetyMode.NONE) {
        AppBarConfiguration(topLevelDestinations, null) { false }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AndroidBug5497Workaround.assistActivity(this)
        initNavController()
        initializeNavigation()
        initView()
        attachKeyboardListener()
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            period.visibility =
                if (topLevelDestinations.contains(destination.id) || (topLevelDestinations.contains(
                        navController.previousBackStackEntry?.destination?.id
                    ) && (destination.id == R.id.navigationBottomDialog || destination.id == R.id.navigationAddTransactionKeyboard))
                ) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
        }
        viewModel.activePeriod.observe(this, Observer(::updatePeriod))
    }

    override fun onBackPressed() {
        if (!navController.popBackStack()) {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController(R.id.navHostFragment))
                || super.onOptionsItemSelected(item)
    }

    private fun updatePeriod(filterPeriodEntity: FilterPeriodEntity?) {
        val text =
            if (filterPeriodEntity?.to?.toLocalDate() == filterPeriodEntity?.from?.toLocalDate()) {
                filterPeriodEntity?.to?.simpleFormat()
            } else {
                "${filterPeriodEntity?.from?.simpleFormat()} - ${filterPeriodEntity?.to?.simpleFormat()}"
            }
        period.text = text

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

    private fun attachKeyboardListener() {
        KeyboardVisibilityEvent.setEventListener(this, object : KeyboardVisibilityEventListener {
            override fun onVisibilityChanged(isOpen: Boolean) {
                if (isOpen) {
                    bottomNavigationView.visibility = View.GONE
                } else {
                    bottomNavigationView.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun initNavController() {
        val mainGraph = navController.navInflater.inflate(R.navigation.main_graph)
        navController.graph = mainGraph
    }

    private fun initializeNavigation() {
        toolbar.setNavigationIcon(R.drawable.ic_custom_back)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setupWithNavController(navController, appBarConfiguration)
        bottomNavigationView.setupWithNavController(navController)
    }

}
