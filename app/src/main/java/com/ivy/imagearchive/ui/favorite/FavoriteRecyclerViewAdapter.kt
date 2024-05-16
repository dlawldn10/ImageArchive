package com.ivy.imagearchive.ui.favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ivy.imagearchive.ItemDetailActivity
import com.ivy.imagearchive.MainActivity
import com.ivy.imagearchive.MainApplication
import com.ivy.imagearchive.R
import com.ivy.imagearchive.constant.INTENT_SELECTED_ITEM
import com.ivy.imagearchive.constant.ITEMTYPE_IMAGE
import com.ivy.imagearchive.constant.ITEMTYPE_VCLIP
import com.ivy.imagearchive.databinding.ItemFavoriteBinding
import com.ivy.imagearchive.ui.search.SearchItemData

class FavoriteRecyclerViewAdapter(
    private var searchItemList: ArrayList<SearchItemData>,
    private val activity: MainActivity
): RecyclerView.Adapter<FavoriteRecyclerViewAdapter.ViewHolder>() {
    class ViewHolder(
        private val binding: ItemFavoriteBinding,
        private val activity: MainActivity
    ) : RecyclerView.ViewHolder(binding.root) {

        private val application = activity.application as MainApplication
        
        fun bind(item: SearchItemData) {

            // 보관함 아이템 터치 시 동작(확대 화면으로)
            binding.root.setOnClickListener {
                val intent = Intent(activity, ItemDetailActivity::class.java)
                intent.putExtra(INTENT_SELECTED_ITEM, item)
                activity.favoriteItemDetailLauncher.launch(intent)
            }

            // 보관함 하트 버튼 터치 시 동작
            binding.itemFavorite.itemFavorite.apply {
                setOnCheckedChangeListener(null)
                isChecked = application.prefs.getFavoriteItemList().contains(item)
                setOnCheckedChangeListener { button, isChecked ->
                    if (isChecked){
                        // 보관함에 넣기
                        application.prefs.addFavoriteItem(item)
                    }else{
                        // 보관함에서 삭제
                        application.prefs.deleteFavoriteItem(item)
                    }
                }
            }

            // 아이템 표시 정보 세팅
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
        return holder.bind(searchItemList[position])
    }

}