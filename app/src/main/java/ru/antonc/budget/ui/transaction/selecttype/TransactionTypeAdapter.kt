package ru.antonc.budget.ui.transaction.selecttype

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.antonc.budget.R
import ru.antonc.budget.data.entities.TransactionType
import ru.antonc.budget.databinding.ListItemTransactionTypeBinding


class TransactionTypeAdapter(private val itemClickListener: ((TransactionType) -> Unit)?) :
    ListAdapter<TransactionType, TransactionTypeAdapter.ViewHolder>(
        TransactionTypeDiffCallback()
    ) {

    init {
        submitList(TransactionType.values().toList())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_transaction_type, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), itemClickListener)
    }

    class ViewHolder(
        private val binding: ListItemTransactionTypeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(transactionType: TransactionType, clickListener: ((TransactionType) -> Unit)?) {
            with(binding) {
                this.clickListener = View.OnClickListener {
                    clickListener?.invoke(transactionType)
                }
                this.transactionType = transactionType
                executePendingBindings()
            }
        }
    }
}

private class TransactionTypeDiffCallback : DiffUtil.ItemCallback<TransactionType>() {

    override fun areItemsTheSame(
        oldItem: TransactionType,
        newItem: TransactionType
    ): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(
        oldItem: TransactionType,
        newItem: TransactionType
    ): Boolean {
        return oldItem == newItem
    }
}