package com.liujingyuan.wechatmoments.base.fragment

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.UserManager
import android.view.View
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import com.liujingyuan.wechatmoments.R
import com.liujingyuan.wechatmoments.viewmodel.base.BaseViewModel


abstract class BaseFragment<V : BaseViewModel> : MvvmFragment<V>() {
    private var mToast: Toast? = null
    var isVisibleToUser = false

    private val mPageName = this.javaClass.canonicalName

    open fun isManualStatistics() = false


    override fun onResume() {
        super.onResume()
    }


    override fun onPause() {
        super.onPause()
    }

    protected fun sendStatistics(eventName: String) {

    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.isVisibleToUser = isVisibleToUser
    }

    private fun showLoginOverTimeDialog(code: Int) {

    }

    @SuppressLint("ResourceType")
    fun showMRequestDialog(@StringRes msg: Int = 0) {

    }


    override fun onDestroy() {
        super.onDestroy()
    }

    open fun pageName(): Int = 0
    private fun showMToast(msg: String, duration: Int = Toast.LENGTH_SHORT) {
        if (mToast == null) {
            mToast = Toast.makeText(activity?.applicationContext, msg, duration).also { it.show() }
        } else {
            mToast!!.duration = duration
            mToast!!.setText(msg)
            if (!mToast!!.view?.isShown!!)
                mToast?.show()
        }
    }


    private fun showMToast(@StringRes msg: Int, duration: Int = Toast.LENGTH_SHORT) {
        if (mToast == null) {
            mToast = Toast.makeText(activity?.applicationContext, msg, duration).also { it.show() }
        } else {
            mToast!!.duration = duration
            mToast!!.setText(msg)
            if (!mToast!!.view?.isShown!!)
                mToast?.show()
        }
    }

    /**
     *设置对属性的观察
     */
    @CallSuper
    override fun subscribeUi() {
        mViewModel?.apply {
            toastMsg.observe(this@BaseFragment, Observer<String> { msgId ->
                showMToast(msgId)
            })
            toastLongMsg.observe(this@BaseFragment, Observer { msg ->
                showMToast(msg, Toast.LENGTH_LONG)
            })
            toastResId.observe(this@BaseFragment, Observer { resId ->
                showMToast(resId)
            })

            toastLongResId.observe(this@BaseFragment, Observer { resId ->
                showMToast(resId, Toast.LENGTH_LONG)
            })
            customizeToast.observe(this@BaseFragment, Observer { msg ->

            })
            customizeToastStr.observe(this@BaseFragment, Observer { msg ->

            })
            showLoadingDialog.observe(this@BaseFragment, Observer { isShow ->

            })
            showLoadingDialogWithMsg.observe(this@BaseFragment, Observer { isShowInfo ->

            })

        }
    }

    open fun setOnClickListener(vararg views: View, listener: View.OnClickListener) {
        try {
            for (v in views) {
                v.setOnClickListener(listener)
            }
        } catch (e: Exception) {
        }

    }
}