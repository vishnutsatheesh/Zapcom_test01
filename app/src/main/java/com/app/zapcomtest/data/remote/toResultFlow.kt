package com.app.zapcomtest.data.remote


import android.content.Context
import com.app.zapcomtest.utils.Constants.Companion.API_INTERNET_MESSAGE
import com.app.zapcomtest.utils.NetWorkResult
import com.app.zapcomtest.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response


inline fun <reified T> toResultFlow(
    context: Context,
    crossinline call: suspend () -> Response<T>?
): Flow<NetWorkResult<T>> {
    return flow {
        val isInternetConnected = Utils.hasInternetConnection(context)
        if (isInternetConnected) {
            emit(NetWorkResult.Loading(true))
            val c = call()
            c?.let { response ->
                response.isSuccessful
                try {
                    if (c.isSuccessful && c.body() != null) {
                        c.body()?.let {
                            emit(NetWorkResult.Success(it))
                        }
                    } else {
                        emit(NetWorkResult.Error(emptyList<Any>() as T, response.message()))
                    }
                } catch (e: Exception) {
                    emit(NetWorkResult.Error(emptyList<Any>() as T, e.toString()))
                }
            }
        } else {
            emit(NetWorkResult.Error(emptyList<Any>() as T, API_INTERNET_MESSAGE))
        }
    }.flowOn(Dispatchers.IO)
}

