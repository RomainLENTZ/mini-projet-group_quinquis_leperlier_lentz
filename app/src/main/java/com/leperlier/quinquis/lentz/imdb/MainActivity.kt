package com.leperlier.quinquis.lentz.imdb

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.gmail.eamosse.imdb.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.leperlier.quinquis.lentz.imdb.ui.home.HomeFragment
import com.leperlier.quinquis.lentz.imdb.ui.menu.about.AboutFragment
import com.leperlier.quinquis.lentz.imdb.ui.menu.trending.TrendingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initNavController()
    }

    private fun initNavController() {
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener { item ->
            val fragment = when (item.itemId) {
                R.id.navigation_home -> HomeFragment()
                R.id.navigation_trending -> TrendingFragment()
                R.id.navigation_about -> AboutFragment()
                else -> null
            }
            fragment?.let {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, it)
                    .commit()
            }
            true
        }
    }
}
