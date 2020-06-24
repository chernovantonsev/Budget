package ru.antonc.budget.ui.statistics

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF
import kotlinx.android.synthetic.main.item_page_statistics.view.*
import ru.antonc.budget.R
import ru.antonc.budget.data.entities.StatisticsItem
import ru.antonc.budget.data.entities.StatisticsPage

class ViewPagerAdapter : RecyclerView.Adapter<PagerVH>() {

    private var pages: ArrayList<StatisticsPage> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerVH =
        PagerVH(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_page_statistics, parent, false)
        )

    override fun getItemCount(): Int {
        return pages.size
    }

    override fun onBindViewHolder(holder: PagerVH, position: Int): Unit = holder.itemView.run {
        pages[position].also { (_, totalSum, items) ->
            with(CategoryStatisticsAdapter()) {
                list_categories.adapter = this
                submitList(items)
            }

            initChart(items, chart, totalSum)
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

        chart.setDrawCenterText(centerText.isNotEmpty())
        chart.centerText = centerText
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

    fun setPagesInfo(newItems: List<StatisticsPage>) {
        pages.clear()
        pages.addAll(newItems)
        notifyDataSetChanged()
    }
}

class PagerVH(itemView: View) : RecyclerView.ViewHolder(itemView)