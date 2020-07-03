package ru.antonc.budget.ui.transaction.selectaccount

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.antonc.budget.R
import ru.antonc.budget.data.entities.Account
import ru.antonc.budget.databinding.ListItemAccountBinding


class SelectAccountAdapter(private val itemClickListener: ((Account) -> Unit)?) :
    ListAdapter<Account, SelectAccountAdapter.ViewHolder>(
        AccountDiffCallback()
    ) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_account, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), itemClickListener)
    }

    class ViewHolder(
        private val binding: ListItemAccountBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(account: Account, clickListener: ((Account) -> Unit)?) {
            with(binding) {
                this.clickListener = View.OnClickListener {
                    clickListener?.invoke(account)
                }
                this.account = account
                executePendingBindings()
            }
        }
    }
}

private class AccountDiffCallback : DiffUtil.ItemCallback<Account>() {

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