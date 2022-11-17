package com.sys.test.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.view.GravityCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.sys.test.R
import com.sys.test.databinding.ActivityThirdBinding
import com.sys.test.profiledata.ProfileData
import com.sys.test.viewpager.ViewPagerAdapter

//third 화면 : 관광지 세부사항 표기
class ThirdActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var datas: ProfileData
    private lateinit var thirdBinding: ActivityThirdBinding
    private val MIN_SCALE = 0.85f
    private val MIN_ALPHA = 0.5f
    var currentPosition=0
    val handler= Handler(Looper.getMainLooper()){
        setPage()
        true
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        thirdBinding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(thirdBinding.root)
        setToolbar()
        thirdBinding.adviewpager3.adapter = ViewPagerAdapter(getAdList())
        thirdBinding.adviewpager3.orientation =  ViewPager2.ORIENTATION_HORIZONTAL
        thirdBinding.adviewpager3.setPageTransformer(ZoomOutPageTransformer())
        thirdBinding.adviewpager3.isUserInputEnabled = false
        val thread=Thread(PagerRunnable())
        thread.start()
        datas = intent.getSerializableExtra("data") as ProfileData
        thirdBinding.thirdimage.clipToOutline = true

        thirdBinding.thirdtitle.text = datas.item.title
        thirdBinding.thirdtitle2.append(datas.item.title)
        if(datas.item.roadaddress.isNullOrBlank()|| datas.item.roadaddress =="--"){
            thirdBinding.thirdaddr.append("정보 없음")
        }else{
            thirdBinding.thirdaddr.append(datas.item.roadaddress)
        }

        thirdBinding.thirdintro.append(datas.item.introduction ?: "정보 없음")
        Glide.with(this).load(datas.thumbnailpath).into(thirdBinding.thirdimage)
        if(datas.item.phoneno.isNullOrEmpty()|| datas.item.phoneno.isNullOrBlank() || datas.item.phoneno=="--"){
            thirdBinding.tell.visibility = View.GONE
            thirdBinding.tell.isEnabled = false
            thirdBinding.thirdphone.append("정보 없음")
        }else{
            thirdBinding.thirdphone.append(datas.item.phoneno)
        }
        if (datas.item.latitude==null || datas.item.longitude == null|| datas.item.latitude==0.0 || datas.item.longitude == 0.0) {
            thirdBinding.kakaoMap.isEnabled = false
            thirdBinding.kakaoMap.visibility = View.GONE
        }
        thirdBinding.tell.setOnClickListener {
            var intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${datas.item.phoneno}")
            if(intent.resolveActivity(packageManager) != null){
                startActivity(intent)
            }
        }
        thirdBinding.kakaoMap.setOnClickListener {
            when(datas.item.contentscd.label){
                "음식점"->{
                    val url = "geo:${datas.item.latitude},${datas.item.longitude}?q=${datas.item.title}"
                    var intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    intent.addCategory(Intent.CATEGORY_BROWSABLE)
                    startActivity(intent)
                }
                "숙박"->{
                    val url = "geo:${datas.item.latitude},${datas.item.longitude}?q=${datas.item.title}"
                    var intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    intent.addCategory(Intent.CATEGORY_BROWSABLE)
                    startActivity(intent)
                }
                else->{
                    val url = "geo:${datas.item.latitude},${datas.item.longitude}?q=${datas.item.roadaddress}"
                    var intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    intent.addCategory(Intent.CATEGORY_BROWSABLE)
                    startActivity(intent)
                }
            }
        }

    }
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
    private fun getAdList(): ArrayList<Int> {
        return arrayListOf<Int>(R.drawable.mainbol, R.drawable.mainnol, R.drawable.mainmuk,R.drawable.mainshuil)
    }
    fun setPage(){
        thirdBinding.adviewpager3.setCurrentItem(currentPosition,true)
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