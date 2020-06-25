package ru.antonc.budget.ui.menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.antonc.budget.R
import ru.antonc.budget.data.entities.MenuItem
import ru.antonc.budget.databinding.ListItemMenuBinding
import ru.antonc.budget.util.extenstions.getString

class MenuAdapter(private val itemClickListener: ((MenuItem) -> Unit)?) :
    ListAdapter<MenuItem, MenuAdapter.ViewHolder>(MenuItemsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_menu, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemClickListener, getItem(position))
    }

    class ViewHolder(
        private val binding: ListItemMenuBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: ((MenuItem) -> Unit)?, menuItem: MenuItem) {
            with(binding) {
                clickListener = View.OnClickListener {
                    listener?.invoke(menuItem)
                }
                titleValue = binding.root.getString(menuItem.title)
                iconValue = menuItem.icon
                executePendingBindings()
            }
        }
    }
}

private class MenuItemsDiffCallback : DiffUtil.ItemCallback<MenuItem>() {

    override fun areItemsTheSame(
        oldItem: MenuItem,
        newItem: MenuItem
    ): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(
        oldItem: MenuItem,
        newItem: MenuItem
    ): Boolean {
        return oldItem == newItem
    }
}