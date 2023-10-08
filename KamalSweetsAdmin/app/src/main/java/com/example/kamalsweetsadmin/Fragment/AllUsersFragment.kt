package com.example.kamalsweetsadmin.Fragment

import android.os.Binder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.kamalsweetsadmin.Adapter.ProductAdapter
import com.example.kamalsweetsadmin.Adapter.UserAdapter
import com.example.kamalsweetsadmin.Model.AddProductModels
import com.example.kamalsweetsadmin.Model.UserModel
import com.example.kamalsweetsadmin.R
import com.example.kamalsweetsadmin.databinding.FragmentAllUsersBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AllUsersFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val binding by lazy {
        FragmentAllUsersBinding.inflate(layoutInflater)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        getUsers()
        return binding.root
    }

    private fun getUsers() {
        val list =ArrayList<UserModel>()
        Firebase.firestore.collection("users")
            .get().addOnSuccessListener {
                list.clear()
                for(doc in it.documents){
                    val data=doc.toObject(UserModel::class.java)
                    list.add(data!!)
                }
                binding.recyclerView2.adapter= UserAdapter(list,requireContext())
            }.addOnFailureListener { error->
                Toast.makeText(requireContext(), "$error", Toast.LENGTH_SHORT).show()
            }
    }
}