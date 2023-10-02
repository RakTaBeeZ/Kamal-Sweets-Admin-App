package com.example.kamalsweetsadmin.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kamalsweetsadmin.Model.CategoryModel
import com.example.kamalsweetsadmin.databinding.ItemCategoryLayoutBinding

class CategoryAdapter(val list: ArrayList<CategoryModel>,val context:Context): RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

//    inner class CategoryViewHolder(View: View):RecyclerView.ViewHolder(View){
//        val binding=ItemCategoryLayoutBinding.bind(View)
//    }
    inner class CategoryViewHolder(val binding:ItemCategoryLayoutBinding):RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
//        val binding=LayoutInflater.from(context).inflate(R.layout.item_category_layout,parent,false)
        val binding=ItemCategoryLayoutBinding.inflate(LayoutInflater.from(context),parent,false)
        return CategoryViewHolder(binding)
    }

    override fun getItemCount(): Int {

        return list.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
    holder.binding.categoryName.text=list[position].cat
    Glide.with(context).load(list[position].img).into(holder.binding.imageView2)
    }
}
/*

5kg Boondi
sev
glass(200)ml
chai glass
panipuri plate 200pcs

 */



