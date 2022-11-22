package com.sys.test.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.navigation.NavigationView
import com.sys.test.R
import com.sys.test.connect.DockerJejuPlaceApi
import com.sys.test.connect.DockerMonttak
import com.sys.test.connect.DockerMonttakItem
import com.sys.test.databinding.SecondBinding
import com.sys.test.network.Item
import com.sys.test.network.JeJuPlaceApi
import com.sys.test.network.Monttak
import com.sys.test.profiledata.HorizontalItemDecorator
import com.sys.test.profiledata.ProfileAdapter
import com.sys.test.profiledata.ProfileData
import com.sys.test.profiledata.VerticalItemDecorator
import com.sys.test.viewpager.ViewPagerAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

//세컨 화면 : 프로그래스 바 이후 다 받으면 리사이클러뷰로 정보 제공
class SecondActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    //전역변수 선언  뷰바인딩, 뷰페이저, 핸들러등
    private var resultDec = 45
    private var resultDecD = 0
    private var split = ""
    private var label = ""
    private var count = 0
    private var position = 0
    lateinit var profileAdapter: ProfileAdapter
    private var datas = ArrayList<ProfileData>()
    private lateinit var data: ArrayList<Item>
    private lateinit var dataD : ArrayList<DockerMonttakItem>
    private var resultAmount = 0
    private var resultAmountD = 0
    private val MIN_SCALE = 0.85f
    private val MIN_ALPHA = 0.5f
    var currentPosition=0
    val handler= Handler(Looper.getMainLooper()){
        setPage()
        true
    }
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(50, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .build()
    private lateinit var secondBinding: SecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        secondBinding = SecondBinding.inflate(layoutInflater)
        setContentView(secondBinding.root)

        //툴바 설정
        setToolbar()

        //광고배너 어뎁터 및 애니메이션 설정
        secondBinding.adviewpager2.adapter = ViewPagerAdapter(getAdList())
        secondBinding.adviewpager2.orientation =  ViewPager2.ORIENTATION_HORIZONTAL
        secondBinding.adviewpager2.setPageTransformer(ZoomOutPageTransformer())
        secondBinding.adviewpager2.isUserInputEnabled = false


        //자동 스크롤 광고 시작
        CoroutineScope(Dispatchers.IO).launch{
            while(true){
                Thread.sleep(10000)
                handler.sendEmptyMessage(0)
            }
        }

        //넘어오는 값으로 주제 판별 및 제목 변경
        val intent = intent
        data = ArrayList<Item>()
        dataD = ArrayList<DockerMonttakItem>()
        split = intent.getStringExtra("split")!!
        label = intent.getStringExtra("label")!!
        Log.d("split:", " : $split")
        Log.d("label:", " : $label")
        when (split) {
            "muk" -> {
                secondBinding.mung.text = "먹거리"
            }
            "nol" -> {
                secondBinding.mung.text = "놀멍"
            }
            "bol" -> {
                secondBinding.mung.text = "볼거리"
            }
            "shil" -> {
                secondBinding.mung.text = "쉴멍"
            }
        }

        //api호출을 위한 url등 준비및 호출
        val retrofit =
            Retrofit.Builder().baseUrl("https://api.visitjeju.net/vsjApi/contents/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()).build()
        val api = retrofit.create(JeJuPlaceApi::class.java)
        Log.d("apicall:", " : ")
        apiCall(api, label, split)

        val retrofitD =
            Retrofit.Builder().baseUrl("http://172.30.1.89:5001/api/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()).build()
        val apiD = retrofitD.create(DockerJejuPlaceApi::class.java)

        apiCallD(apiD, label, split)
        //광고 클릭 리스너 구현
//        secondBinding.adviewpager2.setOnClickListener {
//            val adimg = findViewById<ImageView>(R.id.advertise_img)
//            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://ijto.or.kr/korean/"))
//            startActivity(intent)
//            when(adimg.tag){
//                "볼거리"->{
//                    var intent = Intent(Intent.ACTION_DIAL)
//                    intent.data = Uri.parse("tel:1")
//                    if(intent.resolveActivity(packageManager) != null){
//                        startActivity(intent)
//                    }
//                }
//                "놀멍"->{
//                    var intent = Intent(Intent.ACTION_DIAL)
//                    intent.data = Uri.parse("tel:2")
//                    if(intent.resolveActivity(packageManager) != null){
//                        startActivity(intent)
//                    }
//                }
//                "먹거리"->{
//                    var intent = Intent(Intent.ACTION_DIAL)
//                    intent.data = Uri.parse("tel:3")
//                    if(intent.resolveActivity(packageManager) != null){
//                        startActivity(intent)
//                    }
//                }
//                "쉴멍"->{
//                    var intent = Intent(Intent.ACTION_DIAL)
//                    intent.data = Uri.parse("tel:4")
//                    if(intent.resolveActivity(packageManager) != null){
//                        startActivity(intent)
//                    }
//                }
//            }
//
//        }
    }

    /*
    받은 관광정보를 리사이클러뷰에 추가
    비어있거나 잘못된 자료 필터링
    * */
    private fun initRecycler(items: ArrayList<Item>, label: String, split: String) {
        var itemCount = 0
        val token = label.split(",")
        for (i in 0 until items.size) {
            if (items[i].repPhoto != null && items[i].repPhoto.photoid != null) {
                if (items[i].repPhoto.photoid.thumbnailpath != null && token.contains(
                        items[i].contentscd.label
                    )
                ) {
                    if (!items[i].roadaddress.isNullOrEmpty()) {
                        if (!datas.contains(
                                ProfileData(
                                    roadaddress = "${items[i].roadaddress}",
                                    thumbnailpath = items[i].repPhoto.photoid.thumbnailpath,
                                    title = items[i].title,
                                    item = items[i]
                                )
                            )
                        ) {
                            datas.add(
                                ProfileData(
                                    roadaddress = "${items[i].roadaddress}",
                                    thumbnailpath = items[i].repPhoto.photoid.thumbnailpath,
                                    title = items[i].title,
                                    item = items[i]
                                )
                            )
                        }

                        itemCount++
                    } else {
                        if (!datas.contains(
                                ProfileData(
                                    roadaddress = "",
                                    thumbnailpath = items[i].repPhoto.photoid.thumbnailpath,
                                    title = items[i].title,
                                    item = items[i]
                                )
                            )
                        ) {
                            datas.add(
                                ProfileData(
                                    roadaddress = "",
                                    thumbnailpath = items[i].repPhoto.photoid.thumbnailpath,
                                    title = items[i].title,
                                    item = items[i]
                                )
                            )
                        }

                        itemCount++
                    }
                }
            }
        }
        count += itemCount
        Log.d("실제1 : ", count.toString())
        if (resultDec == 42) {
            Log.d("초기화", "")
            profileAdapter = ProfileAdapter(datas, this)
            secondBinding.itemlist.layoutManager = LinearLayoutManager(this)
            profileAdapter.notifyDataSetChanged()
        }
        if (resultDec == 42) {
            secondBinding.itemlist.adapter = profileAdapter
            chooseView()
        } else {
            secondBinding.itemlist.scrollToPosition(position)
        }
        Log.d("size", profileAdapter.itemCount.toString())
    }

    /*
    api 호출 및 initRecyclerview()호출
    api 3페이지씩 호출하여 정보를 보여줌
    3개의 호출이 도착하면 로딩바 제거하고 화면을 띄워줌
    */
    private fun apiCall(api: JeJuPlaceApi, label: String, split: String) {
        var count = 0
        if (resultDec >= 0) {
            for (j in (resultDec - 2)..(resultDec)) {
                val jejuPlace = api.getDataPage(j)
                jejuPlace.enqueue(object : Callback<Monttak> {
                    override fun onResponse(call: Call<Monttak>, response: Response<Monttak>) {
                        if (response.isSuccessful && response.code() == 200) {
                            if (data.isNullOrEmpty()) {
                                data = response.body()!!.items as ArrayList<Item>
                            } else {
                                data.addAll(response.body()!!.items)
                            }
                            Log.d("결과", "성공 : ${response.raw()}")
                            resultAmount++
                            if (resultAmount == 3) {
                                initRecycler(data, label, split)
                                initScrollListener()
                                Log.d("최종 결과", "성공")
                                resultAmount = 0
                                secondBinding.loading.visibility = View.GONE
                            }
                        }
                    }
                    override fun onFailure(call: Call<Monttak>, t: Throwable) {
                        Log.d("결과:", "실패 : $t")
                    }
                })
                count++
            }
        }
        resultDec -= count
    }

    private fun apiCallD(api: DockerJejuPlaceApi, label: String, split: String) {
        var result = 0
        if (resultDecD >= 0) {
            val jejuPlace = api.getDataPage(resultDecD,resultDecD+100)
            jejuPlace.enqueue(object : Callback<List<DockerMonttakItem>> {
                override fun onResponse(call: Call<List<DockerMonttakItem>>, response: Response<List<DockerMonttakItem>>) {
                    if (response.isSuccessful && response.code() == 200) {
                        if (dataD.isNullOrEmpty()) {
                            dataD = response.body()!! as ArrayList<DockerMonttakItem>
                        } else {
                            dataD.addAll(response.body()!!)
                        }
                        Log.d("결과", "성공 : ${response.raw()}")
                        result++
                        if (result == 1) {
                            Log.d("도커 결과", "성공${dataD[0].address}")
//                            initRecycler(data, label, split)
//                            initScrollListener()
                            Log.d("도커 결과", "성공")

                        }
                    }
                }
                override fun onFailure(call: Call<List<DockerMonttakItem>>, t: Throwable) {
                    Log.d("도커 결과:", "실패 : $t")
                }
            })
        }
        resultDecD += 100
        Log.d("결과 resultDecD", "성공 : ${4400-resultDecD}")
    }
    //줄 간격 설정 및 visible
    private fun chooseView() {
        secondBinding.itemlist.addItemDecoration(VerticalItemDecorator(20))
        secondBinding.itemlist.addItemDecoration(HorizontalItemDecorator(10))
        secondBinding.itemlist.visibility = View.VISIBLE
    }

    //애니매이션 설정
    inner class ZoomOutPageTransformer : ViewPager2.PageTransformer {
        override fun transformPage(view: View, position: Float) {
            view.apply {
                val pageWidth = width
                val pageHeight = height
                when {
                    position < -1 -> { // [-Infinity,-1)
                        // This page is way off-screen to the left.
                        alpha = 0f
                    }
                    position <= 1 -> { // [-1,1]
                        // Modify the default slide transition to shrink the page as well
                        val scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position))
                        val vertMargin = pageHeight * (1 - scaleFactor) / 2
                        val horzMargin = pageWidth * (1 - scaleFactor) / 2
                        translationX = if (position < 0) {
                            horzMargin - vertMargin / 2
                        } else {
                            horzMargin + vertMargin / 2
                        }

                        // Scale the page down (between MIN_SCALE and 1)
                        scaleX = scaleFactor
                        scaleY = scaleFactor

                        // Fade the page relative to its size.
                        alpha = (MIN_ALPHA +
                                (((scaleFactor - MIN_SCALE) / (1 - MIN_SCALE)) * (1 - MIN_ALPHA)))
                    }
                    else -> { // (1,+Infinity]
                        // This page is way off-screen to the right.
                        alpha = 0f
                    }
                }
            }
        }
    }

    //광고 리스트
    private fun getAdList(): ArrayList<Int> {
        return arrayListOf<Int>(R.drawable.ad1, R.drawable.ad2, R.drawable.ad3,R.drawable.ad4)
    }
    //광고 페이지 넘기기
    fun setPage(){
        secondBinding.adviewpager2.setCurrentItem(currentPosition,true)
        currentPosition+=1
    }

    //2초 마다 페이지 넘기기
    inner class PagerRunnable:Runnable{
        override fun run() {
            while(true){
                Thread.sleep(2000)
                handler.sendEmptyMessage(0)
            }
        }
    }

    //툴바 생성
    private fun setToolbar() {
        setSupportActionBar(secondBinding.toolbar)
        // 툴바 왼쪽 버튼 설정
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)  // 왼쪽 버튼 사용 여부 true
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_orange_menu_24)  // 왼쪽 버튼 이미지 설정
        supportActionBar!!.setDisplayShowTitleEnabled(false)    // 타이틀 안보이게 하기
    }

    // 툴바 메뉴 버튼을 설정
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        return true
    }

    // 툴바 메뉴 버튼이 클릭 됐을 때 콜백
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> { // 메뉴 버튼
                secondBinding.drawerLayout.openDrawer(GravityCompat.START)    // 네비게이션 드로어 열기
            }
        }
        return super.onOptionsItemSelected(item)
    }

    //아이템이 눌려도 닫음
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {  // 네비게이션 메뉴가 클릭되면 스낵바가 나타난다.
//            R.id.account-> Snackbar.make(secondBinding.toolbar,"Navigation Account pressed", Snackbar.LENGTH_SHORT).show()
//            R.id.setting-> Snackbar.make(secondBinding.toolbar,"Navigation Setting pressed", Snackbar.LENGTH_SHORT).show()
        }
        secondBinding.drawerLayout.closeDrawers() // 기능을 수행하고 네비게이션을 닫아준다.
        return false
    }

    //뒤로가기 버튼눌렸을때 닫음
    override fun onBackPressed() {
        if (secondBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            secondBinding.drawerLayout.closeDrawers()
        } else {
            super.onBackPressed()
        }
    }

    //데이터를 더 받고 recyclerview에 추가함
    private fun loadMorePosts() {
        if(secondBinding.loading.visibility == View.GONE) {
            secondBinding.loading.visibility = View.VISIBLE
            CoroutineScope(Dispatchers.IO).launch {
                val retrofit = Retrofit.Builder().baseUrl("https://api.visitjeju.net/vsjApi/contents/")
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create()).build()
                val api = retrofit.create(JeJuPlaceApi::class.java)
                Log.d("loadMore", "dd")
                apiCall(api, label, split)
            }
        }
    }

    //마지막 item에서 스크롤 하면 로딩과 함께 다시 받아서 추가하기
    private fun initScrollListener() {
        secondBinding.itemlist.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = secondBinding.itemlist.layoutManager
                // hasNextPage() -> 다음 페이지가 있는 경우
                if (resultDec > 1) {
                    val lastVisibleItem = (layoutManager as LinearLayoutManager)
                        .findLastCompletelyVisibleItemPosition()
                    val itemTotalCount = recyclerView.adapter!!.itemCount-1
                    position = recyclerView.adapter!!.itemCount-1
                    // 마지막으로 보여진 아이템 position 이
                    // 전체 아이템 개수보다 5개 모자란 경우, 데이터를 loadMore 한다
                    if (!secondBinding.itemlist.canScrollVertically(1) && lastVisibleItem == itemTotalCount) {
                        loadMorePosts()
                    }
                }
            }
        })
    }
}