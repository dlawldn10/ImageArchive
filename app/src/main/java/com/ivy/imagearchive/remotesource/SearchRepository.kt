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

    suspend fun getSearchResult(searchRequestData: SearchRequestData) = flow<ArrayList<SearchItemData>> {
        val response = server.getSearchResult(
            searchRequestData.path,
            searchRequestData.authorization,
            searchRequestData.query,
            searchRequestData.sort,
            searchRequestData.page,
            searchRequestData.perPage
        ).awaitResponse()

        if (response.code() == 200 && response.body() != null){
            emit(response.body()!!.documents)
        }else{
            emit(arrayListOf())
        }
    }


}