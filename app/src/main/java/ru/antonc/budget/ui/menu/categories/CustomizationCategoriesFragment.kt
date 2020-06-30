package ru.antonc.budget.ui.menu.categories

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.google.android.material.tabs.TabLayoutMediator
import ru.antonc.budget.R
import ru.antonc.budget.databinding.FragmentCustomizationCategoriesBinding
import ru.antonc.budget.di.Injectable
import ru.antonc.budget.ui.base.BaseFragment
import ru.antonc.budget.util.autoCleared

class CustomizationCategoriesFragment : BaseFragment(), Injectable {

    private lateinit var viewModel: CustomizationCategoriesViewModel

    var binding by autoCleared<FragmentCustomizationCategoriesBinding>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProviders.of(this, viewModelFactory)
                .get(CustomizationCategoriesViewModel::class.java)

        binding = FragmentCustomizationCategoriesBinding.inflate(inflater, container, false)
            .apply {
                lifecycleOwner = this@CustomizationCategoriesFragment
            }

        val pagerAdapter = CategoriesViewPagerAdapter(childFragmentManager, lifecycle)
        binding.vpInfo.adapter = pagerAdapter

        CategoriesViewPagerAdapter.CategoriesPage.values().let { pages ->
            binding.tabs.removeAllTabs()

            TabLayoutMediator(binding.tabs, binding.vpInfo,
                TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                    pages[position].also { page ->
                        tab.text = page.title
                        tab.tag = page.title
                    }
                }).attach()
        }

        binding.buttonAddCategory.setOnClickListener {
            createNewCategory(
                CategoriesViewPagerAdapter.CategoriesPage.values()[binding.vpInfo.currentItem]
            )
        }

        return binding.root
    }

    private fun createNewCategory(currentPage: CategoriesViewPagerAdapter.CategoriesPage) {
        viewModel.setCategoryName()

        MaterialDialog(requireContext()).show {
            title(R.string.create_new_category)
            input(
                inputType = InputType.TYPE_CLASS_TEXT,
                hintRes = R.string.hint_category_name,
                waitForPositiveButton = false,
                callback = { _, charSequence -> viewModel.setCategoryName(charSequence.toString()) }
            )

            positiveButton(android.R.string.ok) {
                viewModel.saveCategory(currentPage.transactionType)
                dismiss()
            }
            negativeButton(android.R.string.cancel) {
                dismiss()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.toolbar.apply {
            setNavigationOnClickListener { requireActivity().onBackPressed() }
        }
    }

}