package com.example.soccer

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import com.example.soccer.AddPlayer.AddPlayerFragment
import com.example.soccer.PlayersList.PlayersList

class MainActivity : AppCompatActivity() {


    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
               var PlayersList=PlayersList()
                Common.getFragment(PlayersList,R.id.main_content,this)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                var ParametersListFragment=ParametersListFragment()
             Common.getFragment(ParametersListFragment,R.id.main_content,this)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {

            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var PlayersList=PlayersList()
        Common.getFragment(PlayersList,R.id.main_content,this)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)


        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }
}
