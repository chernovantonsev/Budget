package ru.antonc.budget.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import ru.antonc.budget.R
import ru.antonc.budget.databinding.ActivityMainBinding
import ru.antonc.budget.ui.base.OnBackPressedListener
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    private lateinit var navController: NavController

    private lateinit var navigation: BottomNavigationView

    var onBackPressedListener: ArrayList<OnBackPressedListener?> = ArrayList()


    private val navigationToFragments: MutableMap<Int, Int> = mutableMapOf()
    private val fragmentsToNavigation: MutableMap<Int, Int> = mutableMapOf()


    init {
        navigationToFragments[R.id.navigation_overview] = R.id.overviewFragment
        navigationToFragments[R.id.navigation_transactions] = R.id.transactionsListFragment
        navigationToFragments[R.id.navigation_statistics] = R.id.statisticsFragment
        navigationToFragments[R.id.navigation_menu] = R.id.menuFragment

        navigationToFragments.forEach { (key, value) ->
            fragmentsToNavigation[value] = key
        }
    }

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            if (navigation.selectedItemId == item.itemId)
                return@OnNavigationItemSelectedListener true

            navigationToFragments[item.itemId]?.let { fragmentId ->
                navController.navigate(fragmentId)
                return@OnNavigationItemSelectedListener true
            }

            return@OnNavigationItemSelectedListener false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(
            this,
            R.layout.activity_main
        )

        navigation = binding.navigation
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            fragmentsToNavigation[destination.id]?.let {
                navigation.setOnNavigationItemSelectedListener(null)
                navigation.selectedItemId = it
                navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

            }
        }
    }

    override fun onBackPressed() {
        when {
            onBackPressedListener.firstOrNull()?.onBackPressed() == true -> return
            navController.popBackStack() -> return
            else -> finish()
        }
    }

    fun setSelectedItemNavigation(id: Int) {
        navigation.menu.findItem(id)?.let {
            navigation.selectedItemId = id
        }
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

}