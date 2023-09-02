package com.arttttt.appholder.ui.permissions.models

import com.arttttt.appholder.domain.entity.permission.Permission2
import com.arttttt.appholder.ui.base.ListItem
import kotlin.reflect.KClass

sealed class PermissionLazyListItem : ListItem {

    abstract val title: String

    data class Granted(
        override val key: Any,
        override val title: String
    ) : PermissionLazyListItem()

    data class Denied(
        override val title: String,
        val permission: KClass<out Permission2>,
    ) : PermissionLazyListItem() {

        override val key: Any = permission
    }
}