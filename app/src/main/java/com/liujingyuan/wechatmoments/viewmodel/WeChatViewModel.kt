package com.liujingyuan.wechatmoments.viewmodel

import com.liujingyuan.wechatmoments.http.HttpManger
import com.liujingyuan.wechatmoments.viewmodel.base.BaseViewModel

class WeChatViewModel : BaseViewModel() {
    fun loadeUserInfo(userId: String) = HttpManger.httpApi.getUserInfo(userId)
}