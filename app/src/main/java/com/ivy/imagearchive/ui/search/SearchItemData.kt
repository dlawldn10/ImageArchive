package com.ivy.imagearchive.ui.search

import com.google.gson.annotations.SerializedName
import com.ivy.imagearchive.constant.ITEMTYPE_IMAGE
import java.io.Serializable

data class SearchItemData(
    val itemType: Int = ITEMTYPE_IMAGE,

    @SerializedName("display_sitename")
    val title: String,

    @SerializedName("collection")
    val category: String,

    @SerializedName("thumbnail_url")
    val thumbnailUrl: String,

    @SerializedName("image_url")
    val contentUrl: String,

    @SerializedName("doc_url")
    val docUrl: String,

    @SerializedName("datetime")
    val dateTime: String
) : Serializable
