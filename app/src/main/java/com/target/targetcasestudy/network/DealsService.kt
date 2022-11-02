package com.target.targetcasestudy.network

import android.content.Context

import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import com.target.targetcasestudy.BuildConfig
import com.target.targetcasestudy.api.BASE_URL
import com.target.targetcasestudy.api.DealApiKtx
import com.target.targetcasestudy.api.DealResponse
import com.target.targetcasestudy.model.Deal
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


class DealsService(val context: Context) {

    private val tag = DealsService::class.java.simpleName

    //private val domain = context.getString(R.string.schema)
    private val client: OkHttpClient by lazy {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(loggingInterceptor).addInterceptor(OkHttpProfilerInterceptor())
        }
        builder.build()
    }
    private val retrofit: Retrofit = Retrofit.Builder().baseUrl(domain).client(client)
        .addConverterFactory(GsonConverterFactory.create()).build()
    private val apiService: DealApiKtx = retrofit.create(DealApiKtx::class.java)
    private val headerMap = hashMapOf<String, String>(
        "User-Agent" to context.applicationInfo.packageName,
        //"X-API-Key" to context.getString(R.string.api_key)
    )

    enum class DistanceUnit(val unitS: String) {
        MILES("Miles"),
        KILOS("KM")
    }


    suspend fun retrieveDeals(): Result<DealResponse> {
        return try {
            Result.success(apiService.retrieveDeals())
        } catch (ex: Exception) {
            Result.failure(ex)
        }

    }


    suspend fun retrieveDeal(@Path("dealId") dealId: String): Result<Deal> {
        return try {
            Result.success(apiService.retrieveDeal(dealId))
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }
}

