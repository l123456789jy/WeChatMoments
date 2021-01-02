package com.liujingyuan.wechatmoments.model.adapter


import android.widget.ImageView
import coil.load
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.liujingyuan.wechatmoments.R
import com.liujingyuan.wechatmoments.model.MomentEnty
import com.liujingyuan.wechatmoments.widget.ImageNineGridLayout

/**
 * 朋友圈列表
 */
class MultipleTypesItemProvider : BaseItemProvider<MomentEnty>() {

    companion object {
        const val IMAGE_TEX_TYPE = 1
    }

    override val itemViewType: Int
        get() = IMAGE_TEX_TYPE
    override val layoutId: Int
        get() = R.layout.moment_item

    override fun convert(helper: BaseViewHolder, item: MomentEnty) {
        item?.images?.let {
            if (it.size >= 1) {
                helper.setGone(R.id.layout_image, false)
                helper.getView<ImageNineGridLayout>(R.id.layout_image).render(it)
            } else {
                helper.setGone(R.id.layout_image, true)
            }
        } ?: kotlin.run {
            helper.setGone(R.id.layout_image, true)
        }
        helper.setText(R.id.tv_moment_content, item.content)
        helper.setText(R.id.tv_moment_name, item.sender.nick)
        helper.getView<ImageView>(R.id.iv_moment_avatar).load(item.sender.avatar)

    }


}