package com.liujingyuan.wechatmoments.model

import androidx.lifecycle.LiveData
import com.liujingyuan.wechatmoments.base.BaseResponse
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * 将返回的数据流转换成LiveData这样便自动有了生命周期的感知能力
 * 不用我们再去管理
 */
class LiveDataCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != LiveData::class.java) return null
        //获取第一个泛型类型
        val observableType = getParameterUpperBound(0, returnType as ParameterizedType)
        val rawType = getRawType(observableType)
        if (rawType != BaseResponse::class.java) {
            throw IllegalArgumentException("type must be ApiResponse")
        }
        if (observableType !is ParameterizedType) {
            throw IllegalArgumentException("resource must be parameterized")
        }
        return LiveDataCallAdapter<Any>(observableType)
    }
}