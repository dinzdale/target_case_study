package com.target.targetcasestudy.api

import com.target.targetcasestudy.model.DealsResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface DealApiRx {
    @GET("${BASE_URL}deals")
    fun retrieveDeals(): Single<DealsResponse>

    @GET("${BASE_URL}deals/{dealId}")
    fun retrieveDeal(@Path("dealId") dealId: String): Single<DealsResponse>
}
