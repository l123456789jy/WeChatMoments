package com.liujingyuan.wechatmoments.view

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import coil.load
import com.liujingyuan.wechatmoments.R
import com.liujingyuan.wechatmoments.base.activity.BaseActivity
import com.liujingyuan.wechatmoments.databinding.ActivityMainBinding
import com.liujingyuan.wechatmoments.model.MomentEnty
import com.liujingyuan.wechatmoments.model.adapter.MomentAdapter
import com.liujingyuan.wechatmoments.viewmodel.WeChatViewModel

/**
 *main
 */
class WeChatMomentActivity : BaseActivity<WeChatViewModel>() {
    //这里的ActivityMainBinding是布局的名字驼峰activity_main
    val mBinding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var iv_moments_head: ImageView;
    private lateinit var iv_user_avatar: ImageView;
    private lateinit var tv_user_name: TextView;
    private lateinit var momentHead: View
    override fun providerLayoutView(): View {
        return mBinding.root;
    }

    override fun providerViewModel(): Class<WeChatViewModel>? = WeChatViewModel::class.java


    override fun initWidget() {
        super.initWidget()
        momentHead =
            LayoutInflater.from(this).inflate(R.layout.moments_head, mBinding.momentRv, false)
        iv_moments_head = momentHead.findViewById<ImageView>(R.id.iv_moments_head)
        iv_user_avatar = momentHead.findViewById<ImageView>(R.id.iv_user_avatar)
        tv_user_name = momentHead.findViewById<TextView>(R.id.tv_user_name)
        mBinding.sw.setColorSchemeColors(
            Color.BLUE,
            Color.GREEN,
            Color.YELLOW,
            Color.RED
        );
        mBinding.sw.setOnRefreshListener {

        }
    }


    override fun fetchData() {
        var momentAdapter = MomentAdapter(mutableListOf<MomentEnty>())
        momentAdapter.addHeaderView(momentHead, 0)
        mBinding.momentRv.adapter = momentAdapter
        var loadeUserInfo = mViewModel?.loadeUserInfo("jsmith")
        loadeUserInfo?.observe(this, {
            iv_moments_head.load(it?.body?.profileImage)
            iv_user_avatar.load(it?.body?.avatar)
            tv_user_name.text = it?.body?.nick
        })
    }

    override fun isSetInnerLayoutFullScreen(): Boolean {
        return true
    }

    override fun subscribeUi() {
        super.subscribeUi()
    }

}