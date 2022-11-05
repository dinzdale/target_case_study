package com.target.targetcasestudy.network

import com.target.targetcasestudy.api.DealApiKtx
import com.target.targetcasestudy.model.ProductResponse
import com.target.targetcasestudy.model.Product
import retrofit2.Retrofit
import retrofit2.http.Path
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DealsService @Inject constructor(val retrofit: Retrofit) {

    private val tag = DealsService::class.java.simpleName

    val BASE_URL = "https://api.target.com/mobile_case_study_deals/v1/"

    private val apiService: DealApiKtx = retrofit.create(DealApiKtx::class.java)


    suspend fun retrieveDeals(): Result<ProductResponse> {
        return try {
            Result.success(apiService.retrieveDeals())
        } catch (ex: Exception) {
            Result.failure(ex)
        }

    }


    suspend fun retrieveDeal(@Path("dealId") dealId: Int): Result<Product> {
        return try {
            Result.success(apiService.retrieveDeal(dealId))
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }
}

