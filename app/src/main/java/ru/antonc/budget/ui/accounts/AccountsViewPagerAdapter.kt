package ru.antonc.budget.ui.accounts

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.antonc.budget.ui.accounts.list.AccountsListFragment
import ru.antonc.budget.ui.accounts.summary.SummaryAccountsFragment

class AccountsViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val pages = ArrayList<AccountsPage>()

    init {
        pages.addAll(AccountsPage.values())
    }

    override fun getItemCount() = AccountsPage.values().size

    override fun createFragment(position: Int): Fragment {
        return when (pages[position]) {
            AccountsPage.LIST -> {
                AccountsListFragment().apply {
                    arguments = bundleOf(
                        AccountsListFragment.IS_PAGE to true
                    )
                }
            }
            AccountsPage.SUMMARY -> {
                SummaryAccountsFragment()
            }
        }
    }
}

