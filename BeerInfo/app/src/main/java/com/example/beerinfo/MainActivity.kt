package com.example.beerinfo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.beerinfo.databinding.ActivityMainBinding
import com.example.beerinfo.ui.main.FavouriteFragment
import com.example.beerinfo.ui.main.BeerListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadFragment(BeerListFragment())
        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home->{
                    loadFragment(BeerListFragment())
                    true
                }
                R.id.fav->{
                    loadFragment(FavouriteFragment())
                    true
                }
                else -> {false}
            }
        }
    }

    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container,fragment)
        transaction.commit()
    }
}