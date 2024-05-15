package com.ivy.imagearchive.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.ivy.imagearchive.constant.favoriteItemsKey
import com.ivy.imagearchive.constant.sharedPreferencesKey
import com.ivy.imagearchive.ui.search.SearchItemData

class PreferenceUtil(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(sharedPreferencesKey, Context.MODE_PRIVATE)


    fun getString(key: String, defValue: String): String {
        return prefs.getString(key, defValue).toString()
    }

    fun getFavoriteItemList(): ArrayList<SearchItemData> {
        val stringPrefs = getString(favoriteItemsKey, "[]")
        return GsonBuilder().create().fromJson(
            stringPrefs,
            object : TypeToken<ArrayList<SearchItemData>>() {}.type
        )
    }

    fun saveFavoriteItemList(arrayListPrefs: ArrayList<SearchItemData>) {
        val stringPrefs = GsonBuilder().create().toJson(
            arrayListPrefs,
            object : TypeToken<ArrayList<SearchItemData>>() {}.type
        )
        prefs.edit().putString(favoriteItemsKey, stringPrefs).apply() // SharedPreferences에 push

        Log.d("search - pref", getString(favoriteItemsKey, "[]"))
    }

    fun addFavoriteItem(searchItemData: SearchItemData) {
        saveFavoriteItemList(
            getFavoriteItemList()
                .apply {
                    add(searchItemData)
                })
    }

    fun deleteFavoriteItem(searchItemData: SearchItemData) {
        saveFavoriteItemList(
            getFavoriteItemList()
                .apply {
                    remove(searchItemData)
                }
        )
    }

    fun deleteAllFavoriteItems() {
        prefs.edit().remove(favoriteItemsKey).apply()
    }
}