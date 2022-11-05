package com.target.targetcasestudy.model

import com.google.gson.Gson
import retrofit2.HttpException

data class ItemNotFoundResponse(
    val code: String,
    val message: String
)

fun <T> Exception.getErrorResponse(httpCode: Int, clazz: Class<T>): T? {
    return if (this is HttpException && code() == httpCode) {
        try {
            Gson().fromJson(response()?.errorBody()?.string(), clazz)
        } catch (ex: Exception) {
            null
        }
    } else {
        null
    }
}