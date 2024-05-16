package com.ivy.imagearchive.ui.itemdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ivy.imagearchive.repository.FavoriteRepository
import com.ivy.imagearchive.repository.SearchRepository
import com.ivy.imagearchive.ui.search.SearchItemData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ItemDetailViewModel @Inject constructor(private val favoriteRepository: FavoriteRepository) : ViewModel() {

    val _isFavorite = MutableLiveData<Boolean>()

    // 좋아요 갱신
    fun setIsFavorite(searchItemData: SearchItemData, check: Boolean){
        _isFavorite.value = check
        if (check) favoriteRepository.addFavoriteItem(searchItemData)
        else favoriteRepository.deleteFavoriteItem(searchItemData)
    }

    // 좋아요 누른 아이템인지
    fun getIsFavorite(searchItemData: SearchItemData): Boolean {
        _isFavorite.value = favoriteRepository.getFavoriteItemList().contains(searchItemData)
        return _isFavorite.value ?: false
    }
}