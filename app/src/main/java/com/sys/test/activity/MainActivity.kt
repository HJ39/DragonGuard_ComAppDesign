package com.sys.test.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.sys.test.R
import com.sys.test.databinding.ActivityMainBinding

import com.sys.test.network.Item



class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var isNavigationOpen = false
    private lateinit var data :ArrayList<Item>

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar()

        binding.navigationView.setNavigationItemSelectedListener(this)

//        var longitude : Double = 127.005515
//        var latitude : Double  = 37.537229
//
//        binding.button.setOnClickListener {
//            if(latitude != 0.0 && longitude != 0.0){
//                val url ="kakaomap://look?p=${latitude},${longitude}"
//                var intent =  Intent(Intent.ACTION_VIEW, Uri.parse(url))
//                intent.addCategory(Intent.CATEGORY_BROWSABLE)
//                var list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
//
//                //카카오맵 어플리케이션이 사용자 핸드폰에 깔려있으면 바로 앱으로 연동
//                //그렇지 않다면 다운로드 페이지로 연결
//
//                if (list== null){
//                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=net.daum.android.map")))
//                }else{
//                    startActivity(intent)
//                }
//            }
//        }
        binding.bol.setOnClickListener {
            var intent = Intent(applicationContext, SecondActivity::class.java)
            intent.putExtra("label","정보,축제/행사")
            intent.putExtra("split","bol")
            startActivity(intent)
        }
        binding.nol.setOnClickListener {
            var intent = Intent(applicationContext, SecondActivity::class.java)
            intent.putExtra("label","테마여행,쇼핑")
            intent.putExtra("split","bol")
            startActivity(intent)
        }
        binding.shil.setOnClickListener {
            var intent = Intent(applicationContext, SecondActivity::class.java)
            intent.putExtra("label","숙박")
            intent.putExtra("split","shil")
            startActivity(intent)
        }
        binding.muk.setOnClickListener {
            var intent = Intent(applicationContext, SecondActivity::class.java)
            intent.putExtra("label","음식점")
            intent.putExtra("split","muk")
            startActivity(intent)
        }
    }
    private fun setToolbar(){
        setSupportActionBar(binding.toolbar)

        // 툴바 왼쪽 버튼 설정
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)  // 왼쪽 버튼 사용 여부 true
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp)  // 왼쪽 버튼 이미지 설정
        supportActionBar!!.setDisplayShowTitleEnabled(false)    // 타이틀 안보이게 하기
    }
    // 툴바 메뉴 버튼을 설정
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        return true
    }

    // 툴바 메뉴 버튼이 클릭 됐을 때 콜백
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item!!.itemId){
            android.R.id.home->{ // 메뉴 버튼
                binding.drawerLayout.openDrawer(GravityCompat.START)    // 네비게이션 드로어 열기
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        binding.drawerLayout.closeDrawers() // 기능을 수행하고 네비게이션을 닫아준다.
        return false
    }
    override fun onBackPressed() {
        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.closeDrawers()
        }else{
            super.onBackPressed()
        }
    }
}