package com.ivy.imagearchive.ui.search

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ivy.imagearchive.databinding.ItemSearchBinding

class SearchRecyclerViewAdapter(
    private var searchItemList: ArrayList<SearchItemData>,
    private val activity: Activity
): RecyclerView.Adapter<SearchRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemSearchBinding, val activity: Activity) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SearchItemData) {

            binding.apply {
                itemCategory.text = item.category
                itemContent.text = item.contentUrl
                itemDatetime.text = item.dateTime
                itemTitle.text = item.title
            }

            Glide.with(activity).load(item.thumbnailUrl).into(binding.itemThumbnailLayout.itemThumbnail)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            activity
        )
    }

    override fun getItemCount(): Int {
        return searchItemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position % 10 == 9){
            holder.binding.itemFooterLayout.itemFooterLayout.visibility = View.VISIBLE
            holder.binding.itemFooterLayout.pageNumber.text = (position/10 + 1).toString()
        }else{
            holder.binding.itemFooterLayout.itemFooterLayout.visibility = View.GONE
        }
        return holder.bind(searchItemList[position])
    }

    fun setItemList(searchItemList: ArrayList<SearchItemData>){
        this.searchItemList = searchItemList
        notifyDataSetChanged()
    }
}