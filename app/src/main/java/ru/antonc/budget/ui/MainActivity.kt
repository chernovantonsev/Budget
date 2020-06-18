package ru.antonc.budget.ui

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

    private var onBackPressedListener: ArrayList<OnBackPressedListener?> = ArrayList()

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            if (navigation.selectedItemId == item.itemId)
                return@OnNavigationItemSelectedListener true

            when (item.itemId) {
                R.id.navigation_overview -> {
                    navController.navigate(R.id.overviewFragment)
                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigation_transactions -> {
                    navController.navigate(R.id.transactionsListFragment)
                    return@OnNavigationItemSelectedListener true
                }
                else -> {
                    return@OnNavigationItemSelectedListener true
                }
            }
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
    }

    fun setOnBackPressedListener(onBackPressedListener: OnBackPressedListener?) {
        this.onBackPressedListener.add(onBackPressedListener)
    }

    fun removeOnBakPressedListener(onBackPressedListener: OnBackPressedListener?) {
        this.onBackPressedListener.remove(onBackPressedListener)
    }

    override fun onBackPressed() {
        when {
            onBackPressedListener.firstOrNull()?.onBackPressed() == true -> return
            navController.popBackStack() -> return
            else -> finish()
        }
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

}