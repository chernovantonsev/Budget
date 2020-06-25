package ru.antonc.budget.ui.categories

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import ru.antonc.budget.R
import ru.antonc.budget.databinding.FragmentListOfCategoriesBinding
import ru.antonc.budget.di.Injectable
import ru.antonc.budget.ui.base.BaseFragment
import ru.antonc.budget.util.autoCleared

class CategoriesFragment : BaseFragment(), Injectable {

    private lateinit var viewModel: CategoriesViewModel

    var binding by autoCleared<FragmentListOfCategoriesBinding>()

    private val params by navArgs<CategoriesFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(CategoriesViewModel::class.java)
        viewModel.setTransactionInfo(params.transactionId, params.transactionType)

        binding = FragmentListOfCategoriesBinding.inflate(inflater, container, false)
            .apply {
                viewModel = this@CategoriesFragment.viewModel
                lifecycleOwner = this@CategoriesFragment
            }

        val adapter = CategoriesAdapter { category ->
            viewModel.selectCategory(category, params.transactionId)
            findNavController().navigateUp()
        }
        binding.categoriesList.adapter = adapter

        viewModel.categoriesList.observe(viewLifecycleOwner) { categories ->
            adapter.submitList(categories)
        }
        viewModel.selectedCategoryId.observe(viewLifecycleOwner) {selectedCategoryId ->
            adapter.setSelectedCategoryId(selectedCategoryId)
        }

        binding.buttonAddCategory.setOnClickListener { createNewCategory() }

        return binding.root
    }

    private fun createNewCategory() {
        viewModel.setCategoryName("")

        MaterialDialog(requireContext()).show {
            title(R.string.create_new_category)
            input(
                inputType = InputType.TYPE_CLASS_TEXT,
                hintRes = R.string.hint_category_name,
                waitForPositiveButton = false,
                callback = { _, charSequence -> viewModel.setCategoryName(charSequence.toString()) }
            )

            positiveButton(android.R.string.ok) {
                viewModel.saveCategory()
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