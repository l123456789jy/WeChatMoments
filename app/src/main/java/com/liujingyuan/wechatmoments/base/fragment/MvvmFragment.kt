package com.liujingyuan.wechatmoments.base.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.liujingyuan.wechatmoments.viewmodel.base.BaseViewModel

/**
 *  BaseMvvmFragment
 * @param V : BaseViewModel
 * @property mAttachActivity FragmentActivity?
 * @property mViewModel V?
 * @property isViewInit Boolean
 * @property isUiVisible Boolean
 * @property isLoadData Boolean
 */
abstract class MvvmFragment<V : BaseViewModel> : Fragment() {

    private var mAttachActivity: FragmentActivity? = null
    protected var mViewModel: V? = null

    // 标识view 是否初始化完成
    private var isViewInit = false

    //是否可见
    private var isUiVisible = false

    // 是否加载数据
    var isLoadData = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mAttachActivity = if (context is FragmentActivity) context else null
    }

    override fun onDetach() {
        super.onDetach()
        mAttachActivity = null
    }

    open fun getAttachActivity() = mAttachActivity
    fun hasLoadData() = isLoadData

    /**`
     * 布局文件
     */
    abstract fun providerLayoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getBundleData()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(providerLayoutId(), null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initWidget()
    }

    open fun initWidget() {}

    /**
     * 提供继承于[ViewModel]的子类
     */
    open fun providerViewModel(): Class<V>? = null

    /**
     * 提供viewmodel 的factory
     */
    open fun providerFactory(): ViewModelProvider.NewInstanceFactory? = null

    /**
     *提供 [LifecycleObserver]
     */
    open fun providerLifecycleObserver(): LifecycleObserver? = null

    /**
     *设置对属性的观察
     */
    open fun subscribeUi() {}

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewModel()
        providerLifecycleObserver()?.let { lifecycle.addObserver(it) }
        subscribeUi()
        this.isViewInit = true
        // 防止一开始加载的时候未 调用 preLoadData 方法， 因为setUserVisibleHint 比 onActivityCreated 触发 前
        if (userVisibleHint) {
            preLoadData()
        }
    }

    open fun initViewModel() {
        providerViewModel()?.let {
            mViewModel =
                try {
                    if (activity != null)
                        initViewModel(activity!!, it)
                    if (getAttachActivity() != null)
                        initViewModel(getAttachActivity()!!, it)
                    else null
                } catch (e: Exception) {
                    null
                }
        }
    }

    private fun initViewModel(ac: FragmentActivity, cla: Class<V>): V {
        return ViewModelProvider(ac)[cla]
    }

    open fun getBundleData() {}

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            this.isUiVisible = isVisibleToUser
            preLoadData()
        }
    }

    /**
     * 当UI初始化成功，UI可见并且没有加载过数据的时候 加载数据
     */
    private fun preLoadData() {
        if (isViewInit && isVisible && !isLoadData) {
            isLoadData = true
            loadData()
        }
    }

    /**
     * 子类重写此方法加载数据
     */
    open fun loadData() {}

}