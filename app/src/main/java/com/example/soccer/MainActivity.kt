package com.example.soccer

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView

class MainActivity : AppCompatActivity() {


    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
               var AddParametersFragment=AddParametersFragment()
                var fragmentTransaction=supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.main_content,AddParametersFragment)
                fragmentTransaction.commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {

            }
            R.id.navigation_notifications -> {

            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)


        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }
}
