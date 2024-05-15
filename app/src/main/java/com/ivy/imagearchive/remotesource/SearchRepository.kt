package com.ivy.imagearchive.remotesource

import android.app.Application
import android.util.Log
import android.widget.Toast
import com.ivy.imagearchive.remotesource.dataclass.SearchRequestData
import com.ivy.imagearchive.remotesource.dataclass.SearchResponseData
import com.ivy.imagearchive.ui.search.SearchItemData
import kotlinx.coroutines.flow.flow
import retrofit2.awaitResponse
import javax.inject.Inject

class SearchRepository @Inject constructor(private val server: RequestInterface) {

    suspend fun getSearchResult() = flow<ArrayList<SearchItemData>> {
        val response = server.getSearchResult(
            "image", "KakaoAK 12e8b9317e72dbc97bc1610193cf7fd2", "이효리", "recency", 1, 10
        ).awaitResponse()

        if (response.code() == 200 && response.body() != null){
            emit(response.body()!!.documents)
        }else{
            emit(arrayListOf())
        }
    }


}