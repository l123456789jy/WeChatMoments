package com.liujingyuan.wechatmoments.api

import androidx.lifecycle.LiveData
import com.liujingyuan.wechatmoments.base.BaseResponse
import com.liujingyuan.wechatmoments.model.User
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * app的网络请求接口
 */
interface AppApi {
    @GET("user/{userId}")
    fun getUserInfo(
        @Path("userId") userId: String
    ): LiveData<BaseResponse<User>>

}