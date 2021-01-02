package com.liujingyuan.wechatmoments.model.adapter


import android.widget.TextView
import com.chad.library.adapter.base.BaseProviderMultiAdapter

import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.liujingyuan.wechatmoments.R
import com.liujingyuan.wechatmoments.model.MomentEnty


class EmptyItemProvider : BaseItemProvider<MomentEnty>() {

    companion object {
        const val DEFAULT_TYPE = 0
    }

    override val itemViewType: Int
        get() = DEFAULT_TYPE
    override val layoutId: Int
        get() = R.layout.empty_list_item

    override fun convert(helper: BaseViewHolder, item: MomentEnty) {

    }

}