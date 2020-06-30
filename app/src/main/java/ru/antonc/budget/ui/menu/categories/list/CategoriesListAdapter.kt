package ru.antonc.budget.ui.menu.categories.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.antonc.budget.R
import ru.antonc.budget.data.entities.Category
import ru.antonc.budget.databinding.ListItemCategoryBinding

class CategoriesListAdapter(private val itemClickListener: ((Category) -> Unit)?) :
    ListAdapter<Category, CategoriesListAdapter.ViewHolder>(
        CategoriesDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_category, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemClickListener, getItem(position))
    }

    class ViewHolder(
        private val binding: ListItemCategoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: ((Category) -> Unit)?, category: Category) {
            with(binding) {
                clickListener = View.OnClickListener {
                    listener?.invoke(category)
                }
                this.category = category
                executePendingBindings()
            }
        }
    }
}

private class CategoriesDiffCallback : DiffUtil.ItemCallback<Category>() {

    override fun areItemsTheSame(
        oldItem: Category,
        newItem: Category
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Category,
        newItem: Category
    ): Boolean {
        return oldItem == newItem
    }
}