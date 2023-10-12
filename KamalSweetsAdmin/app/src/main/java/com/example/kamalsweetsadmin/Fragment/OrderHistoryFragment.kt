package com.example.kamalsweetsadmin.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kamalsweetsadmin.Adapter.HistoryOrderAdapter
import com.example.kamalsweetsadmin.Model.AllOrderModel
import com.example.kamalsweetsadmin.databinding.FragmentOrderHistoryBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class OrderHistoryFragment : Fragment() {
    private val binding by lazy {
        FragmentOrderHistoryBinding.inflate(layoutInflater)
    }
    private  var list=ArrayList<AllOrderModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Firebase.firestore.collection("orderHistory").get().addOnSuccessListener {

            for(doc in it.documents){
                var data=doc.toObject(AllOrderModel::class.java)
                list.add(data!!)
            }
            binding.recyclerView3.adapter=HistoryOrderAdapter(list,requireContext())
        }
        return binding.root
    }


}