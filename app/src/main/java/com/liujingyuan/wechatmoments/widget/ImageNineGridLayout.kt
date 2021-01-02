package com.liujingyuan.wechatmoments.widget

import android.content.Context
import android.media.Image
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import coil.load

import com.github.mzule.ninegridlayout.AbstractNineGridLayout
import com.liujingyuan.wechatmoments.R
import com.liujingyuan.wechatmoments.model.MomentEnty

class ImageNineGridLayout(context: Context, attrs: AttributeSet) :
    AbstractNineGridLayout<MutableList<MomentEnty.ImagesBean>>(context, attrs) {

    private var mClickListener: ImageNineGridViewClickListener? = null
    private var mImageViews: Array<ImageView>? = null

    override fun fill() {
        fill(R.layout.image_nine_grid_item)
        mImageViews = findInChildren(R.id.image_item, ImageView::class.java)
    }

    override fun render(images: MutableList<MomentEnty.ImagesBean>?) {
        images ?: return
        setDisplayCount(images.size)
        for (i in images.indices) {
            if (i >= 9) {
                return
            }
            val url = images[i].url
            mImageViews?.get(i)?.let {
                it.load(url)
                it.setOnClickListener(OnImageViewClickListener(i))
            }
        }
    }

    private inner class OnImageViewClickListener(private val index: Int) : OnClickListener {

        override fun onClick(v: View) {
            mClickListener?.onImageViewClick(v, index)
        }
    }

    interface ImageNineGridViewClickListener {
        fun onImageViewClick(onClickView: View, index: Int)
    }

    fun setImageNineGridViewClickListener(listener: ImageNineGridViewClickListener) {
        mClickListener = listener
    }


}