package ru.antonc.budget.ui.statistics

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_page_statistics.view.*
import ru.antonc.budget.R

class ViewPagerAdapter : RecyclerView.Adapter<PagerVH>() {

    private var pageTitles = ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerVH =
        PagerVH(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_page_statistics, parent, false)
        )

    override fun getItemCount(): Int{
        return pageTitles.size
    }


    override fun onBindViewHolder(holder: PagerVH, position: Int) = holder.itemView.run {

    }

    fun setPagesInfo(newItems: List<String>) {
        pageTitles.clear()
        pageTitles.addAll(newItems)
        notifyDataSetChanged()
    }
}

class PagerVH(itemView: View) : RecyclerView.ViewHolder(itemView)