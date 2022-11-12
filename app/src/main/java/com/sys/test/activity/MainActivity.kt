package com.sys.test.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.WindowCompat
import com.google.android.material.navigation.NavigationView
import com.sys.test.R
import com.sys.test.databinding.ActivityMainBinding

import com.sys.test.network.Item


//메인화면 : 로딩화면 이후 메인 화면에 툴바 달기
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = Intent(this@MainActivity, LoadingActivity::class.java)
        startActivity(intent)
        setToolbar()
        binding.navigationView.setNavigationItemSelectedListener(this)
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
    //툴바 생성
    private fun setToolbar(){
        setSupportActionBar(binding.toolbar)
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
        when(item!!.itemId){
            android.R.id.home->{ // 메뉴 버튼
                binding.drawerLayout.openDrawer(GravityCompat.START)    // 네비게이션 드로어 열기
            }
        }
        return super.onOptionsItemSelected(item)
    }
    //아이템이 눌려도 닫음
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        binding.drawerLayout.closeDrawers() // 기능을 수행하고 네비게이션을 닫아준다.
        return false
    }
    //뒤로가기 버튼눌렸을때 닫음
    override fun onBackPressed() {
        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.closeDrawers()
        }else{
            super.onBackPressed()
        }
    }
}