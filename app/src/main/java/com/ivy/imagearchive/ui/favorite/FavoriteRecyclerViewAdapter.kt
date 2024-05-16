package com.ivy.imagearchive.ui.favorite

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ivy.imagearchive.ItemDetailActivity
import com.ivy.imagearchive.MainActivity
import com.ivy.imagearchive.MainApplication
import com.ivy.imagearchive.R
import com.ivy.imagearchive.constant.ITEMTYPE_IMAGE
import com.ivy.imagearchive.constant.ITEMTYPE_VCLIP
import com.ivy.imagearchive.constant.PER_PAGE
import com.ivy.imagearchive.databinding.ItemFavoriteBinding
import com.ivy.imagearchive.databinding.ItemSearchBinding
import com.ivy.imagearchive.ui.search.SearchItemData

class FavoriteRecyclerViewAdapter(
    private var searchItemList: ArrayList<SearchItemData>,
    private val activity: MainActivity
): RecyclerView.Adapter<FavoriteRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemFavoriteBinding, val activity: Activity) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SearchItemData) {

            binding.apply {
                itemDatetime.text = item.dateTimeFavorite
                itemTitle.text = item.title
                when (item.itemType){
                    ITEMTYPE_VCLIP -> itemIcon.setImageDrawable(activity.getDrawable(R.drawable.icon_video))
                    ITEMTYPE_IMAGE -> itemIcon.setImageDrawable(activity.getDrawable(R.drawable.icon_image))
                }
            }

            Glide.with(activity).load(item.thumbnailUrl).into(binding.itemThumbnail)


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            activity
        )
    }

    override fun getItemCount(): Int {
        return searchItemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            val intent = Intent(activity, ItemDetailActivity::class.java)
            intent.putExtra("selectedItem", searchItemList[position])
//            activity.startActivity(intent)
            activity.favoriteItemDetailLauncher.launch(intent)
        }

        holder.binding.itemFavorite.itemFavorite.setOnCheckedChangeListener(null)

        holder.binding.itemFavorite.itemFavorite.isChecked =
            (activity.application as MainApplication).prefs.getFavoriteItemList().contains(searchItemList[position])

        holder.binding.itemFavorite.itemFavorite.setOnCheckedChangeListener { button, isChecked ->
            if (isChecked){
                // 보관함에 넣기
                (activity.application as MainApplication).prefs.addFavoriteItem(searchItemList[position])
            }else{
                // 보관함에서 삭제
                (activity.application as MainApplication).prefs.deleteFavoriteItem(searchItemList[position])
            }
        }


        return holder.bind(searchItemList[position])
    }

}