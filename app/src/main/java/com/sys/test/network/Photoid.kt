package com.sys.test.network

import java.io.Serializable

data class Photoid(
    val imgpath: String,
    val photoid: Long,
    val thumbnailpath: String
):Serializable