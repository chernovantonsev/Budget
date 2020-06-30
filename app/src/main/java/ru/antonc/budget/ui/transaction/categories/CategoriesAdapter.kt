package ru.antonc.budget.ui.transaction.categories

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

class CategoriesAdapter(private val itemClickListener: ((Category) -> Unit)?) :
    ListAdapter<Category, CategoriesAdapter.ViewHolder>(CategoriesDiffCallback()) {

    private var selectedCategoryId = -1L

    fun setSelectedCategoryId(id: Long) {
        selectedCategoryId = id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_category, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemClickListener, getItem(position), selectedCategoryId)
    }

    class ViewHolder(
        private val binding: ListItemCategoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: ((Category) -> Unit)?, category: Category, selectedCategoryId: Long) {
            with(binding) {
                clickListener = View.OnClickListener {
                    listener?.invoke(category)
                }
                isSelected = category.id == selectedCategoryId
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