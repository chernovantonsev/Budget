package ru.antonc.budget.ui.accounts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import ru.antonc.budget.databinding.FragmentAccountsBinding
import ru.antonc.budget.di.Injectable
import ru.antonc.budget.ui.base.BaseFragment
import ru.antonc.budget.util.autoCleared

class AccountsFragment : BaseFragment(), Injectable {

    var binding by autoCleared<FragmentAccountsBinding>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountsBinding.inflate(inflater, container, false)
            .apply {
                lifecycleOwner = this@AccountsFragment
            }

        val pagerAdapter = AccountsViewPagerAdapter(childFragmentManager, lifecycle)
        binding.vpInfo.adapter = pagerAdapter

        AccountsViewPagerAdapter.AccountsPage.values().let { pages ->
            binding.tlTypes.removeAllTabs()

            TabLayoutMediator(binding.tlTypes, binding.vpInfo,
                TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                    pages[position].also { page ->
                        tab.text = page.title
                        tab.tag = page.title
                    }
                }).attach()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.toolbar.apply {
            setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
    }
}