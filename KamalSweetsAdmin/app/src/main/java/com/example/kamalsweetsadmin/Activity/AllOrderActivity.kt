package com.example.kamalsweetsadmin.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.kamalsweetsadmin.Adapter.AllOrderAdapter
import com.example.kamalsweetsadmin.Model.AllOrderModel
import com.example.kamalsweetsadmin.R
import com.example.kamalsweetsadmin.databinding.ActivityAllOrderBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class AllOrderActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityAllOrderBinding.inflate(layoutInflater)

    }

    private lateinit var list:ArrayList<AllOrderModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        list =ArrayList()


        Firebase.firestore.collection("allOrders").get().addOnSuccessListener {
            
            if(it.size()<1){
                binding.recyclerView.visibility=View.GONE
                binding.noOrders.visibility=View.VISIBLE
            }
            list.clear()

            for (doc in it.documents){
                val data=doc.toObject(AllOrderModel::class.java)
                list.add(data!!)
            }
            val adapter =AllOrderAdapter(list,this)
            binding.recyclerView.adapter=adapter


        }.addOnFailureListener {
            Toast.makeText(this, "Fetching Order Details Went Wrong", Toast.LENGTH_SHORT).show()
        }



    }

   

}