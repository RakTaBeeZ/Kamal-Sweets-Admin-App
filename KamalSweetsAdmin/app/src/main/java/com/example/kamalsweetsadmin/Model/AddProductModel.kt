package com.example.kamalsweetsadmin.Model

data class AddProductModel (
    val ProductName:String?="",
    val ProductDiscription:String?="",
    val ProductCoverImage:String?="",
    val productCategory:String?="",
    val ProduductID:String?="",
    val ProductMRP:String?="",
    val ProductSp:String?="",
    val ProductImage:ArrayList<String>,

    )