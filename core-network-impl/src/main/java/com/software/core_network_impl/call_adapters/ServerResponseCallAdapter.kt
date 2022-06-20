package com.software.core_network_impl.call_adapters

import com.software.feature_api.models.ServerResponse
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class ServerResponseCallAdapter<T>(
    private val successType: Type
) : CallAdapter<T, Call<ServerResponse<T>>> {

    override fun responseType(): Type = successType

    override fun adapt(call: Call<T>): Call<ServerResponse<T>> =
        ServerResponseCall(call)
}
