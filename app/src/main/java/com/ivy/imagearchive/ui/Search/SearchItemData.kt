package com.ivy.imagearchive.ui.Search

import android.os.Parcelable
import java.io.Serializable

data class SearchItemData(
    val thumbnailUrl: String,
    val title: String,
    val contentUrl: String,
    val dateTime: String
)
