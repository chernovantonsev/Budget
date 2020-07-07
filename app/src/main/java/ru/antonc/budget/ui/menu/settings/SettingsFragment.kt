package ru.antonc.budget.ui.menu.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import ru.antonc.budget.R
import ru.antonc.budget.databinding.FragmentSettingsBinding
import ru.antonc.budget.di.Injectable
import ru.antonc.budget.ui.base.BaseFragment
import ru.antonc.budget.util.autoCleared

class SettingsFragment : BaseFragment(), Injectable {

    var binding by autoCleared<FragmentSettingsBinding>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSettingsBinding.inflate(inflater, container, false)
            .apply {
                lifecycleOwner = this@SettingsFragment
            }

        childFragmentManager
            .beginTransaction()
            .add(R.id.preferences_container, PreferencesFragment())
            .commit()

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.toolbar.apply {
            setNavigationOnClickListener { findNavController().navigateUp() }
        }
    }
}