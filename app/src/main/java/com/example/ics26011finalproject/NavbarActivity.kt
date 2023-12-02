package com.example.ics26011finalproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class NavbarActivity : AppCompatActivity() { private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navbar)

        bottomNavigationView = findViewById(R.id.bottomNavbar)

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId){
                R.id.navHome ->{
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.navCategories ->{
                    replaceFragment(CategoriesFragment())
                    true
                }
                R.id.navAccount ->{
                    val userFragment = UserFragment()
                    val bundle = Bundle()
                    bundle.putString("USERNAME", intent.getStringExtra("USERNAME"))
                    bundle.putString("FIRST_NAME", intent.getStringExtra("FIRST_NAME"))
                    bundle.putString("LAST_NAME", intent.getStringExtra("LAST_NAME"))
                    bundle.putString("EMAIL", intent.getStringExtra("EMAIL"))
                    userFragment.arguments = bundle

                    replaceFragment(userFragment)
                    true
                }
                else -> false
            }
        }
        replaceFragment(HomeFragment())
    }

    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.frame_main, fragment).commit()
    }
}
