package com.target.targetcasestudy.network

import android.content.Context

import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import com.target.targetcasestudy.BuildConfig
import com.target.targetcasestudy.api.BASE_URL
import com.target.targetcasestudy.api.DealApiKtx
import com.target.targetcasestudy.model.DealsResponse
import com.target.targetcasestudy.model.DealResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Path
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DealsService @Inject constructor(val retrofit: Retrofit) {

    private val tag = DealsService::class.java.simpleName

    val BASE_URL = "https://api.target.com/mobile_case_study_deals/v1/"

    private val apiService: DealApiKtx = retrofit.create(DealApiKtx::class.java)


    suspend fun retrieveDeals(): Result<DealsResponse> {
        return try {
            Result.success(apiService.retrieveDeals())
        } catch (ex: Exception) {
            Result.failure(ex)
        }

    }


    suspend fun retrieveDeal(@Path("dealId") dealId: Int): Result<DealResponse> {
        return try {
            Result.success(apiService.retrieveDeal(dealId))
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }
}

