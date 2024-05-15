package com.ivy.imagearchive.remotesource

import com.ivy.imagearchive.remotesource.dataclass.SearchRequestData
import com.ivy.imagearchive.remotesource.dataclass.SearchResponseData
import com.ivy.imagearchive.ui.search.SearchItemData
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface RequestInterface {
    @GET("{path}")
    fun getSearchResult(
        @Path("path") path: String,
        @Header("Authorization") authorization: String,
        @Query("query") query: String,
        @Query("sort") sort: String = "recency",
        @Query("page") page: Int,
        @Query("size") perPage: Int = 10
    ) : Call<SearchResponseData>
}