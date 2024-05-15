package com.ivy.imagearchive.remotesource.dataclass

import com.google.gson.annotations.SerializedName

data class SearchClipItemData(
    @SerializedName("title")
    val title: String,

    @SerializedName("thumbnail")
    val thumbnailUrl: String,

    @SerializedName("url")
    val contentUrl: String,

    @SerializedName("datetime")
    val dateTime: String
)
