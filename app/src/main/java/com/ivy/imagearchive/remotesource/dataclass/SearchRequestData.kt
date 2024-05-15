package com.ivy.imagearchive.remotesource.dataclass

import com.google.gson.annotations.SerializedName
import com.ivy.imagearchive.ui.search.SearchItemData
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

data class SearchRequestData(
    @Path("path") val path: String = "image",
    @Header("Authorization") val authorization: String = "KakaoAK 12e8b9317e72dbc97bc1610193cf7fd2",
    @Query("query") val query: String = "",
    @Query("sort") val sort: String = "recency",
    @Query("page") val page: Int = 1,
    @Query("size") val perPage: Int = 10
)
