package com.liujingyuan.wechatmoments.viewmodel

import androidx.lifecycle.LiveData
import com.liujingyuan.wechatmoments.api.ApiResponse
import com.liujingyuan.wechatmoments.http.HttpManger
import com.liujingyuan.wechatmoments.model.MomentEnty
import com.liujingyuan.wechatmoments.model.User
import com.liujingyuan.wechatmoments.viewmodel.base.BaseViewModel

class WeChatViewModel : BaseViewModel() {
    /*viewModelScope.launch  {
        remoteApi.slowFetch()
    }*/
    lateinit var twitterList: LiveData<ApiResponse<List<MomentEnty>>>;
    lateinit var userData: LiveData<ApiResponse<User>>;
    fun loadeUserInfo(userId: String): LiveData<ApiResponse<User>> {
        userData = HttpManger.httpApi.getUserInfo(userId)
        return userData
    }

    fun loadeMomentListInfo(userId: String): LiveData<ApiResponse<List<MomentEnty>>> {
        twitterList = HttpManger.httpApi.loadeMomentListInfo(userId)
        return twitterList
    }
}