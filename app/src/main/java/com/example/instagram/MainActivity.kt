package com.example.instagram

import android.os.Bundle
import android.os.Message
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.instagram.Fragment.*
import com.example.instagram.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var onNavigationItemSelectedListener =BottomNavigationView.OnNavigationItemSelectedListener{ item->
        when(item.itemId){
            R.id.nav_home->{
                moveToFragment(HomeFragment())
                return@OnNavigationItemSelectedListener  true
            }
            R.id.nav_search->{
                moveToFragment(SearchFragment())
                return@OnNavigationItemSelectedListener  true
            }
            R.id.nav_add->{
                moveToFragment(AddFragment())
                return@OnNavigationItemSelectedListener  true
            }
            R.id.nav_notifications->{
                moveToFragment(NotificationFragment())
                return@OnNavigationItemSelectedListener  true
            }
            R.id.nav_profile->{
                moveToFragment(ProfileFragment())
                return@OnNavigationItemSelectedListener  true
            }
        }
        false

        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView=findViewById(R.id.nav_view)

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        moveToFragment(HomeFragment())
    }
    private fun moveToFragment(fragment: Fragment){
        val fragmentTrans = supportFragmentManager.beginTransaction()
        fragmentTrans.replace(R.id.fragment_container,fragment)
        fragmentTrans.commit()
    }
}