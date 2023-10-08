package com.example.kamalsweetsadmin.Model

class AllOrderModel {
    var name: String? = ""
    var orderId: String? = ""
    var userId: String? = ""
    var status: String? = ""
    var productId: String? = ""
    var price: String? = ""
    var userName: String = ""
    var userAddress: String = ""
    var productQuantity: String = ""
    var paymentStatus: String = ""
    var deliveryPersonName:String=""
    var deliveryPersonNumber:String=""

    constructor()
    constructor(
        name: String?,
        orderId: String?,
        userId: String?,
        status: String?,
        productId: String?,
        price: String?,
        userName: String,
        userAddress: String,
        productQuantity: String,
        paymentStatus: String,
        deliveryPersonName:String,
        deliveryPersonNumber:String
    ) {
        this.name = name
        this.orderId = orderId
        this.userId = userId
        this.status = status
        this.productId = productId
        this.price = price
        this.userName = userName
        this.userAddress = userAddress
        this.productQuantity = productQuantity
        this.paymentStatus = paymentStatus
        this.deliveryPersonName=deliveryPersonName
        this.deliveryPersonNumber=deliveryPersonNumber
    }

}
