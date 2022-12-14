package com.sys.test.activity

import android.content.ClipData
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.navigation.NavigationView
import com.sys.test.R
import com.sys.test.connect.DockerJejuPlaceApi
import com.sys.test.connect.DockerMonttakItem
import com.sys.test.databinding.SecondBinding
import com.sys.test.profiledata.*
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

/*세컨 화면 : 프로그래스 바 이후 다 받으면 리사이클러뷰로 정보 제공
  도커 서버 적용이후 이전 apicall, initrecycler는 주석처리함
  광고 또한 보여주기만 하므로 리스너도 주석처리함
* */
class SecondActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    //전역변수 선언  뷰바인딩, 뷰페이저, 핸들러등
    private var resultDecD = 1
    private var split = ""
    private var label = ""
    private var count = 0
    private var position = 0
    lateinit var dockerprofileAdapter : DockerProfileAdapter
    private var datasD = ArrayList<DockerProfileData>()
    private lateinit var data: ArrayList<ClipData.Item>
    private lateinit var dataD: ArrayList<DockerMonttakItem>
    private val MIN_SCALE = 0.85f
    private val dockerIp = "http://172.30.1.85:5001/api/"
    private val MIN_ALPHA = 0.5f
    var currentPosition = 0
    val handler = Handler(Looper.getMainLooper()) {
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
        secondBinding.adviewpager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        secondBinding.adviewpager2.setPageTransformer(ZoomOutPageTransformer())
        secondBinding.adviewpager2.isUserInputEnabled = false


        //자동 스크롤 광고 시작
        CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                Thread.sleep(3000)
                handler.sendEmptyMessage(0)
            }
        }

        //넘어오는 값으로 주제 판별 및 제목 변경
        val intent = intent
        data = ArrayList<ClipData.Item>()
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


//      api호출을 위한 url등 준비및 호출(도커 서버에서 호출)
        val retrofitD =
            Retrofit.Builder().baseUrl(dockerIp)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()).build()
        val apiD = retrofitD.create(DockerJejuPlaceApi::class.java)

        apiCallD(apiD, label, split)
    }

    /*
    db서버에서 받은 관광정보를 리사이클러뷰에 추가
    비어있거나 잘못된 자료 필터링
    * */
    private fun initRecyclerD(items: ArrayList<DockerMonttakItem>, label: String, split: String) {
        var itemCount = 0
        val token = label.split(",")
        for (i in 0 until items.size) {
            if (items[i].thumbnailpath != null && token.contains(
                    items[i].contentscdlabel
                )
            ) {
                if (!datasD.contains(
                        DockerProfileData(
                            monttakItem = items[i],
                            title = items[i].title
                        )
                    )
                ) {
                    datasD.add(
                        DockerProfileData(
                            monttakItem = items[i],
                            title = items[i].title
                        )
                    )
                }
                itemCount++
            }

        }
        count += itemCount
        Log.d("실제1 : ", count.toString())
        if (resultDecD == 101) {
            Log.d("초기화", "")
            dockerprofileAdapter = DockerProfileAdapter(datasD, this)
            secondBinding.itemlist.layoutManager = LinearLayoutManager(this)
            dockerprofileAdapter.notifyDataSetChanged()
        }
        if (resultDecD == 101) {
            secondBinding.itemlist.adapter = dockerprofileAdapter
            chooseView()
        } else {
            secondBinding.itemlist.scrollToPosition(position)
        }
        Log.d("size", dockerprofileAdapter.itemCount.toString())
    }


    /*
    db서버의 api 호출 및 initRecyclerview()호출
    api 3페이지씩 호출하여 정보를 보여줌
    3개의 호출이 도착하면 로딩바 제거하고 화면을 띄워줌
    */
    private fun apiCallD(api: DockerJejuPlaceApi, label: String, split: String) {
        var result = 0
        Log.d("api 전","before")
        if (resultDecD <=4301) {
            Log.d("api 중","in")
            val jejuPlace = api.getDataPage(resultDecD, resultDecD + 99)
            jejuPlace.enqueue(object : Callback<List<DockerMonttakItem>> {
                override fun onResponse(
                    call: Call<List<DockerMonttakItem>>,
                    response: Response<List<DockerMonttakItem>>
                ) {
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
                            initRecyclerD(dataD, label, split)
                            initScrollListener()
                            secondBinding.loading.visibility = View.GONE
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
        Log.d("결과 resultDecD", "성공 : ${4400 - resultDecD}")
    }

    //recycler뷰 줄 간격 설정 및 visible
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
        return arrayListOf<Int>(R.drawable.ad1, R.drawable.ad2, R.drawable.ad3, R.drawable.ad4)
    }

    //광고 페이지 넘기기
    fun setPage() {
        secondBinding.adviewpager2.setCurrentItem(currentPosition, true)
        currentPosition += 1
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
        if (secondBinding.loading.visibility == View.GONE) {
            secondBinding.loading.visibility = View.VISIBLE
            CoroutineScope(Dispatchers.Main).launch {
                val retrofitD =
                    Retrofit.Builder().baseUrl(dockerIp)
                        .client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create()).build()
                val apiD = retrofitD.create(DockerJejuPlaceApi::class.java)

                apiCallD(apiD, label, split)
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
                if (resultDecD <4401 ) {
                    val lastVisibleItem = (layoutManager as LinearLayoutManager)
                        .findLastCompletelyVisibleItemPosition()
                    val itemTotalCount = recyclerView.adapter!!.itemCount - 1
                    position = recyclerView.adapter!!.itemCount - 1
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