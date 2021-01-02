package com.liujingyuan.wechatmoments.model.adapter

import com.chad.library.adapter.base.BaseProviderMultiAdapter
import com.liujingyuan.wechatmoments.model.MomentEnty

/**
 * 多条目类型数据隔离的适配器,类似于 ConcatAdapter
 *  为了方便以后列表的多类型扩展因此使用 BaseProviderMultiAdapter
 */
class MomentAdapter : BaseProviderMultiAdapter<MomentEnty>() {
    init {
        addItemProvider(MultipleTypesItemProvider())
        addItemProvider(EmptyItemProvider())
    }

    override fun getItemType(data: List<MomentEnty>, position: Int): Int {
        //防止error脏数据的混入导致条目显示错乱
        data[position].sender?.let {
            return MultipleTypesItemProvider.IMAGE_TEX_TYPE
        }?: kotlin.run {
             return EmptyItemProvider.DEFAULT_TYPE
        }
    }

}