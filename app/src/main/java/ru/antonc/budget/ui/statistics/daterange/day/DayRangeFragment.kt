package ru.antonc.budget.ui.statistics.daterange.day

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import ru.antonc.budget.databinding.FragmentDayRangeBinding
import ru.antonc.budget.di.Injectable
import ru.antonc.budget.ui.base.BaseFragment
import ru.antonc.budget.util.autoCleared
import java.util.*

class DayRangeFragment : BaseFragment(false), Injectable {

    private lateinit var viewModel: DayRangeViewModel

    var binding by autoCleared<FragmentDayRangeBinding>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(DayRangeViewModel::class.java)

        binding = FragmentDayRangeBinding.inflate(inflater, container, false)
            .apply {
                viewModel = this@DayRangeFragment.viewModel
                lifecycleOwner = this@DayRangeFragment
            }


        viewModel.dateInitValue.observe(viewLifecycleOwner) { date ->
            if (date > 0)
                binding.calendar.date = date
        }

        binding.calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            Calendar.getInstance().apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, month)
                set(Calendar.DAY_OF_MONTH, dayOfMonth)
            }.let {
                viewModel.setDate(it.timeInMillis)
            }
        }


        return binding.root
    }
}