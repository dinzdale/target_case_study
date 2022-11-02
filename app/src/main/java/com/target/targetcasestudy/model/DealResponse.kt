package com.target.targetcasestudy.model


import com.google.gson.annotations.SerializedName

data class DealResponse(
    @SerializedName("products")
    val products: List<Product>?
)