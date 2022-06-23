package com.software.core_network_impl.call_adapters

import com.software.core_utils.models.ServerResponse
import okhttp3.Request
import okio.IOException
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class ServerResponseCall<T>(
    private val delegate: Call<T>,
) : Call<ServerResponse<T>> {

    companion object {
        private const val DOESNT_SUPPORT_EXECUTE_MESSAGE =
            "ServerResponseCall doesn't support execute"
    }

    override fun enqueue(callback: Callback<ServerResponse<T>>) {
        delegate.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                response.let {
                    when (it.isSuccessful) {
                        true -> {
                            val body = it.body()
                            if (body != null) {
                                callback.onResponse(
                                    this@ServerResponseCall,
                                    Response.success(ServerResponse.Success(body))
                                )
                            } else {
                                callback.onResponse(
                                    this@ServerResponseCall,
                                    Response.success(ServerResponse.Error(NullPointerException()))
                                )
                            }
                        }

                        else -> {
                            callback.onResponse(
                                this@ServerResponseCall,
                                Response.success(ServerResponse.Error(HttpException(it)))
                            )
                        }
                    }
                }
            }

            override fun onFailure(call: Call<T>, throwable: Throwable) {
                callback.onResponse(
                    this@ServerResponseCall,
                    Response.success(ServerResponse.Error(IOException(throwable)))
                )
            }
        })
    }


    override fun clone() = ServerResponseCall(delegate.clone())

    override fun execute() =
        throw UnsupportedOperationException(DOESNT_SUPPORT_EXECUTE_MESSAGE)


    override fun isExecuted(): Boolean = delegate.isExecuted

    override fun cancel() = delegate.cancel()

    override fun isCanceled(): Boolean = delegate.isCanceled
    override fun request(): Request = delegate.request()
    override fun timeout(): Timeout = delegate.timeout()

}
