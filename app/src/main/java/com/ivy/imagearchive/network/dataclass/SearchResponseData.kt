package com.ivy.imagearchive.network.dataclass

import com.google.gson.annotations.SerializedName
import com.ivy.imagearchive.ui.search.SearchItemData

data class SearchResponseData(
    @SerializedName("documents")
    val documents: ArrayList<SearchItemData>
)
