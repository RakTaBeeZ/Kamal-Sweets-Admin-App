package com.example.kamalsweetsadmin.Model

data class AddProductModels (
    val productCategory:String?="",
    val productCoverImage:String?="",
    val productDiscription:String?="",
    val productImages:ArrayList<String>?= ArrayList(),
    val productMRP:String?="",
    val productName:String?="",
    val productSp:String?="",
    val produductID:String?=""

    )