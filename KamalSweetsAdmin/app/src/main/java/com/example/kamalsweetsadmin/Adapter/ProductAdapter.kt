package com.example.kamalsweetsadmin.Adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kamalsweetsadmin.Model.AddProductModels
import com.example.kamalsweetsadmin.databinding.LayoutProductItemBinding

class ProductAdapter(val context:Context,val list:ArrayList<AddProductModels>) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>(){

    inner class ProductViewHolder(val binding: LayoutProductItemBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding=LayoutProductItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return ProductViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return list.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

        val data=list[position]

        Glide.with(context).load(data.productCoverImage).into(holder.binding.productImage)
        holder.binding.textView3.text=data.productName
        holder.binding.textView2.text=data.productCategory
        holder.binding.textView1.text="MRP:- ₹${data.productMRP}"
        holder.binding.productSP.text="SP:- ₹${data.productSp}"
        holder.binding.productDesc.text=data.productDiscription
        Log.d("user", "onBindViewHolder: ${data.productDiscription}")

//        holder.itemView.setOnClickListener {
//            val intent = Intent(context, ProductDetailActivity::class.java)
//            intent.putExtra("id",list[position].produductID)
//            context.startActivity(intent)
//        }
    }
}