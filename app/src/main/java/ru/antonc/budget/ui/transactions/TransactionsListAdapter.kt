package ru.antonc.budget.ui.transactions

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
import ru.antonc.budget.ui.transactions.TransactionListItem.Companion.TYPE_DATE

class TransactionsListAdapter :
    ListAdapter<TransactionListItem, RecyclerView.ViewHolder>(
        TransactionsDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            TYPE_DATE -> {
                return DateViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.list_item_transaction_date, parent, false
                    )
                )
            }

            else -> return TransactionViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.list_item_transaction, parent, false
                )
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).getType()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            TYPE_DATE -> {
                with(getItem(position) as TransactionDateListItem) {
                    (holder as DateViewHolder).bind(date, sum.toString())
                }
            }
            else -> (holder as TransactionViewHolder).bind((getItem(position) as TransactionGeneralListItem).transaction)
        }
    }

    class TransactionViewHolder(
        private val binding: ListItemTransactionBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(transaction: FullTransaction) {
            with(binding) {

                setClickListener { view ->
                    view.findNavController().navigate(
                        TransactionsListFragmentDirections.actionTransactionsListFragmentToTransactionFragment(
                            transaction.info.id, ""
                        )
                    )
                }

                this.transaction = transaction
                executePendingBindings()
            }
        }
    }

    class DateViewHolder(
        private val binding: ListItemTransactionDateBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(date: String, sum: String) {
            with(binding) {
                this.date = date
                this.sum = sum
                executePendingBindings()
            }
        }
    }
}

private class TransactionsDiffCallback : DiffUtil.ItemCallback<TransactionListItem>() {
    override fun areItemsTheSame(
        oldItem: TransactionListItem,
        newItem: TransactionListItem
    ): Boolean {
        return if (oldItem is TransactionGeneralListItem && newItem is TransactionGeneralListItem) {
            oldItem.transaction.info.id == newItem.transaction.info.id
        } else if (oldItem is TransactionDateListItem && newItem is TransactionDateListItem) {
            oldItem.date == newItem.date
        } else false
    }

    override fun areContentsTheSame(
        oldItem: TransactionListItem,
        newItem: TransactionListItem
    ): Boolean {
        return if (oldItem is TransactionGeneralListItem && newItem is TransactionGeneralListItem) {
            oldItem.transaction.info == newItem.transaction.info
        } else if (oldItem is TransactionDateListItem && newItem is TransactionDateListItem) {
            oldItem.date == newItem.date
        } else false
    }
}