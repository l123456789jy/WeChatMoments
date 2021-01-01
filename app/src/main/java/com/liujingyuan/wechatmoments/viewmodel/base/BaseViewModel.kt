package com.liujingyuan.wechatmoments.viewmodel.base

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    /**
     * 用于显示的toast
     */
    val toastMsg: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val toastLongMsg: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    /**
     * toast string id
     */
    val toastResId: MutableLiveData<Int> by lazy { MutableLiveData<Int>() }

    val toastLongResId: MutableLiveData<Int> by lazy { MutableLiveData<Int>() }
    /**
     * 用于显示网络请求的dialog
     */
    val showLoadingDialog: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    /**
     * 用于显示网络请求的dialog +信息
     */
    val showLoadingDialogWithMsg: MutableLiveData<Pair<Boolean, Int>> by lazy {
        MutableLiveData<Pair<Boolean, Int>>()
    }
    /**
     *显示 SwipeRefreshLayout
     */
    val showRefreshIcon: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    /**
     *  没有错误或者中断
     */
    val netWorkError: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    /**
     * 网络请求时判断是否登录超时
     */
    val loginOverTime: MutableLiveData<Int> by lazy { MutableLiveData<Int>() }
    /**
     * 自定义toast
     */
    val customizeToast: MutableLiveData<Int> by lazy { MutableLiveData<Int>() }
    val customizeToastStr: MutableLiveData<String> by lazy { MutableLiveData<String>() }

    open fun onDestroy() {}
    /**
     * 显示loading dialog和默认文字
     */
    fun setDialogShow(isShow: Boolean) {
        showLoadingDialog.value = isShow
    }

    /**
     * 显示loading dialog和特定文字
     */
    fun setDialogShowWithMsg(isShow: Boolean, @StringRes msg: Int = 0) {
        showLoadingDialogWithMsg.value = Pair(isShow, msg)
    }

    fun setToastText(text: String, duration: Int = Toast.LENGTH_SHORT) {
        if (duration == Toast.LENGTH_SHORT){
            toastMsg.value = text
        }else{
            toastLongMsg.value = text
        }
    }

    fun setToastResId(@StringRes resId: Int, duration: Int = Toast.LENGTH_SHORT) {
        if (duration == Toast.LENGTH_SHORT){
            toastResId.value = resId
        }else{
            toastLongResId.value = resId
        }
    }

    fun setRefreshIconVisibility(show: Boolean) {
        showRefreshIcon.value = show
    }

    fun onDestory() {

    }
}