package ru.antonc.budget.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import ru.antonc.budget.data.entities.MenuItem
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

        }
        binding.menuItemsList.adapter = adapter
        adapter.submitList(MenuItem.values().toList())

        return binding.root
    }

}