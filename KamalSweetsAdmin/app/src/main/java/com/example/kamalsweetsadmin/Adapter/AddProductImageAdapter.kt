package com.example.kamalsweetsadmin.Adapter

import android.net.Uri
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import com.example.kamalsweetsadmin.databinding.ImageItemBinding

class AddProductImageAdapter(val list: ArrayList<Uri>) : RecyclerView.Adapter<AddProductImageAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(val binding: ImageItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder( holder: ProductViewHolder, position: Int) {
        holder.binding.itemImage.setImageURI(list[position])
    }
}
