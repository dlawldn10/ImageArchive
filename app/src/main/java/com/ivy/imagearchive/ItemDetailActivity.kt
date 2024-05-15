package com.ivy.imagearchive

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.tabs.TabLayout
import com.ivy.imagearchive.constant.ITEMTYPE_IMAGE
import com.ivy.imagearchive.constant.ITEMTYPE_VCLIP
import com.ivy.imagearchive.databinding.ActivityItemDetailBinding
import com.ivy.imagearchive.databinding.ActivityMainBinding
import com.ivy.imagearchive.ui.itemdetail.ItemDetailFragment
import com.ivy.imagearchive.ui.search.SearchItemData

class ItemDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityItemDetailBinding

    lateinit var selectedItem: SearchItemData


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (intent.hasExtra("selectedItem")){
            selectedItem = intent.getSerializableExtra("selectedItem") as SearchItemData
        }

        binding = ActivityItemDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.appbarBack.setOnClickListener {
            onBackPressed()
        }

        binding.appbarTitle.text = selectedItem.title

        binding.detailFavorite.isChecked = (application as MainApplication).prefs.getFavoriteItemList().contains(selectedItem)
        binding.detailFavorite.setOnCheckedChangeListener { button, isChecked ->
            if (isChecked){
                // 보관함에 넣기
                (application as MainApplication).prefs.addFavoriteItem(selectedItem)
            }else{
                // 보관함에서 삭제
                (application as MainApplication).prefs.deleteFavoriteItem(selectedItem)
            }
        }


        supportFragmentManager.commit {
            replace(binding.fragmentContainer.id, ItemDetailFragment())
        }



    }
}