package com.sys.test.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.core.view.GravityCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.sys.test.R
import com.sys.test.databinding.ActivityThirdBinding
import com.sys.test.profiledata.DockerProfileData
import com.sys.test.profiledata.ProfileData
import com.sys.test.viewpager.ViewPagerAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//third 화면 : 관광지 세부사항 표기
class ThirdActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    //전역변수 선언  뷰바인딩, 뷰페이저, 핸들러
    lateinit var datas: DockerProfileData
    private lateinit var thirdBinding: ActivityThirdBinding
    private val MIN_SCALE = 0.85f
    private val MIN_ALPHA = 0.5f
    var currentPosition = 0
    val handler = Handler(Looper.getMainLooper()) {
        setPage()
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        thirdBinding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(thirdBinding.root)

        //툴바 설정
        setToolbar()

        //광고배너 어뎁터 및 애니메이션 설정
        thirdBinding.adviewpager3.adapter = ViewPagerAdapter(getAdList())
        thirdBinding.adviewpager3.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        thirdBinding.adviewpager3.setPageTransformer(ZoomOutPageTransformer())
        thirdBinding.adviewpager3.isUserInputEnabled = false

        //자동 스크롤 광고 시작
        CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                Thread.sleep(10000)
                handler.sendEmptyMessage(0)
            }
        }

        //두번째 화면의 리사이클러뷰로부터 정보 받기
        datas = intent.getSerializableExtra("data") as DockerProfileData

        //사진 모서리 둥글게 하기
        thirdBinding.thirdimage.clipToOutline = true

        //받은 정보 화면에 정리해서 사진, 텍스트등을 보여주기
        thirdBinding.thirdtitle.text = datas.title
        thirdBinding.thirdtitle2.append(datas.title)
        if (datas.monttakItem.road_address.isNullOrBlank() || datas.monttakItem.road_address == "--") {
            thirdBinding.thirdaddr.append("정보 없음")
        } else {
            thirdBinding.thirdaddr.append(datas.monttakItem.road_address)
        }
        thirdBinding.thirdintro.append(datas.monttakItem.introduction ?: "정보 없음")
        Glide.with(this).load(datas.monttakItem.thumbnailpath).into(thirdBinding.thirdimage)
        if (datas.monttakItem.phoneno.isNullOrEmpty() || datas.monttakItem.phoneno.isNullOrBlank() || datas.monttakItem.phoneno == "--") {
            thirdBinding.tell.visibility = View.GONE
            thirdBinding.tell.isEnabled = false
            thirdBinding.thirdphone.append("정보 없음")
        } else {
            thirdBinding.thirdphone.append(datas.monttakItem.phoneno)
        }
//        if (datas.monttakItem.latitude == null || datas.monttakItem.longitude == null || datas.monttakItem.latitude == 0.0 || datas.monttakItem.longitude == 0.0) {
//            thirdBinding.map.isEnabled = false
//            thirdBinding.map.visibility = View.GONE
//        }

        //전화걸기및 지도 클릭시 동작
        thirdBinding.tell.setOnClickListener {
            var intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${datas.monttakItem.phoneno}")
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }
        }
//        thirdBinding.map.setOnClickListener {
//            when (datas.monttakItem.contentscdlabel) {
//                "음식점" -> {
//                    val url =
//                        "geo:${datas.monttakItem.latitude},${datas.monttakItem.longitude}?q=${datas.monttakItem.title}"
//                    var intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//                    intent.addCategory(Intent.CATEGORY_BROWSABLE)
//                    startActivity(intent)
//                }
//                "숙박" -> {
//                    val url =
//                        "geo:${datas.monttakItem.latitude},${datas.monttakItem.longitude}?q=${datas.monttakItem.title}"
//                    var intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//                    intent.addCategory(Intent.CATEGORY_BROWSABLE)
//                    startActivity(intent)
//                }
//                else -> {
//                    val url =
//                        "geo:${datas.monttakItem.latitude},${datas.monttakItem.longitude}?q=${datas.monttakItem.road_address}"
//                    var intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//                    intent.addCategory(Intent.CATEGORY_BROWSABLE)
//                    startActivity(intent)
//                }
//            }
//        }

        //광고 클릭 리스너 구현
//        thirdBinding.adviewpager3.setOnClickListener {
//            val adimg = findViewById<ImageView>(R.id.advertise_img)
//            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://ijto.or.kr/korean/"))
//            startActivity(intent)
//            when (adimg.tag) {
//                "볼거리" -> {
//                    var intent = Intent(Intent.ACTION_DIAL)
//                    intent.data = Uri.parse("tel:1")
//                    if (intent.resolveActivity(packageManager) != null) {
//                        startActivity(intent)
//                    }
//                }
//                "놀멍" -> {
//                    var intent = Intent(Intent.ACTION_DIAL)
//                    intent.data = Uri.parse("tel:2")
//                    if (intent.resolveActivity(packageManager) != null) {
//                        startActivity(intent)
//                    }
//                }
//                "먹거리" -> {
//                    var intent = Intent(Intent.ACTION_DIAL)
//                    intent.data = Uri.parse("tel:3")
//                    if (intent.resolveActivity(packageManager) != null) {
//                        startActivity(intent)
//                    }
//                }
//                "쉴멍" -> {
//                    var intent = Intent(Intent.ACTION_DIAL)
//                    intent.data = Uri.parse("tel:4")
//                    if (intent.resolveActivity(packageManager) != null) {
//                        startActivity(intent)
//                    }
//                }
//            }
//
//        }
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
        thirdBinding.adviewpager3.setCurrentItem(currentPosition, true)
        currentPosition += 1
    }

    //툴바 생성
    private fun setToolbar() {
        setSupportActionBar(thirdBinding.toolbar)
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
                thirdBinding.drawerLayout.openDrawer(GravityCompat.START)    // 네비게이션 드로어 열기
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
        thirdBinding.drawerLayout.closeDrawers() // 기능을 수행하고 네비게이션을 닫아준다.
        return false
    }

    //뒤로가기 버튼눌렸을때 닫음
    override fun onBackPressed() {
        if (thirdBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            thirdBinding.drawerLayout.closeDrawers()
        } else {
            super.onBackPressed()
        }
    }
}