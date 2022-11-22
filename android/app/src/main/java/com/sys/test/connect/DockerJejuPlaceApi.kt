package com.sys.test.connect

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DockerJejuPlaceApi {

    @GET("{Startpage}/{Endpage}")
    fun getDataPage(@Path("Startpage") num: Int, @Path("Endpage") num2: Int) : Call<List<DockerMonttakItem>>
}