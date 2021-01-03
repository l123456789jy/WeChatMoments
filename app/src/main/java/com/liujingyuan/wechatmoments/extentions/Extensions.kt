package com.liujingyuan.wechatmoments.extentions

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import coil.request.Disposable


/**
 * 判断对象是否为空
 * @param right 对象不为空时执行的方法
 * @param isNull 对象为空时执行的方法
 */
inline fun <T> T.mLet(notNull: (T) -> Unit, isNull: () -> Unit) {
    if (this != null)
        notNull(this)
    else isNull()
}
/**
**
* 判断对象是否为空
*/
inline fun <T> T.mLet(notNull: (T) -> Unit) {
    if (this != null)
        notNull(this)
}
/**
 * thenWithReturn 可单独使用，也可搭配elseWithReturn使用，但是不能和elseNoResult一起使用
 */
infix fun <T> Boolean.thenWithReturn(action: () -> T): T? =
    if (this) action.invoke() else null

infix fun <T> T?.elseWithReturn(action: () -> T): T =
    this ?: action.invoke()

/**
 * thenNoResult 可单独使用，也可搭配elseNoResult使用，但是不能和thenWithReturn一起使用
 */
infix fun Boolean.thenNoResult(action: () -> Unit): Boolean {
    if (this)
        action.invoke()
    return this
}

infix fun Boolean.elseNoResult(action: () -> Unit) {
    if (!this) action.invoke()
}


fun ByteArray.toInt() = (this[3].toInt() and 0xFF or
    (this[2].toInt() and 0xFF shl 8) or (
    this[1].toInt() and 0xFF shl 16) or (
    this[0].toInt() and 0xFF shl 24))


fun <T> T.getCurrentTime() = System.currentTimeMillis() / 1000


inline fun <reified V : View> ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): V {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot) as V
}

fun <T> unsafeLazy(initializer: () -> T) = lazy(LazyThreadSafetyMode.NONE, initializer)

inline fun <reified T : ViewModel> FragmentActivity.bindViewModel() = unsafeLazy {
    ViewModelProviders.of(this).get(T::class.java)
}

inline fun <reified R : Any> Array<*>.findInstance(): R? = find { it is R } as R?

inline val AndroidViewModel.context: Context
    get() = getApplication()

fun <V : View> Activity.bindView(@IdRes id: Int) = unsafeLazy { findViewById<V>(id) }

fun Int.dp(context: Context): Float {
    return this * context.resources.displayMetrics.density
}

/**
 * list 分页
 */
fun <T> limitPage(list: List<T>, page: Int, size: Int): List<T> {
    return when {
        list.size < page * size -> emptyList()
        list.size >= page * size && list.size <= (page + 1) * size -> list.subList(
            page * size,
            list.size
        )
        else -> list.subList(page * size, (page + 1) * size)
    }
}



