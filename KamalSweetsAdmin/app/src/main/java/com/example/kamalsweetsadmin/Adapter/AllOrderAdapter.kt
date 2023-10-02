package com.example.kamalsweetsadmin.Adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getDrawable
import androidx.recyclerview.widget.RecyclerView
import com.example.kamalsweetsadmin.Model.AllOrderModel
import com.example.kamalsweetsadmin.Model.CategoryModel
import com.example.kamalsweetsadmin.R
import com.example.kamalsweetsadmin.databinding.AllOrderItemLayoutBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AllOrderAdapter(var list: ArrayList<AllOrderModel>,val context: Context) :RecyclerView.Adapter<AllOrderAdapter.viewHolder>() {

    inner class viewHolder(val binding: AllOrderItemLayoutBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val binding=AllOrderItemLayoutBinding.inflate((LayoutInflater.from(context)),parent,false)

        return viewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.binding.productTitle.text="${list[position].name}  x  ${list[position].productQuantity}"
        holder.binding.productPrice.text="₹"+list[position].price
        holder.binding.customerNumber.text=list[position].userId
        holder.binding.customerName.text=list[position].userName
        holder.binding.customerAddress.text=list[position].userAddress
        if(list[position].status=="Canceled"){
            holder.binding.cancelButton.text="Order Canceled"
            holder.binding.cancelButton.textSize=25f
            holder.binding.cancelButton.isEnabled=false
        }
        holder.binding.cancelButton.setOnClickListener {
            holder.binding.cancelButton.text="Canceled"
            holder.binding.cancelButton.textSize=25f
            holder.binding.cancelButton.isEnabled=false
            holder.binding.proceedButton.visibility=View.GONE
            updateStatus("Canceled",list[position].orderId!!,position)
        }

        when(list[position].status){
            "Ordered"->{
                holder.binding.proceedButton.text="Confirm Order"
                holder.binding.productUpdate.text="New Order Recieved!"
                holder.binding.productUpdate.setTextColor(Color.parseColor("#02E72A"))
                holder.binding.proceedButton.setOnClickListener {
                    updateStatus("Confirmed",list[position].orderId!!,position)

                }
            }
            "Confirmed"->{
                holder.binding.proceedButton.text="Dispatched"
                holder.binding.productUpdate.text="Order Confirmed!"
                holder.binding.productUpdate.setTextColor(Color.parseColor("#02E72A"))
                holder.binding.proceedButton.setOnClickListener {
                    updateStatus("Dispatched",list[position].orderId!!,position)

                }
            }
            "Dispatched"->{
                holder.binding.productUpdate.text="Order Dispatched"
                holder.binding.productUpdate.setTextColor(Color.parseColor("#FFBD33"))
                holder.binding.proceedButton.text="Deliver"
                holder.binding.proceedButton.setOnClickListener {
                    updateStatus("Delivered", list[position].orderId!!,position)

                }
            }
            "Delivered"->{
                holder.binding.cancelButton.visibility=View.GONE
                holder.binding.productUpdate.text="Order Delivered"
                holder.binding.productUpdate.setTextColor(Color.parseColor("#02E72A"))
                holder.binding.proceedButton.text="Already Delivered"
                holder.binding.proceedButton.isEnabled=false
                holder.binding.proceedButton.setOnClickListener {
                    Toast.makeText(context, "Product Has Delivered to customer", Toast.LENGTH_SHORT).show()
                }
            }
            "Canceled"->{
                holder.binding.proceedButton.visibility=View.GONE
                holder.binding.productUpdate.text="Order Canceled"
                holder.binding.productUpdate.setTextColor(Color.parseColor("#CD0808"))

            }
        }

    }

    private fun updateStatus(str:String,doc:String,position: Int){
        val data= hashMapOf<String,Any>()
        data["status"]=str
        Firebase.firestore.collection("allOrders").document(doc).update(data)
            .addOnSuccessListener {
                list[position].status = str
                notifyDataSetChanged()
                Toast.makeText(context, "Status Updated", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
            }
    }
}