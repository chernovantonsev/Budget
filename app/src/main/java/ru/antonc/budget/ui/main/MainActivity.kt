package ru.antonc.budget.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import ru.antonc.budget.R
import ru.antonc.budget.databinding.ActivityMainBinding
import ru.antonc.budget.ui.base.OnBackPressedListener
import ru.antonc.budget.util.KeyboardEventListener
import ru.antonc.budget.util.extenstions.combineWith
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    private lateinit var navController: NavController

    private lateinit var navigation: BottomNavigationView

    var onBackPressedListener: ArrayList<OnBackPressedListener?> = ArrayList()

    private val isNavigationNotRequired = MutableLiveData<Boolean>()
    private val isKeyBoardOpened = MutableLiveData<Boolean>()

    private val isNavigationGone: LiveData<Boolean> =
        isNavigationNotRequired.combineWith(isKeyBoardOpened) { isNavigationNotRequired, isKeyBoardOpened ->
            if (isNavigationNotRequired == null || isKeyBoardOpened == null)
                return@combineWith true

            if (isNavigationNotRequired) return@combineWith true
            else isKeyBoardOpened
        }

    private val fragmentsWithoutBottomNavigationView: ArrayList<Int> = ArrayList()

    private var lastStateNavigation: Boolean? = null

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

        fragmentsWithoutBottomNavigationView.apply {
            add(R.id.transactionFragment)
            add(R.id.transactionTypeFragment)
            add(R.id.selectAccountFragment)
            add(R.id.editSumFragment)
            add(R.id.categoriesFragment)
            add(R.id.settingsFragment)
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

            isNavigationNotRequired.value =
                fragmentsWithoutBottomNavigationView.contains(destination.id)
        }

        isNavigationGone.observe(this, Observer { isGone ->
            if (isGone == lastStateNavigation)
                return@Observer

            binding.buttonAddTransaction.visibility = if (isGone) View.GONE else View.VISIBLE

            navigation.alpha = if (isGone) 1F else 0F
            navigation.visibility = if (isGone) View.GONE else View.VISIBLE

            navigation.animate()
                .alpha(if (isGone) 0F else 1F)
                .setDuration(100)
                .start()

            lastStateNavigation = isGone
        })


        binding.buttonAddTransaction.setOnClickListener {
            navController.navigate(
                R.id.transactionTypeFragment,
                null,
                NavOptions.Builder()
                    .setEnterAnim(R.anim.slide_in_right)
                    .setExitAnim(R.anim.slide_out_left)
                    .setPopEnterAnim(R.anim.slide_in_left)
                    .setPopExitAnim(R.anim.slide_out_right)
                    .build()
            )

        }
    }

    override fun onResume() {
        super.onResume()
        KeyboardEventListener(this) { isOpen ->
            isKeyBoardOpened.value = isOpen
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