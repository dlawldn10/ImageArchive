package com.ivy.imagearchive.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivy.imagearchive.remotesource.SearchRepository
import com.ivy.imagearchive.remotesource.dataclass.SearchRequestData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchRepository: SearchRepository) : ViewModel() {

    val _imageData = MutableLiveData<ArrayList<SearchItemData>>()

    val imageData: LiveData<ArrayList<SearchItemData>> get() = _imageData

    fun search(searchRequestData: SearchRequestData? = null){
        viewModelScope.launch {

            searchRepository.getSearchResult().collect {
                Log.d("", it.toString())
                _imageData.value = it
            }
        }
    }

    fun setRecyclerView(){

    }
}