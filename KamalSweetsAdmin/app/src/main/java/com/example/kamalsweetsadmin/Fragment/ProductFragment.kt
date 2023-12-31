package com.example.kamalsweetsadmin.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.kamalsweetsadmin.Adapter.ProductAdapter
import com.example.kamalsweetsadmin.Model.AddProductModel
import com.example.kamalsweetsadmin.Model.AddProductModels
import com.example.kamalsweetsadmin.R
import com.example.kamalsweetsadmin.databinding.FragmentProductBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class ProductFragment : Fragment() {
    private lateinit var binding: FragmentProductBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductBinding.inflate(layoutInflater)
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_productFragment_to_addProductFragment)
        }
        getProducts()
        return binding.root
    }

    private fun getProducts() {
        val list =ArrayList<AddProductModels>()
        Firebase.firestore.collection("products")
            .get().addOnSuccessListener {
                list.clear()
                for(doc in it.documents){
                    val data=doc.toObject(AddProductModels::class.java)
                    list.add(data!!)
                }
                binding.productRecyclerView.adapter= ProductAdapter(requireContext(),list)
            }.addOnFailureListener { error->
                Toast.makeText(requireContext(), "$error", Toast.LENGTH_SHORT).show()
            }
    }
}