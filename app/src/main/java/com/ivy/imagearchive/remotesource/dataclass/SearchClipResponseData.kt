package com.ivy.imagearchive.remotesource.dataclass

import com.google.gson.annotations.SerializedName

data class SearchClipResponseData(
    @SerializedName("documents")
    val documents: ArrayList<SearchClipItemData>
)
