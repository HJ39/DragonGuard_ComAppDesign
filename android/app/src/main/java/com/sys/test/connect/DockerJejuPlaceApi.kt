package com.sys.test.connect

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/*db 서버에서 호출
 http:ip주소:5001/api/시작페이지/끝페이지   형태
*/
interface DockerJejuPlaceApi {

    @GET("{Startpage}/{Endpage}")
    fun getDataPage(@Path("Startpage") num: Int, @Path("Endpage") num2: Int) : Call<List<DockerMonttakItem>>
}