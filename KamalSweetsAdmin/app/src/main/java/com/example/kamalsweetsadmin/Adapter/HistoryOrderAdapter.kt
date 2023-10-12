package com.example.kamalsweetsadmin.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kamalsweetsadmin.Model.AllOrderModel
import com.example.kamalsweetsadmin.databinding.FragmentOrderHistoryBinding
import com.example.kamalsweetsadmin.databinding.OrderHistoryLayoutBinding

class HistoryOrderAdapter(var list:ArrayList<AllOrderModel>,val context: Context) : RecyclerView.Adapter<HistoryOrderAdapter.viewHolder>() {

    inner class viewHolder(var binding: OrderHistoryLayoutBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val binding=OrderHistoryLayoutBinding.inflate(LayoutInflater.from(context),parent,false)
        return viewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.binding.productTitle.text=list[position].name+"  x  "+list[position].productQuantity
       holder.binding.customerName.text=list[position].userName
        holder.binding.productUpdate.text="Delivered"
       holder.binding.customerNumber.text=list[position].userId
       holder.binding.customerAddress.text=list[position].userAddress
       holder.binding.productPrice.text="₹"+list[position].price
       holder.binding.paymentStatus.text="Payment Status: "+list[position].paymentStatus
       holder.binding.delPerNamP.text=list[position].deliveryPersonName
       holder.binding.delPerNumP.text=list[position].deliveryPersonNumber
        holder.binding.cancelReason.text="Cancel Reason: "+list[position].cancelReason
    }
}