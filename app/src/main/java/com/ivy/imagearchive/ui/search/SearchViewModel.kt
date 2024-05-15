package com.ivy.imagearchive.ui.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivy.imagearchive.remotesource.SearchRepository
import com.ivy.imagearchive.remotesource.dataclass.SearchRequestData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.http.Query
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchRepository: SearchRepository) : ViewModel() {

    val _imageData = MutableLiveData<ArrayList<SearchItemData>>()
    val imageData = ArrayList<SearchItemData>()

    var page = 1
    var query = ""

    fun addSearchData(searchRequestData: SearchRequestData){
        addPage()
        viewModelScope.launch {
            searchRepository.getSearchResult(searchRequestData).collect {
                Log.d("", it.toString())
                imageData.addAll(it)
                _imageData.value = imageData
            }
        }
    }

    fun replaceSearchData(searchRequestData: SearchRequestData){
        updateQuery(searchRequestData.query)
        viewModelScope.launch {
            searchRepository.getSearchResult(searchRequestData).collect {
                Log.d("", it.toString())
                _imageData.value = it
            }
        }
    }

    private fun addPage(){
        page += 1
    }

    fun updateQuery(newQuery: String){
        query = newQuery
    }
}