package com.ivy.imagearchive.constant

import android.content.Context
import android.widget.Toast

const val PER_PAGE = 20
const val PATH_IMAGE = "image"
const val PATH_VCLIP = "vclip"
const val ITEMTYPE_IMAGE = 0
const val ITEMTYPE_VCLIP = 1

const val sharedPreferencesKey = "shared_pref"
const val favoriteItemsKey = "favorite_pref"


const val FRAG_SEARCH = "fragment_search"
const val FRAG_FAVORITE = "fragment_favorite"

const val INTENT_SELECTED_ITEM = "selectedItem"

fun showEmptyQueryToast(context: Context, newQuery: String){
    if (newQuery.isEmpty()) {
        Toast.makeText(context, "검색어를 입력해주세요", Toast.LENGTH_SHORT).show()
    }
}