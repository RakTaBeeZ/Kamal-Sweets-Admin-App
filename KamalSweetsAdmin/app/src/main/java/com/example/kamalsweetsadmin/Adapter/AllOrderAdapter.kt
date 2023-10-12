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
private lateinit var deliveryPersonName:String
private lateinit var deliveryPersonNumber:String
    inner class viewHolder(val binding: AllOrderItemLayoutBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val binding=AllOrderItemLayoutBinding.inflate((LayoutInflater.from(context)),parent,false)

        return viewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {


        holder.binding.productTitle.text="${list[position].name}  x  ${list[position].productQuantity}                                  "
        holder.binding.productPrice.text="₹"+list[position].price
        holder.binding.customerNumber.text=list[position].userId
        holder.binding.paymentStatus.text="Payment Status: "+list[position].paymentStatus
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
                        Firebase.firestore.collection("allOrders").document(list[position].orderId!!)
                            .get().addOnSuccessListener {
                               val status =it.getString("status").toString()
                                if (status=="Ordered"){
                                    updateStatus("Confirmed",list[position].orderId!!,position)
                                }else{
                                    holder.binding.cancelReason.visibility=View.VISIBLE
                                    holder.binding.cancelReason.text="Reason:-"+it.getString("cancelReason").toString()
                                    holder.binding.proceedButton.visibility=View.GONE
                                    holder.binding.cancelButton.isEnabled=false
                                    holder.binding.productUpdate.text="Order Canceled By Customer"
                                    holder.binding.productUpdate.setTextColor(Color.parseColor("#CD0808"))
                                }
                            }
                    }
            }
            "Confirmed"->{
                holder.binding.proceedButton.text="Dispatch Order"
                holder.binding.productUpdate.text="Order Confirmed!"
                holder.binding.productUpdate.setTextColor(Color.parseColor("#02E72A"))
                holder.binding.proceedButton.setOnClickListener {
                    if(holder.binding.delPerNam.text.toString().isEmpty() or holder.binding.delPerNum.text.toString().isEmpty()){
                        holder.binding.delPerNam.visibility=View.VISIBLE
                        holder.binding.delPerNum.visibility=View.VISIBLE
                        Toast.makeText(context, "Plesae Provide Delivery Details", Toast.LENGTH_SHORT).show()
                    }else{
                        deliveryPersonName=holder.binding.delPerNam.text.toString()
                        deliveryPersonNumber=holder.binding.delPerNum.text.toString()
                        holder.binding.delPerNam.visibility=View.GONE
                        holder.binding.delPerNum.visibility=View.GONE
                        holder.binding.delPerNumP.text="Person Nu8mber: "+deliveryPersonNumber
                        holder.binding.delPerNamP.text="Person Name: "+deliveryPersonName
                        holder.binding.deliveryDetails.visibility=View.VISIBLE

                        updateStatus("Dispatched",list[position].orderId!!,position)

                    }

                }
            }
            "Dispatched"->{
                holder.binding.productUpdate.text="Order Dispatched"
                holder.binding.productUpdate.setTextColor(Color.parseColor("#FFBD33"))
                holder.binding.proceedButton.text="Deliver Order"

                holder.binding.proceedButton.setOnClickListener {
                    updateStatus("Delivered", list[position].orderId!!,position)

                }
            }

            "Delivered"->{
                holder.binding.cancelButton.visibility=View.GONE
                holder.binding.productUpdate.text="Order Delivered"
                holder.binding.productUpdate.setTextColor(Color.parseColor("#02E72A"))
                holder.binding.proceedButton.text="Order is Delivered"
                holder.binding.proceedButton.isEnabled=false
                var data = getData(position)
                Firebase.firestore.collection("orderHistory").document(list[position].orderId!!).set(data).addOnSuccessListener {
                    deleteDocument(position)
                }.addOnFailureListener {e->
                    Log.w("user", "Error In Adding History document", e)
                }
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
        fetchDeliveryDetails(holder,list[position].orderId!!)
    }

    private fun deleteDocument(position: Int) {
        Firebase.firestore.collection("allOrders").document(list[position].orderId!!).delete()
            .addOnSuccessListener {
                Log.d("user", "DocumentSnapshot successfully deleted!")
            }.addOnFailureListener {e->
                Log.w("user", "Error deleting document", e)
            }
    }

    private fun getData(position:Int): Any {
        val data = hashMapOf<String,Any>()
        data["name"]=list[position].name!!
        data["price"]=list[position].price!!
        data["productId"]=list[position].productId!!
        data["status"]= list[position].status!!
        data["userId"]=list[position].userId!!
        data["userName"]=list[position].userName!!
        data["userAddress"]=list[position].userAddress
        data["productQuantity"]=list[position].productQuantity
        data["cancelReason"]=list[position].cancelReason
        data["paymentStatus"]=list[position].paymentStatus
        data["deliveryPersonName"]=list[position].deliveryPersonName
        data["deliveryPersonNumber"]=list[position].deliveryPersonNumber

        return data
    }

    private fun fetchDeliveryDetails(holder: AllOrderAdapter.viewHolder,orderId:String) {
        if(holder.binding.proceedButton.text=="Dispatch Order" || holder.binding.proceedButton.text=="Confirm Order"){
            if(holder.binding.cancelButton.text=="Order Canceled"){
                Firebase.firestore.collection("allOrders").document(orderId).get().addOnSuccessListener {
                    if (it.getString("deliveryPersonName").toString().isEmpty()){
                    }else{
                        holder.binding.delPerNumP.text="Person Number: "+it.getString("deliveryPersonNumber")
                        holder.binding.delPerNamP.text="Person Name: "+it.getString("deliveryPersonName")
                        holder.binding.deliveryDetails.visibility=View.VISIBLE
                    }

                }
            }
        }else{
            Firebase.firestore.collection("allOrders").document(orderId).get().addOnSuccessListener {
                if (it.getString("deliveryPersonName").toString().isEmpty()){

                }else{
                    holder.binding.delPerNumP.text="Person Number: "+it.getString("deliveryPersonNumber")
                    holder.binding.delPerNamP.text="Person Name: "+it.getString("deliveryPersonName")
                    holder.binding.deliveryDetails.visibility=View.VISIBLE
                }

            }
        }

    }

    private fun updateStatus(str:String,doc:String,position: Int){
        val data= hashMapOf<String,Any>()
        data["status"]=str
        if(str=="Dispatched"){
            data["deliveryPersonName"]=deliveryPersonName
            data["deliveryPersonNumber"]=deliveryPersonNumber
        }
        Firebase.firestore.collection("allOrders").document(doc).update(data)
            .addOnSuccessListener {
                notifyDataSetChanged()
                Toast.makeText(context, "Status Updated", Toast.LENGTH_SHORT).show()

            }.addOnFailureListener {
                Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
            }
    }
}