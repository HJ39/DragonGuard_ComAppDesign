package com.sys.test.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.sys.test.R
import com.sys.test.databinding.SecondBinding
import com.sys.test.network.Item
import com.sys.test.network.KakaoMapApi
import com.sys.test.network.Monttak
import com.sys.test.profiledata.HorizontalItemDecorator
import com.sys.test.profiledata.ProfileAdapter
import com.sys.test.profiledata.ProfileData
import com.sys.test.profiledata.VerticalItemDecorator
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

class SecondActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var resultDec = 45
    private var split = ""
    private var label = ""
    private var count = 0
    private var position = 0
    lateinit var profileAdapter: ProfileAdapter
    private var datas = ArrayList<ProfileData>()
    private lateinit var data: ArrayList<Item>
    private var resultAmount = 0
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
//        window.apply {
//            setFlags(
//                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
//            )
//        }
//        if(Build.VERSION.SDK_INT >= 30) {	// API 30 에 적용
//            WindowCompat.setDecorFitsSystemWindows(window, false)
//        }
        setToolbar()
        val intent = intent
        data = ArrayList<Item>()
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
        val retrofit =
            Retrofit.Builder().baseUrl("https://api.visitjeju.net/vsjApi/contents/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()).build()
        val api = retrofit.create(KakaoMapApi::class.java)
        Log.d("apicall:", " : ")
        apiCall(api, label, split)
    }

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
        if (resultDec == 40) {
            Log.d("초기화", "")
            profileAdapter = ProfileAdapter(datas, this)
            secondBinding.itemlist.layoutManager = LinearLayoutManager(this)
            profileAdapter.notifyDataSetChanged()
        }
        if (resultDec == 40) {
            secondBinding.itemlist.adapter = profileAdapter
        } else {
            secondBinding.itemlist.scrollToPosition(position)
        }

        chooseView()
        Log.d("size", profileAdapter.itemCount.toString())

    }

    private fun apiCall(api: KakaoMapApi, label: String, split: String) {
        var count = 0
        if (resultDec >= 0) {
            for (j in (resultDec - 4)..(resultDec)) {
                val kakaoMap = api.getDataPage(j)
                kakaoMap.enqueue(object : Callback<Monttak> {
                    override fun onResponse(call: Call<Monttak>, response: Response<Monttak>) {
                        if (response.isSuccessful && response.code() == 200) {
                            if (data.isNullOrEmpty()) {
                                data = response.body()!!.items as ArrayList<Item>
                            } else {
                                data.addAll(response.body()!!.items)
                            }
                            Log.d("결과", "성공 : ${response.raw()}")
                            resultAmount++
                            if (resultAmount == 5) {
                                secondBinding.loading.visibility = View.GONE
                                initRecycler(data, label, split)
                                initScrollListener()
                                Log.d("최종 결과", "성공")
                                resultAmount = 0
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
        Log.d("결과 resultDec", "성공 : ${resultDec}")
    }

    private fun chooseView() {
        secondBinding.itemlist.addItemDecoration(VerticalItemDecorator(20))
        secondBinding.itemlist.addItemDecoration(HorizontalItemDecorator(10))
        secondBinding.itemlist.visibility = View.VISIBLE
    }

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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {  // 네비게이션 메뉴가 클릭되면 스낵바가 나타난다.
//            R.id.account-> Snackbar.make(secondBinding.toolbar,"Navigation Account pressed", Snackbar.LENGTH_SHORT).show()
//            R.id.setting-> Snackbar.make(secondBinding.toolbar,"Navigation Setting pressed", Snackbar.LENGTH_SHORT).show()
        }
        secondBinding.drawerLayout.closeDrawers() // 기능을 수행하고 네비게이션을 닫아준다.
        return false
    }

    override fun onBackPressed() {
        if (secondBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            secondBinding.drawerLayout.closeDrawers()
        } else {
            super.onBackPressed()
        }
    }

    private fun loadMorePosts() {
        if(secondBinding.loading.visibility == View.GONE) {
            secondBinding.loading.visibility = View.VISIBLE
            CoroutineScope(Dispatchers.IO).launch {
                val retrofit = Retrofit.Builder().baseUrl("https://api.visitjeju.net/vsjApi/contents/")
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create()).build()
                val api = retrofit.create(KakaoMapApi::class.java)
                Log.d("loadMore", "dd")
                apiCall(api, label, split)
            }
        }
    }

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