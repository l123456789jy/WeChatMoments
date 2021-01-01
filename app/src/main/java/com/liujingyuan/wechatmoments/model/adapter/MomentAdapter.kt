package com.liujingyuan.wechatmoments.model.adapter

import com.chad.library.adapter.base.BaseProviderMultiAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.liujingyuan.wechatmoments.model.MomentEnty

/**
 * 多条目类型数据隔离的适配器,类似于 ConcatAdapter
 */
class MomentAdapter(data: MutableList<MomentEnty>?) : BaseProviderMultiAdapter<MomentEnty>(data) {


    override fun getItemType(data: List<MomentEnty>, position: Int): Int {
        return -2
    }

}