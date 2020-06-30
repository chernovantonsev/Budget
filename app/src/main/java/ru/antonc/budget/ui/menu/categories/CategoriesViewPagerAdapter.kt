package ru.antonc.budget.ui.menu.categories

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.antonc.budget.data.entities.TransactionType
import ru.antonc.budget.ui.menu.categories.list.CategoriesListFragment

class CategoriesViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    enum class CategoriesPage(val title: String, val transactionType: TransactionType) {
        EXPENSES("Расходы", TransactionType.EXPENSE), INCOMES("Доходы", TransactionType.INCOME)
    }

    private val pages = ArrayList<CategoriesPage>()

    init {
        pages.addAll(CategoriesPage.values())
    }

    override fun getItemCount() = CategoriesPage.values().size


    override fun createFragment(position: Int): Fragment {
        return CategoriesListFragment().apply {
            arguments = bundleOf(
                CategoriesListFragment.TRANSACTION_TYPE to pages[position].transactionType.type
            )
        }
    }
}

