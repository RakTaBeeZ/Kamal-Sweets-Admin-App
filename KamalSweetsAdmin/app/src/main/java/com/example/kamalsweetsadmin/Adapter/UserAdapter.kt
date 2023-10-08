package com.example.kamalsweetsadmin.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kamalsweetsadmin.Model.UserModel
import com.example.kamalsweetsadmin.databinding.AllUserDetailsBinding

class UserAdapter(val list:ArrayList<UserModel>,val context: Context):RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: AllUserDetailsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=AllUserDetailsBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data=list[position]
        val address=data.village+data.city+data.state
        val pincode=data.pincode
        val customerAddress= holder.binding.customeraddress
        val customerPincode= holder.binding.customerpincode
        holder.binding.customername.text="Name: "+data.userName
        holder.binding.customernumber.text="Number: +91 "+data.userPhoneNumber
        if(address.isEmpty()){
            customerAddress.text="Address: --------------"
        }else{
            customerAddress.text="Address: "+data.village+" "+data.city+" "+data.state
        }
        if(pincode.isEmpty()){
            customerPincode.text="Pincode: ---------------"
        }else{
            customerPincode.text="Pincode: "+pincode
        }




    }
}