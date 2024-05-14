package com.ivy.imagearchive.ui.search

import com.google.gson.annotations.SerializedName

data class SearchItemData(
    @SerializedName("display_sitename")
    val title: String,

    @SerializedName("collection")
    val category: String,

    @SerializedName("thumbnail_url")
    val thumbnailUrl: String,

    @SerializedName("doc_url")
    val contentUrl: String,

    @SerializedName("datetime")
    val dateTime: String
)
