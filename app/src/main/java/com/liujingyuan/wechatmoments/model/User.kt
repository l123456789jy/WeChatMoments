package com.liujingyuan.wechatmoments.model

import com.google.gson.annotations.SerializedName

/**
 * 用户信息
 * @property avatar String?
 * @property nick String?
 * @property profileImage String?
 * @property username String?
 * @constructor
 */
data class User(
    @SerializedName("profile-image")
    var profileImage: String?="",
    var avatar: String?="",
    var nick: String?="",
    var username: String?=""
)

