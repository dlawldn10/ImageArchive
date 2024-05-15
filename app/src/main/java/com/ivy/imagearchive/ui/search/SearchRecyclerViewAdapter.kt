package com.ivy.imagearchive.ui.search

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ivy.imagearchive.MainApplication
import com.ivy.imagearchive.R
import com.ivy.imagearchive.constant.ITEMTYPE_IMAGE
import com.ivy.imagearchive.constant.ITEMTYPE_VCLIP
import com.ivy.imagearchive.constant.PER_PAGE
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
                when (item.itemType){
                    ITEMTYPE_VCLIP -> itemIcon.setImageDrawable(activity.getDrawable(R.drawable.icon_video))
                    ITEMTYPE_IMAGE -> itemIcon.setImageDrawable(activity.getDrawable(R.drawable.icon_image))
                }
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
        if (position % PER_PAGE == PER_PAGE - 1){
            holder.binding.itemFooterLayout.itemFooterLayout.visibility = View.VISIBLE
            holder.binding.itemFooterLayout.pageNumber.text = (position/ PER_PAGE + 1).toString()
        }else{
            holder.binding.itemFooterLayout.itemFooterLayout.visibility = View.GONE
        }

        holder.binding.itemThumbnailLayout.itemFavorite.itemFavorite.setOnCheckedChangeListener { button, isChecked ->
            if (isChecked){
                // 보관함에 넣기
                (activity.application as MainApplication).prefs.addFavoriteItem(searchItemList[position])
            }else{
                // 보관함에서 삭제
                (activity.application as MainApplication).prefs.deleteFavoriteItem(searchItemList[position])
            }
        }

        holder.binding.itemThumbnailLayout.itemFavorite.itemFavorite.isChecked =
            (activity.application as MainApplication).prefs.getFavoriteItemList().contains(searchItemList[position])


        return holder.bind(searchItemList[position])
    }

    fun setItemList(searchItemList: ArrayList<SearchItemData>){
        this.searchItemList = searchItemList
        notifyDataSetChanged()
    }
}