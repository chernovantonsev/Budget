package ru.antonc.budget.ui.statistics.piechart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.antonc.budget.R
import ru.antonc.budget.data.entities.LegendItem
import ru.antonc.budget.databinding.ListItemLegendStatisticsBinding

class LegendStatisticsAdapter :
    ListAdapter<LegendItem, LegendStatisticsAdapter.ViewHolder>(
        LegendStatisticsDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_legend_statistics, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ListItemLegendStatisticsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(legendItem: LegendItem) {
            with(binding) {
                this.legendItem = legendItem
                executePendingBindings()
            }
        }
    }
}

private class LegendStatisticsDiffCallback : DiffUtil.ItemCallback<LegendItem>() {

    override fun areItemsTheSame(
        oldItem: LegendItem,
        newItem: LegendItem
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: LegendItem,
        newItem: LegendItem
    ): Boolean {
        return oldItem == newItem
    }
}