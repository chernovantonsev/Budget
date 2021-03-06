package ru.antonc.budget.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import ru.antonc.budget.databinding.FragmentMenuBinding
import ru.antonc.budget.di.Injectable
import ru.antonc.budget.ui.base.BaseFragment

class MenuFragment : BaseFragment(), Injectable {

    private lateinit var viewModel: MenuViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(MenuViewModel::class.java)

        val binding = FragmentMenuBinding.inflate(inflater, container, false)
            .apply {
                viewModel = this@MenuFragment.viewModel
                lifecycleOwner = this@MenuFragment
            }

        val adapter = MenuAdapter {
            when (it) {
                MenuItem.ACCOUNTS -> {
                    findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToAccountsFragment())
                }
                MenuItem.CATEGORIES -> {
                    findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToCustomizationCategoriesFragment())
                }
                MenuItem.SETTINGS -> {
                    findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToSettingsFragment())
                }
            }
        }
        binding.menuItemsList.adapter = adapter
        adapter.submitList(MenuItem.values().toList())

        return binding.root
    }

}