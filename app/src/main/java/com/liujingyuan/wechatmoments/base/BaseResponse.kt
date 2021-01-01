package com.liujingyuan.wechatmoments.base

/**
 *  返回数据的基类
 * @param T
 * @property data T?
 * @property errorCode Int
 * @property errorMsg String
 * @constructor
 */
class BaseResponse<T>(
    var data: T?,
    var errorCode: Int=-1,
    var errorMsg: String=""
)
