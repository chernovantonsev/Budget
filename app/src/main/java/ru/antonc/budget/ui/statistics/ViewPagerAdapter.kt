package ru.antonc.budget.ui.statistics

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_page_statistics.view.*
import ru.antonc.budget.R
import ru.antonc.budget.data.entities.CategoryStatistics

class ViewPagerAdapter : RecyclerView.Adapter<PagerVH>() {

    private var pages: ArrayList<Pair<String, List<CategoryStatistics>>> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerVH =
        PagerVH(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_page_statistics, parent, false)
        )

    override fun getItemCount(): Int {
        return pages.size
    }

    override fun onBindViewHolder(holder: PagerVH, position: Int): Unit = holder.itemView.run {
        pages[position].also { (_, categories) ->
            with(CategoryStatisticsAdapter()) {
                list_categories.adapter = this
                submitList(categories)
            }
        }
    }

    fun setPagesInfo(newItems: List<Pair<String, List<CategoryStatistics>>>) {
        pages.clear()
        pages.addAll(newItems)
        notifyDataSetChanged()
    }
}

class PagerVH(itemView: View) : RecyclerView.ViewHolder(itemView)