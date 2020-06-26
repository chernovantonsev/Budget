package ru.antonc.budget.ui.statistics.piechart

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF
import ru.antonc.budget.data.entities.LegendItem
import ru.antonc.budget.databinding.FragmentPieChartStatisticsBinding
import ru.antonc.budget.di.Injectable
import ru.antonc.budget.ui.base.BaseFragment

class PieChartStatisticsFragment : BaseFragment(false), Injectable {

    companion object {
        const val DATA_TYPE = "type"
    }

    private lateinit var viewModel: PieChartStatisticsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(PieChartStatisticsViewModel::class.java)

        val binding = FragmentPieChartStatisticsBinding.inflate(inflater, container, false)
            .apply {
                viewModel = this@PieChartStatisticsFragment.viewModel
                lifecycleOwner = this@PieChartStatisticsFragment
            }

        arguments?.let {
            it.getString(DATA_TYPE)?.let { typeKey ->
                viewModel.setDataTypeName(typeKey)
            }
        }

        val legendAdapter = LegendStatisticsAdapter()
        binding.listLegend.adapter = legendAdapter

        viewModel.data.observe(viewLifecycleOwner) { data ->
            legendAdapter.submitList(data.itemsLegend)
            initChart(data.itemsLegend, binding.chart, data.totalSum)
        }

        return binding.root
    }

    private fun initChart(
        itemsChart: List<LegendItem>,
        chart: PieChart,
        centerText: String = ""
    ) {
        val dataSet = PieDataSet(itemsChart.map { it.convertToPieEntry() }, "")

        dataSet.setDrawIcons(false)

        dataSet.sliceSpace = 3f
        dataSet.iconsOffset = MPPointF(0F, 40f)
        dataSet.selectionShift = 5f


        // add a lot of colors
        val colors = java.util.ArrayList<Int>()

        for (c in ColorTemplate.VORDIPLOM_COLORS) colors.add(c)
        for (c in ColorTemplate.JOYFUL_COLORS) colors.add(c)
        for (c in ColorTemplate.COLORFUL_COLORS) colors.add(c)
        for (c in ColorTemplate.LIBERTY_COLORS) colors.add(c)
        for (c in ColorTemplate.PASTEL_COLORS) colors.add(c)

        colors.add(ColorTemplate.getHoloBlue())

        dataSet.colors = colors

        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter(chart))
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.GRAY)
        chart.setEntryLabelColor(Color.GRAY)
        chart.setUsePercentValues(true)
        chart.description.isEnabled = false
        chart.setExtraOffsets(5f, 10f, 5f, 5f)

        chart.dragDecelerationFrictionCoef = 0.95f

        chart.setDrawCenterText(true)
        chart.centerText =
            if (itemsChart.isNotEmpty()) centerText else "Нет данных для отображения"
        chart.setCenterTextColor(Color.GRAY)
        chart.setCenterTextSize(17f)

        chart.isDrawHoleEnabled = true
        chart.setHoleColor(Color.WHITE)

        chart.setTransparentCircleColor(Color.WHITE)
        chart.setTransparentCircleAlpha(110)

        chart.holeRadius = 58f
        chart.transparentCircleRadius = 61f


        chart.rotationAngle = 0f
        chart.isRotationEnabled = true
        chart.isHighlightPerTapEnabled = true
        chart.data = data
        chart.highlightValues(null)
        chart.legend.isEnabled = false

        chart.animateY(700, Easing.EaseInOutQuad)

        chart.invalidate()
    }

}