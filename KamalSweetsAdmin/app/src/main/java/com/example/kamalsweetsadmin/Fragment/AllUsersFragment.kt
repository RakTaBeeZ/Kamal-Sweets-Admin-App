package com.example.kamalsweetsadmin.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.kamalsweetsadmin.Adapter.ProductAdapter
import com.example.kamalsweetsadmin.Model.AddProductModels
import com.example.kamalsweetsadmin.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AllUsersFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_users, container, false)
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
               // binding.productRecyclerView.adapter= ProductAdapter(requireContext(),list)
            }.addOnFailureListener { error->
                Toast.makeText(requireContext(), "$error", Toast.LENGTH_SHORT).show()
            }
    }
}