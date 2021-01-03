package com.liujingyuan.wechatmoments.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.liujingyuan.wechatmoments.api.ApiResponse
import com.liujingyuan.wechatmoments.extentions.limitPage
import com.liujingyuan.wechatmoments.http.HttpManger
import com.liujingyuan.wechatmoments.model.MomentEnty
import com.liujingyuan.wechatmoments.model.User
import com.liujingyuan.wechatmoments.viewmodel.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WeChatViewModel : BaseViewModel() {
    val pagingData: MutableLiveData<List<MomentEnty>> by lazy { MutableLiveData<List<MomentEnty>>() }
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

    fun getMomentPagingData(page: Int) {
        Log.e("getMomentPagingData", page.toString())
        viewModelScope.launch {
            if (::twitterList.isInitialized) {
                var pageData = limitPage(twitterList?.value?.body as List<MomentEnty>  , page, 6)
                delay(1000)
                pagingData.postValue(pageData)
            }
        }
    }



}