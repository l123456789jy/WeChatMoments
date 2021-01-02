package com.liujingyuan.wechatmoments.model.adapter

import android.widget.ImageView
import coil.load
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.liujingyuan.wechatmoments.R
import com.liujingyuan.wechatmoments.model.MomentEnty

class CommentAdapter(layoutResId: Int, data: MutableList<MomentEnty.CommentsBean>?) :
    BaseQuickAdapter<MomentEnty.CommentsBean, BaseViewHolder>(layoutResId, data) {
    override fun convert(holder: BaseViewHolder, item: MomentEnty.CommentsBean) {
        holder.setText(R.id.tv_moment_content, item.content)
        holder.setText(R.id.tv_moment_name,"${item.sender.nick}: ")
        holder.getView<ImageView>(R.id.iv_moment_avatar).load(item.sender.avatar)
    }

}