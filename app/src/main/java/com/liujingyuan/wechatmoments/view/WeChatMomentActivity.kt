package com.liujingyuan.wechatmoments.view

import android.animation.ArgbEvaluator
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import com.liujingyuan.wechatmoments.Constants.Companion.USER_ID
import com.liujingyuan.wechatmoments.R
import com.liujingyuan.wechatmoments.base.activity.BaseActivity
import com.liujingyuan.wechatmoments.databinding.ActivityMainBinding
import com.liujingyuan.wechatmoments.model.MomentEnty
import com.liujingyuan.wechatmoments.model.adapter.MomentAdapter
import com.liujingyuan.wechatmoments.viewmodel.WeChatViewModel


/**
 *main
 */
class WeChatMomentActivity : BaseActivity<WeChatViewModel>(), OnLoadMoreListener {
    //这里的ActivityMainBinding是布局的名字驼峰activity_main
    val mBinding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var iv_moments_head: ImageView;
    private lateinit var iv_user_avatar: ImageView;
    private lateinit var tv_user_name: TextView;
    private lateinit var momentHead: View
    private lateinit var momentAdapter:MomentAdapter
    var page=0
    var isLoadDataSucces=false
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
            fetchData()
        }
        mBinding.sw.post { mBinding.sw.isRefreshing = true }
        mBinding.momentRv.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                //不是静止的时候
                if (recyclerView.scrollState != RecyclerView.SCROLL_STATE_IDLE) {
                    val offset = recyclerView.computeVerticalScrollOffset()
                    changeTopBackgroundColor(offset)
                }
            }
        })
    }

    private fun changeTopBackgroundColor(distance: Int) {
        val maxDistance = resources.getDimensionPixelOffset(R.dimen.dp_250)
        val percent = distance.toDouble() / maxDistance
        if (percent > 1) {
            return
        }
        val argbEvaluator = ArgbEvaluator()
        val COLOR_START = argbEvaluator.evaluate(percent.toFloat(),
            ContextCompat.getColor(this,android.R.color.transparent), ContextCompat.getColor(this,android.R.color.darker_gray)) as Int
        mBinding.rlTitleBg.setBackgroundColor(COLOR_START)
    }



    override fun fetchData() {
        page=0
        isLoadDataSucces=false
         momentAdapter = MomentAdapter()
        if (momentHead.parent != null) {
            (momentHead.parent as ViewGroup).removeView(momentHead)
        }
        momentAdapter.setHeaderView(momentHead, 0)
        momentAdapter.loadMoreModule.setOnLoadMoreListener(this)
        momentAdapter.loadMoreModule.loadMoreToLoading();
        mBinding.momentRv.adapter = momentAdapter
        mViewModel?.loadeMomentListInfo(USER_ID)?.observe(this, {
            refreshComplete()
            mViewModel?.getMomentPagingData(page)
//            momentAdapter.addData( it?.body as MutableList<MomentEnty>)
        })
        mViewModel?.loadeUserInfo(USER_ID)?.observe(this, {
            refreshComplete()
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
        mViewModel?.pagingData?.observe(this, Observer {
            if (it.isEmpty()){
                momentAdapter.loadMoreModule.loadMoreEnd(false)
                return@Observer
            }
            momentAdapter.addData(it)
            momentAdapter.loadMoreModule.loadMoreComplete();
        })
    }

    private fun refreshComplete() {
        isLoadDataSucces=true
        mBinding.sw.isRefreshing = false
    }

    override fun onLoadMore() {
        if (isLoadDataSucces){
            page += 1
            mViewModel?.getMomentPagingData(page)
        }
    }


}