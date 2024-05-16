package com.ivy.imagearchive.network.dataclass

import com.google.gson.annotations.SerializedName

data class SearchClipResponseData(
    @SerializedName("documents")
    val documents: ArrayList<SearchClipItemData>
)
