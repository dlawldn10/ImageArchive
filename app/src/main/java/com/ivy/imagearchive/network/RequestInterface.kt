package com.ivy.imagearchive.network

import com.ivy.imagearchive.network.dataclass.SearchClipResponseData
import com.ivy.imagearchive.network.dataclass.SearchResponseData
import retrofit2.Call
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

    @GET("{path}")
    fun getClipSearchResult(
        @Path("path") path: String,
        @Header("Authorization") authorization: String,
        @Query("query") query: String,
        @Query("sort") sort: String = "recency",
        @Query("page") page: Int,
        @Query("size") perPage: Int = 10
    ) : Call<SearchClipResponseData>
}