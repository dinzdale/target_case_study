package com.target.targetcasestudy.model


import com.google.gson.annotations.SerializedName

data class DealsResponse(
  @SerializedName("products")
  val products: List<Product>
)
