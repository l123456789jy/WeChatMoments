package com.liujingyuan.wechatmoments.http

import android.util.Log
import com.liujingyuan.wechatmoments.Constants.Companion.BASE_URL
import com.liujingyuan.wechatmoments.api.AppApi
import com.liujingyuan.wechatmoments.model.adapter.LiveDataCallAdapterFactory
import com.liujingyuan.wechatmoments.utiles.HttpsCerUtils
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * App网络管理类
 */
object HttpManger {
    const val TAG = "HttpManger"

    // time out
    private const val timeout = 20

    // init OkHttp
    private var client: OkHttpClient

    init {
        var logging = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
            Log.e(TAG, it)
        })
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        val okHttpClientBuilder = OkHttpClient.Builder()
            .connectTimeout(timeout.toLong(), TimeUnit.SECONDS)
            .readTimeout(timeout.toLong(), TimeUnit.SECONDS)
            .writeTimeout(timeout.toLong(), TimeUnit.SECONDS)
            .addInterceptor(logging)
        //允许http请求
        HttpsCerUtils.setTrustAllCertificate(okHttpClientBuilder)
        client = okHttpClientBuilder.build()
    }

    //  init Retrofit
    val retrofit: Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(LiveDataCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api: AppApi by lazy { createService(AppApi::class.java) }

   open val httpApi: AppApi
        get() = api

    private fun <T> createService(serviceClass: Class<T>): T {
        return retrofit.create(serviceClass)
    }

}