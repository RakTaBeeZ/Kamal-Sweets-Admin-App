package com.example.kamalsweetsadmin.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.kamalsweetsadmin.Activity.AllOrderActivity
import com.example.kamalsweetsadmin.R
import com.example.kamalsweetsadmin.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
   private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentHomeBinding.inflate(layoutInflater)

        binding.apply {
            addCategory.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_categoryFragment)
            }
            Product.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_productFragment)
            }
            addSlider.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_sliderFragment)

            }
            allorderdetail.setOnClickListener {
                startActivity(Intent(requireContext(),AllOrderActivity::class.java))
            }
            alluserdetail.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_allUsersFragment)
            }


        }
        return binding.root
    }

}