package com.liujingyuan.wechatmoments.base.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.liujingyuan.wechatmoments.viewmodel.base.BaseViewModel


/**
 */
abstract class BaseActivity<V : BaseViewModel> : MvvmActivity<V>() {

    private var mToast: Toast? = null
    private var mPageStartTime: Long = 0

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    private fun showLoginOverTimeDialog(code: Int) {

    }

    @SuppressLint("ResourceType")
    fun showMRequestDialog(@StringRes msg: Int = 0) {

    }

    private fun dismissMRequestDialog() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPageStartTime = System.currentTimeMillis()
    }


    open fun <T> onEvent(type: Int, data: T) {}


    override fun onDestroy() {

        super.onDestroy()
    }

    private fun initCustomToast() {

    }

    protected fun showMToast(msg: String, duration: Int = Toast.LENGTH_SHORT) {
        if (mToast == null) {
            mToast = Toast.makeText(applicationContext, msg, duration).also { it.show() }
        } else {
            mToast!!.setText(msg)
            if (!mToast!!.view?.isShown!!)
                mToast?.show()
        }
    }

    protected fun showMToast(@StringRes msg: Int, duration: Int = Toast.LENGTH_SHORT) {
        if (mToast == null) {
            mToast = Toast.makeText(applicationContext, msg, duration).also { it.show() }
        } else {
            mToast!!.setText(msg)
            if (!mToast!!.view?.isShown!!)
                mToast?.show()
        }
    }


    fun goPermissionSetting() {
        startActivity(Intent().apply {
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            data = Uri.fromParts("package", packageName, null)
        })
    }


    @CallSuper
    override fun subscribeUi() {
        mViewModel?.apply {
            toastMsg.observe(this@BaseActivity, Observer<String> { msgId ->
                showMToast(msgId)
            })

            toastLongMsg.observe(this@BaseActivity, Observer { msg ->
                showMToast(msg, Toast.LENGTH_LONG)
            })
            toastLongResId.observe(this@BaseActivity, Observer { resId ->
                showMToast(resId, Toast.LENGTH_LONG)
            })

            toastResId.observe(this@BaseActivity, Observer { resId ->
                showMToast(resId)
//                toast(resId!!)
            })
            customizeToast.observe(this@BaseActivity, Observer { msg ->

            })
            customizeToastStr.observe(this@BaseActivity, Observer { msg ->

            })
            showLoadingDialog.observe(this@BaseActivity, Observer { isShow ->
                if (isShow!!) showMRequestDialog() else dismissMRequestDialog()
            })
            showLoadingDialogWithMsg.observe(this@BaseActivity, Observer { isShowInfo ->

            })
            loginOverTime.observe(this@BaseActivity, Observer { goLogin ->
            })
        }
    }




    protected fun showInputMethod() {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
    }

    protected fun hiddenInputMethod() {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }

    protected fun showInputMethod(view: EditText) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    protected fun hideInputMethod(view: EditText) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}


/**
 * 设置默认屏幕亮度
 */
fun Activity.setDefaultBrightness() {
    setScreenBrightness(WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE)
}


fun Activity.setMaxBrightness() {
    setScreenBrightness(WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_FULL)
}

/**
 * 设置屏幕亮度
 */
fun Activity.setScreenBrightness(brightness: Float) {
    val attr = window.attributes
    attr.screenBrightness = brightness
    window.attributes = attr
}

fun Activity.getMColor(@ColorRes c: Int) = ContextCompat.getColor(this, c)

/**
 * 用于携带Bundle跳转activity
 */
interface ActivityArgs {

    /**
     * @return returns an intent that can be used to launch this activity.
     */
    fun intent(activity: Context): Intent

    /**
     * Launches the activity given your activity context.
     *
     * The default implementation uses the intent generated from [intent]
     */
    fun launch(activity: Context) = activity.startActivity(intent(activity))

    fun launchWithFinish(activity: Activity) {
        activity.startActivity(intent(activity))
        activity.finish()
    }

    fun launch(activity: Activity, requestCode: Int) =
        activity.startActivityForResult(intent(activity), requestCode)

    fun launch(fragment: Fragment, requestCode: Int) =
        fragment.startActivityForResult(intent(fragment.activity!!), requestCode)
}