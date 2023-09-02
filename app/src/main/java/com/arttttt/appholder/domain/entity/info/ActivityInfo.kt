package com.arttttt.appholder.domain.entity.info

import kotlinx.serialization.Serializable

@Serializable
data class ActivityInfo(
    val title: String,
    val name: String,
    val pkg: String,
)