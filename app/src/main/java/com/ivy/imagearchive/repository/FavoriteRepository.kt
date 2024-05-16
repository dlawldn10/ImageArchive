package com.ivy.imagearchive.repository

import com.ivy.imagearchive.network.dataclass.SearchClipItemData
import com.ivy.imagearchive.network.dataclass.SearchRequestData
import com.ivy.imagearchive.ui.search.SearchItemData
import com.ivy.imagearchive.utils.PreferenceUtil
import kotlinx.coroutines.flow.flow
import retrofit2.awaitResponse
import javax.inject.Inject

class FavoriteRepository @Inject constructor(private val prefUtil: PreferenceUtil) {

    fun getFavoriteItemList(): ArrayList<SearchItemData> {
        return prefUtil.getFavoriteItemList()
    }

    fun addFavoriteItem(searchItemData: SearchItemData) {
        prefUtil.addFavoriteItem(searchItemData)
    }

    fun deleteFavoriteItem(searchItemData: SearchItemData) {
        prefUtil.deleteFavoriteItem(searchItemData)
    }


}