package com.liujingyuan.wechatmoments.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.liujingyuan.wechatmoments.R
import com.liujingyuan.wechatmoments.base.activity.BaseActivity
import com.liujingyuan.wechatmoments.viewmodel.WeChatViewModel

/**
 *main
 */
class WeChatMomentActivity : BaseActivity<WeChatViewModel>() {

    override fun providerLayoutId(): Int = R.layout.activity_main

    override fun providerViewModel(): Class<WeChatViewModel>? = WeChatViewModel::class.java


    override fun initWidget() {
        super.initWidget()

    }

    override fun subscribeUi() {
        super.subscribeUi()
        var loadeUserInfo = mViewModel?.loadeUserInfo("jsmith")
        loadeUserInfo?.observe(this, Observer {
            it?.body?.profileImage?.let { it1 -> Log.e("subscribeUi", it1) }
        })
    }

}