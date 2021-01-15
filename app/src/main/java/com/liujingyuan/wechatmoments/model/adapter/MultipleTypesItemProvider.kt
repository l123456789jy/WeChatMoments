package com.liujingyuan.wechatmoments.model.adapter


import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.chad.library.adapter.base.module.LoadMoreModule
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
        var grildRV = helper.getView<RecyclerView>(R.id.rv_grid)
        item?.images?.let {
            if (it.size >= 1) {
                var grildItemAdapter = GrildItemAdapter(R.layout.image_nine_grid_item, it)
                grildRV.adapter = grildItemAdapter
                grildRV.visibility = View.VISIBLE
                /* helper.setGone(R.id.layout_image, false)
                 helper.getView<ImageNineGridLayout>(R.id.layout_image).render(it)*/
            } else {
                grildRV.visibility = View.GONE
//                helper.setGone(R.id.layout_image, true)
            }
        } ?: kotlin.run {
            grildRV.visibility = View.GONE
//            helper.setGone(R.id.layout_image, true)
        }
        helper.setText(R.id.tv_moment_content, item.content)
        helper.setText(R.id.tv_moment_name, item.sender.nick)
        helper.getView<ImageView>(R.id.iv_moment_avatar).load(item.sender.avatar)

        item?.comments?.let {
            if (it.size <= 0) {
                helper.setGone(R.id.comments_view, true)
                return
            }
            helper.setGone(R.id.comments_view, false)
            var commentAdapter = CommentAdapter(R.layout.moment_comments_item, it)
            helper.getView<RecyclerView>(R.id.comments_view).adapter = commentAdapter
        } ?: kotlin.run {
            helper.setGone(R.id.comments_view, true)
        }

    }


}