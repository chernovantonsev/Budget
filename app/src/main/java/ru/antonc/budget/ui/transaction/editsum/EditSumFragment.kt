package ru.antonc.budget.ui.transaction.editsum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import ru.antonc.budget.databinding.FragmentEditSumBinding
import ru.antonc.budget.di.Injectable
import ru.antonc.budget.ui.base.BaseFragment
import ru.antonc.budget.util.autoCleared

class EditSumFragment : BaseFragment(), Injectable {

    private lateinit var viewModel: EditSumViewModel

    var binding by autoCleared<FragmentEditSumBinding>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(EditSumViewModel::class.java)

        binding = FragmentEditSumBinding.inflate(inflater, container, false)
            .apply {
                lifecycleOwner = this@EditSumFragment
            }


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.toolbar.apply {
            setNavigationOnClickListener { requireActivity().onBackPressed() }
        }
    }
}