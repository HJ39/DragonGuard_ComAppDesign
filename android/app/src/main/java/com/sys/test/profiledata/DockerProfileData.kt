package com.sys.test.profiledata

import com.sys.test.connect.DockerMonttakItem
import java.io.Serializable

data class DockerProfileData(
    val monttakItem: DockerMonttakItem,
    val title : String
): Serializable
