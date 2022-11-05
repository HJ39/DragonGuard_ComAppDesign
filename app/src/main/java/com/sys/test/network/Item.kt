package com.sys.test.network

import java.io.Serializable

data class Item(
    val address: String?,
    val alltag: String?,
    val contentscd: Contentscd,
    val contentsid: String,
    val introduction: String,
    val latitude: Double?,
    val longitude: Double?,
    val phoneno: String?,
    val postcode: String?,
    val region1cd: Region1cd,
    val region2cd: Region1cd,
    val repPhoto: RepPhoto,
    val roadaddress: String?,
    val tag: String?,
    val title: String
):Serializable