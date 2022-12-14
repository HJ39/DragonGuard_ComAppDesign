package com.sys.test.connect

import java.io.Serializable

/*관광지 데이터 클래스
  서버에서 null을 default값으로 했으므로 nullable하지 않음
 */
data class DockerMonttakItem(
    val address: String,
    val alltag: String,
    val contentscdlabel: String,
    val contentscdrefid: String,
    val contentscdvalue: String,
    val introduction: String,
    val phoneno: String,
    val road_address: String,
    val tag: String,
    val thumbnailpath: String,
    val title: String,
    val latitude : Double,
    val longitude : Double
):Serializable