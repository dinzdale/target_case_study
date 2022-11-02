package com.target.targetcasestudy.api

import com.target.targetcasestudy.model.DealsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

internal const val BASE_URL = "https://api.target.com/mobile_case_study_deals/v1/"

interface DealApi {

  @GET("${BASE_URL}deals")
  fun retrieveDeals(): Call<DealsResponse>

  @GET("${BASE_URL}deals/{dealId}")
  fun retrieveDeal(@Path("dealId") dealId: String): Call<DealsResponse>
}
