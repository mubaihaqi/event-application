package com.mubaihaqi.eventapplication.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mubaihaqi.eventapplication.R
import com.mubaihaqi.eventapplication.fragment.FinishedFragment
import com.mubaihaqi.eventapplication.fragment.HomeFragment
import com.mubaihaqi.eventapplication.fragment.UpcomingFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment())
                .commit()
        }

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, HomeFragment())
                        .commit()
                    true
                }
                R.id.nav_upcoming -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, UpcomingFragment())
                        .commit()
                    true
                }
                R.id.nav_finished -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, FinishedFragment())
                        .commit()
                    true
                }
                else -> false
            }
        }
    }
}