package com.software.core_network_impl.call_adapters

import com.software.feature_api.models.ServerResponse
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ServerResponseCallAdapterFactory: CallAdapter.Factory() {

    companion object {
        private const val CHECK_RETURN_TYPE_FAILURE =
            "return type must be parameterized as Call<ServerResponse<<Foo>> or Call<ServerResponse<out Foo>>"

        private const val CHECK_RESPONSE_TYPE_FAILURE =
            "Response must be parameterized as ServerResponse<Foo> or ServerResponse<out Foo>"
    }

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if(Call::class.java != getRawType(returnType)) {
            return null
        }

        check(returnType is ParameterizedType) {
            CHECK_RETURN_TYPE_FAILURE
        }

        val responseType = getParameterUpperBound(0, returnType)

        if(getRawType(responseType) != ServerResponse::class.java) {
            return null
        }

        check(responseType is ParameterizedType) {
            CHECK_RESPONSE_TYPE_FAILURE
        }

        val successBodyType = getParameterUpperBound(0, responseType)

        return ServerResponseCallAdapter<Any>(successBodyType)
    }
}
