package ru.antonc.budget.ui.statistics.daterange.any

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import ru.antonc.budget.databinding.FragmentAnyRangeBinding
import ru.antonc.budget.di.Injectable
import ru.antonc.budget.ui.base.BaseFragment
import ru.antonc.budget.ui.date.DatePickerFragment
import ru.antonc.budget.util.autoCleared

class AnyRangeFragment : BaseFragment(false), Injectable {

    private lateinit var viewModel: AnyRangeViewModel

    var binding by autoCleared<FragmentAnyRangeBinding>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(AnyRangeViewModel::class.java)

        binding = FragmentAnyRangeBinding.inflate(inflater, container, false)
            .apply {
                viewModel = this@AnyRangeFragment.viewModel
                lifecycleOwner = this@AnyRangeFragment
            }



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.datePickerEvent.observe(viewLifecycleOwner) { datePickEvent ->
            datePickEvent.getContentIfNotHandled()?.let { (initDate, kind) ->
                DatePickerFragment.createDatePickerDialog(
                    initDate = initDate,
                    onDateSelected = { date ->
                        viewModel.setDate(date, kind)
                    })
                    .let { datePickerFragment ->
                        datePickerFragment.show(
                            childFragmentManager,
                            datePickerFragment.javaClass.name
                        )
                    }
            }
        }
    }
}