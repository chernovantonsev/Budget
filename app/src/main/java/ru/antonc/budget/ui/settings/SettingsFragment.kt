package ru.antonc.budget.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import ru.antonc.budget.databinding.FragmentSettingsBinding
import ru.antonc.budget.di.Injectable
import ru.antonc.budget.ui.base.BaseFragment
import ru.antonc.budget.util.autoCleared

class SettingsFragment : BaseFragment(), Injectable {

    private lateinit var viewModel: SettingsViewModel

    var binding by autoCleared<FragmentSettingsBinding>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(SettingsViewModel::class.java)

        binding = FragmentSettingsBinding.inflate(inflater, container, false)
            .apply {
                viewModel = this@SettingsFragment.viewModel
                lifecycleOwner = this@SettingsFragment
            }
        

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.toolbar.apply {
            setNavigationOnClickListener { findNavController().navigateUp() }
        }
    }
}