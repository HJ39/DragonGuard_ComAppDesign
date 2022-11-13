package com.sys.test.profiledata

import com.sys.test.network.Item
import java.io.Serializable

data class ProfileData(
    val thumbnailpath: String,
    val roadaddress: String,
    val title: String,
    val item : Item
):Serializable
