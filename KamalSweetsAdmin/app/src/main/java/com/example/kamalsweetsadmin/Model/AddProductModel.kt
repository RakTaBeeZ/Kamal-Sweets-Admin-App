package com.example.kamalsweetsadmin.Model

data class AddProductModel (
    var ProductName:String?="",
    var ProductDiscription:String?="",
    var ProductCoverImage:String?="",
    var productCategory:String?="",
    var ProduductID:String?="",
    var ProductMRP:String?="",
    var ProductSp:String?="",
    var ProductImage:ArrayList<String>,
    var productUnit:String="",
    var stockStatus:String=""

    )