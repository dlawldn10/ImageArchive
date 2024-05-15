package com.ivy.imagearchive.ui.search

import com.google.gson.annotations.SerializedName
import com.ivy.imagearchive.constant.ITEMTYPE_IMAGE
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

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
) : Serializable {
    val dateTimeSearch: String
        get() {
            // 2024-05-16T04:45:59.000+09:00
            val usFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.KOREA)
            usFormat.timeZone = TimeZone.getTimeZone("UTC")
            val usDateTime = usFormat.parse(dateTime) ?: return "wrong date format"
            val koreaFormat = SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분 ss초", Locale.KOREA)
            return koreaFormat.format(usDateTime)
        }

    val dateTimeFavorite: String
        get() {
            // 2024-05-16T04:45:59.000+09:00
            val usFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.KOREA)
            usFormat.timeZone = TimeZone.getTimeZone("UTC")
            val usDateTime = usFormat.parse(dateTime) ?: return "wrong date format"
            val koreaFormat = SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREA)
            return koreaFormat.format(usDateTime)
        }
}
