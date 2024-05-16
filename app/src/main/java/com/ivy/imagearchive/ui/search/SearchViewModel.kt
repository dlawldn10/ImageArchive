package com.ivy.imagearchive.ui.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivy.imagearchive.constant.ITEMTYPE_VCLIP
import com.ivy.imagearchive.constant.PATH_IMAGE
import com.ivy.imagearchive.constant.PATH_VCLIP
import com.ivy.imagearchive.network.SearchRepository
import com.ivy.imagearchive.network.dataclass.SearchRequestData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchRepository: SearchRepository) : ViewModel() {

    val _imageData = MutableLiveData<ArrayList<SearchItemData>>()
    val imageData = ArrayList<SearchItemData>()

    var page = 1

    var query = ""  // 검색어 갱신
    val _query = MutableLiveData<String>()  // 타이핑 감지

    fun typeQuery(newQuery: String){
        _query.value = newQuery
    }

    fun performSearch(searchRequestData: SearchRequestData){
        updateQuery(searchRequestData.query)
        imageData.clear()
        search(searchRequestData)
        addPage()
    }

    fun fetchNextPage(searchRequestData: SearchRequestData){
        search(searchRequestData)
        addPage()
    }

    fun search(searchRequestData: SearchRequestData){
        viewModelScope.launch {
            val tmpDataArray = arrayListOf<SearchItemData>()

            // 이미지 검색
            searchRequestData.path = PATH_IMAGE
            searchRepository.getSearchResult(searchRequestData).collect {
                Log.d("search - image", it.toString())
                tmpDataArray.addAll(it)
            }

            // 동영상 검색
            searchRequestData.path = PATH_VCLIP
            searchRepository.getClipSearchResult(searchRequestData).collect { resultArray ->
                Log.d("search - vclip", resultArray.toString())
                val newMap = resultArray.map { SearchItemData(
                    ITEMTYPE_VCLIP,
                    it.title,
                    "",
                    it.thumbnailUrl,
                    it.contentUrl,
                    "",
                    it.dateTime
                ) }
                tmpDataArray.addAll(newMap)
            }

            Log.d("search - this page", tmpDataArray.toString())
            // 최신순 분류
            sortByRecency(tmpDataArray)
            updateSearchItemList()

        }
    }

    private fun updateSearchItemList(){
        _imageData.value = imageData
        Log.d("search - all", imageData.toString())
    }

    private fun sortByRecency(array: ArrayList<SearchItemData>){
        imageData.addAll(array.sortedByDescending { it.dateTime })
    }

    private fun addPage(){
        page += 1
    }

    private fun updateQuery(newQuery: String){
        query = newQuery
    }
}