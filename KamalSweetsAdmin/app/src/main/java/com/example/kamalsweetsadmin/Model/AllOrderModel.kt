package com.example.kamalsweetsadmin.Model

data class AllOrderModel(
    val name:String?="",
    val orderId:String?="",
    val userId:String?="",
    var status:String?="",
    val productId:String?="",
    val price:String?="",
    val userName:String="",
    val userAddress:String="",
    val productQuantity:String="",

    )
