package ru.antonc.budget.ui.overview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.antonc.budget.R
import ru.antonc.budget.data.entities.Account
import ru.antonc.budget.databinding.ItemAccountBinding

class AccountsAdapter(private val itemClickListener: ((Account) -> Unit)?) :
    ListAdapter<Account, AccountsAdapter.ViewHolder>(AccountsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_account, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemClickListener, getItem(position))
    }

    class ViewHolder(
        private val binding: ItemAccountBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: ((Account) -> Unit)?, account: Account) {
            with(binding) {
                clickListener = View.OnClickListener {
                    listener?.invoke(account)
                }
                this.account = account
                executePendingBindings()
            }
        }
    }
}

private class AccountsDiffCallback : DiffUtil.ItemCallback<Account>() {

    override fun areItemsTheSame(
        oldItem: Account,
        newItem: Account
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Account,
        newItem: Account
    ): Boolean {
        return oldItem == newItem
    }
}