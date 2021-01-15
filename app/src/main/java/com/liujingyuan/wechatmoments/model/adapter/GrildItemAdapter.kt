package com.liujingyuan.wechatmoments.model.adapter

import android.widget.ImageView
import coil.load
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.liujingyuan.wechatmoments.R
import com.liujingyuan.wechatmoments.model.MomentEnty

//  image_nine_grid_item
class GrildItemAdapter(layoutResId: Int, data: MutableList<MomentEnty.ImagesBean>?) :
    BaseQuickAdapter<MomentEnty.ImagesBean, BaseViewHolder>(layoutResId, data) {
    override fun convert(holder: BaseViewHolder, item: MomentEnty.ImagesBean) {
        holder.getView<ImageView>(R.id.image_item).load(item.url)
    }

}