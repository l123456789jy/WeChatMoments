package com.liujingyuan.wechatmoments.base.activity

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModelProvider
import com.gyf.immersionbar.ktx.immersionBar
import com.liujingyuan.wechatmoments.R
import com.liujingyuan.wechatmoments.databinding.ActivityMainBinding

import com.liujingyuan.wechatmoments.viewmodel.base.BaseViewModel


/**
 * 使用了lifecycle的activity
 */
abstract class MvvmActivity<V : BaseViewModel> : AppCompatActivity() {
    protected var mViewModel: V? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isSetInnerLayoutFullScreen())
            setInnerLayoutFullScreen()
        setContentView(providerLayoutView())
        getBundleData()
        initVm()
        initWidget()
        fetchData()
        setObserver()
        subscribeUi()
    }

    private fun initVm() {
        providerViewModel()?.let {
            mViewModel =ViewModelProvider(this)[it]
        }
    }

    /**
     * 获取数据
     */
    abstract fun fetchData()

    /**
     * 获取bundle传入的数据
     */
    open fun getBundleData() {}


    open fun setTitleString(title:String){

    }

    open fun setTitleString(@StringRes title:Int){

    }
    /**
     * 是否显示toolbar上的返回箭头
     */
    open fun setHomeAsUpEnabled(): Boolean = false

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var menuId = providerMenuId()
        if (menuId > 0) {
            menuInflater.inflate(menuId, menu)
            return true
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                this.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        this.finish()
    }

    override fun onResume() {
        super.onResume()
    }

    /**
     * 禁止截屏
     */
    open fun setNoScreenShot() = false

    /**
     * 布局文件
     */
    abstract fun providerLayoutView(): View

    /**
     * 绑定viewmodel
     */
    open fun providerViewModel(): Class<V>? = null


    /**
     * 提供viewmodel 的factory
     */
    open fun providerFactory(): ViewModelProvider.NewInstanceFactory? = null

    /**
     * 是否有toolbar
     */

    open fun providerToolbar(): Toolbar? = null

    /**
     * menu id
     */
    open fun providerMenuId(): Int = -1

    /**
     * 初始化一些控件
     */
    open fun initWidget() {}

    /**
     * 设置控件的监听器
     */

    /**
     * 提供依赖activity生命周期的Oberver
     */
    open fun provideObserver(): LifecycleObserver? = null

    /**
     * 状态栏高度
     */
    fun getStatusBarHeight(): Int {
        var statusBarHeight = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = resources.getDimensionPixelSize(resourceId)
        }
        return statusBarHeight
    }

    /**
     *设置对属性的观察
     */
    open fun subscribeUi() {}

    private fun setObserver() {
        provideObserver()?.let { lifecycle.addObserver(it) }
    }

    fun setOnClickListener(vararg views: View, listener: View.OnClickListener) {
        for (v in views) {
            v.setOnClickListener(listener)
        }
    }

    /**
     * 是否设置Activity是否全屏，内容穿过Status bar
     */
    open fun isSetInnerLayoutFullScreen() = false

    open fun isSetFullScreen() = false

    /**c
     * 设置activity为沉浸模式
     */
    private fun setInnerLayoutFullScreen() {
        immersionBar {
            statusBarDarkFont(true)
            navigationBarDarkIcon(true)
            transparentBar()
            transparentNavigationBar()
            transparentStatusBar()
            fullScreen(true)
        }
    }

    protected fun setFullScreen() {}

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus && isSetFullScreen()) hideSystemUI()
    }

    private fun hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }


}
