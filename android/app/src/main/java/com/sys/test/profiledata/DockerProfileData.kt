package com.sys.test.profiledata

import com.sys.test.connect.DockerMonttakItem
import java.io.Serializable

//db서버에서 받은 데이터를 recycler뷰에 넣기 위한 데이터 클래스
data class DockerProfileData(
    val monttakItem: DockerMonttakItem,
    val title : String
): Serializable
