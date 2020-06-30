package ru.antonc.budget.ui.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.antonc.budget.R
import ru.antonc.budget.data.entities.FullTransaction
import ru.antonc.budget.databinding.ListItemTransactionBinding
import ru.antonc.budget.databinding.ListItemTransactionDateBinding
import ru.antonc.budget.databinding.ListItemTransactionShortBinding
import ru.antonc.budget.ui.transactions.TransactionListItem.Companion.TYPE_DATE

class TransactionsListAdapter :
    ListAdapter<FullTransaction, TransactionsListAdapter.ViewHolder>(
        TransactionsDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_transaction_short, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ListItemTransactionShortBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(transaction: FullTransaction) {
            with(binding) {

                setClickListener { view ->
                    view.findNavController().navigate(
                        OverviewFragmentDirections.actionOverviewFragmentToTransactionFragment(
                            transaction.info.id, ""
                        )
                    )
                }

                this.transaction = transaction
                executePendingBindings()
            }
        }
    }
}

private class TransactionsDiffCallback : DiffUtil.ItemCallback<FullTransaction>() {

    override fun areItemsTheSame(
        oldItem: FullTransaction,
        newItem: FullTransaction
    ): Boolean {
        return oldItem.info.id == newItem.info.id
    }

    override fun areContentsTheSame(
        oldItem: FullTransaction,
        newItem: FullTransaction
    ): Boolean {
        return oldItem.info == newItem.info
    }
}