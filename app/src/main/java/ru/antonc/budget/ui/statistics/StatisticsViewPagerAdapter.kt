package ru.antonc.budget.ui.statistics

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF
import ru.antonc.budget.R
import ru.antonc.budget.data.entities.StatisticsItem
import ru.antonc.budget.data.entities.StatisticsPage
import ru.antonc.budget.databinding.ItemPageStatisticsBinding

class StatisticsViewPagerAdapter :
    ListAdapter<StatisticsPage, RecyclerView.ViewHolder>(StatisticsPageDiffCallback()) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as StatisticsPagesViewHolder).bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return StatisticsPagesViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_page_statistics, parent, false
            )
        )
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).type.ordinal
    }

    class StatisticsPagesViewHolder(
        private val binding: ItemPageStatisticsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(statisticsPage: StatisticsPage) {
            with(binding) {
                with(CategoryStatisticsAdapter()) {
                    listCategories.adapter = this
                    submitList(statisticsPage.itemsLegend)
                }
                initChart(statisticsPage.itemsLegend, chart, statisticsPage.totalSum)

                executePendingBindings()
            }
        }

        private fun initChart(
            itemsChart: List<StatisticsItem>,
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
}


private class StatisticsPageDiffCallback : DiffUtil.ItemCallback<StatisticsPage>() {
    override fun areItemsTheSame(
        oldItem: StatisticsPage,
        newItem: StatisticsPage
    ): Boolean {
        return oldItem.type == newItem.type
    }

    override fun areContentsTheSame(
        oldItem: StatisticsPage,
        newItem: StatisticsPage
    ): Boolean {
        return oldItem == newItem

    }
}