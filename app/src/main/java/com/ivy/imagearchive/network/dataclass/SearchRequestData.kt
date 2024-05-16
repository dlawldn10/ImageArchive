package com.ivy.imagearchive.network.dataclass

import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

data class SearchRequestData(
    @Path("path") var path: String = "image",
    @Header("Authorization") val authorization: String = "KakaoAK 12e8b9317e72dbc97bc1610193cf7fd2",
    @Query("query") val query: String = "",
    @Query("sort") val sort: String = "recency",
    @Query("page") val page: Int = 1,
    @Query("size") val perPage: Int = 10
)
