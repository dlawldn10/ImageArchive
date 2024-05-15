package com.ivy.imagearchive

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.tabs.TabLayout
import com.ivy.imagearchive.databinding.ActivityMainBinding
import com.ivy.imagearchive.ui.favorite.FavoriteFragment
import com.ivy.imagearchive.ui.search.SearchFragment
import com.ivy.imagearchive.ui.search.SearchItemData
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    val searchTabPosition = 0
    val favoriteTabPosition = 1

    val searchFragment = SearchFragment()
    val favoriteFragment = FavoriteFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position){
                    searchTabPosition -> replaceFragment(searchFragment)
                    favoriteTabPosition -> replaceFragment(favoriteFragment)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })

        replaceFragment(searchFragment)
    }

    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.commit {
            replace(binding.fragmentContainer.id, fragment)
        }
    }
}