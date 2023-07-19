package com.example.kamalsweetsadmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.findNavController
import com.example.kamalsweetsadmin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val navHostFragment=supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        val navController=navHostFragment!!.findNavController()

        navController.addOnDestinationChangedListener(object : NavController.OnDestinationChangedListener {
            override fun onDestinationChanged(
                controller: NavController,
                destination: NavDestination,
                arguments: Bundle?
            ) {
                title=when(destination.id){
                    R.id.categoryFragment->"CATEGORY"
                    R.id.sliderFragment->"ADD SLIDER"
                    R.id.addProductFragment->"ADD PRODUCT"
                    R.id.productFragment->"PRODUCTS"
                    else->"Kamal Sweets"
                }
            }

        })
    }
}