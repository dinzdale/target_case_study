package com.target.targetcasestudy.api


import com.google.gson.annotations.SerializedName
import com.target.targetcasestudy.model.Deal

data class DealResponse(
  @SerializedName("products")
  val deals: List<Deal>
)
