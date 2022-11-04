package com.target.targetcasestudy.api

import com.target.targetcasestudy.model.DealsResponse
import com.target.targetcasestudy.model.Product
import retrofit2.http.GET
import retrofit2.http.Path

interface DealApiKtx {


  @GET("${BASE_URL}deals")
  suspend fun retrieveDeals(): DealsResponse

  @GET("${BASE_URL}deals/{dealId}")
  suspend fun retrieveDeal(@Path("dealId") dealId: Int): Product
}
