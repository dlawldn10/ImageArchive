package com.ivy.imagearchive

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.tabs.TabLayout
import com.ivy.imagearchive.constant.INTENT_SELECTED_ITEM
import com.ivy.imagearchive.constant.ITEMTYPE_IMAGE
import com.ivy.imagearchive.constant.ITEMTYPE_VCLIP
import com.ivy.imagearchive.databinding.ActivityItemDetailBinding
import com.ivy.imagearchive.databinding.ActivityMainBinding
import com.ivy.imagearchive.ui.itemdetail.ItemDetailFragment
import com.ivy.imagearchive.ui.itemdetail.ItemDetailViewModel
import com.ivy.imagearchive.ui.search.SearchItemData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ItemDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityItemDetailBinding

    lateinit var selectedItem: SearchItemData

    lateinit var itemDetailViewModel: ItemDetailViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        itemDetailViewModel = ViewModelProvider(this).get(ItemDetailViewModel::class.java)

        if (intent.hasExtra(INTENT_SELECTED_ITEM)){
            selectedItem = intent.getSerializableExtra(INTENT_SELECTED_ITEM) as SearchItemData
        }

        binding = ActivityItemDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setAppbarView()

        supportFragmentManager.commit {
            replace(binding.fragmentContainer.id, ItemDetailFragment())
        }
    }

    private fun setAppbarView(){
        binding.appbarBack.setOnClickListener {
            onBackPressed()
        }

        binding.appbarTitle.text = selectedItem.title

        binding.detailFavorite.apply {
            isChecked = itemDetailViewModel.getIsFavorite(selectedItem)
            setOnCheckedChangeListener { button, isChecked ->
                itemDetailViewModel.setIsFavorite(selectedItem, isChecked)
            }
        }
    }
}