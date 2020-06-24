package ru.antonc.budget.ui.statistics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.antonc.budget.R
import ru.antonc.budget.data.entities.StatisticsItem
import ru.antonc.budget.databinding.ListItemCategoryStatisticsBinding

class CategoryStatisticsAdapter :
    ListAdapter<StatisticsItem, CategoryStatisticsAdapter.ViewHolder>(
        CategoryStatisticsDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_category_statistics, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ListItemCategoryStatisticsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(statisticsItem: StatisticsItem) {
            with(binding) {
                category = statisticsItem
                executePendingBindings()
            }
        }
    }
}

private class CategoryStatisticsDiffCallback : DiffUtil.ItemCallback<StatisticsItem>() {

    override fun areItemsTheSame(
        oldItem: StatisticsItem,
        newItem: StatisticsItem
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: StatisticsItem,
        newItem: StatisticsItem
    ): Boolean {
        return oldItem == newItem
    }
}