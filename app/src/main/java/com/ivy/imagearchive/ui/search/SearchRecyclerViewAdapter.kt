package com.ivy.imagearchive.ui.search

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
import com.ivy.imagearchive.constant.INTENT_SELECTED_ITEM
import com.ivy.imagearchive.constant.ITEMTYPE_IMAGE
import com.ivy.imagearchive.constant.ITEMTYPE_VCLIP
import com.ivy.imagearchive.constant.PER_PAGE
import com.ivy.imagearchive.databinding.ItemSearchBinding

class SearchRecyclerViewAdapter(
    private var searchItemList: ArrayList<SearchItemData>,
    private val activity: MainActivity
): RecyclerView.Adapter<SearchRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(
        val binding: ItemSearchBinding,
        val activity: MainActivity
    ) : RecyclerView.ViewHolder(binding.root) {

        private val application = activity.application as MainApplication

        fun bind(item: SearchItemData, position: Int) {

            // 검색 아이템 터치 시 동작(확대 화면으로)
            binding.root.setOnClickListener {
                val intent = Intent(activity, ItemDetailActivity::class.java)
                intent.putExtra(INTENT_SELECTED_ITEM, item)
                activity.searchItemDetailLauncher.launch(intent)
            }

            // 페이징에 따른 뷰 출력
            if (position % PER_PAGE == PER_PAGE - 1){
                binding.itemFooterLayout.itemFooterLayout.visibility = View.VISIBLE
                binding.itemFooterLayout.pageNumber.text = (position/ PER_PAGE + 1).toString()
            }else{
                binding.itemFooterLayout.itemFooterLayout.visibility = View.GONE
            }

            // 보관함 하트 버튼 터치 시 동작
            binding.itemThumbnailLayout.itemFavorite.itemFavorite.apply {
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
                itemCategory.text = item.category
                itemDatetime.text = item.dateTimeSearch
                itemTitle.text = item.title
                when (item.itemType){
                    ITEMTYPE_VCLIP -> {
                        itemIcon.setImageDrawable(activity.getDrawable(R.drawable.icon_video))
                        itemContent.text = item.contentUrl
                    }
                    ITEMTYPE_IMAGE -> {
                        itemIcon.setImageDrawable(activity.getDrawable(R.drawable.icon_image))
                        itemContent.text = item.docUrl
                    }
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
        return holder.bind(searchItemList[position], position)
    }

    fun setItemList(searchItemList: ArrayList<SearchItemData>){
        this.searchItemList = searchItemList
        notifyDataSetChanged()
    }
}