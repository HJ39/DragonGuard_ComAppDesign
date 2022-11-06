package com.sys.test.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.GravityCompat
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.sys.test.R
import com.sys.test.databinding.ActivityThirdBinding
import com.sys.test.profiledata.ProfileData

class ThirdActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var datas : ProfileData
    private lateinit var thirdBinding: ActivityThirdBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        thirdBinding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(thirdBinding.root)
        setToolbar()
        datas = intent.getSerializableExtra("data") as ProfileData
        Glide.with(this).load(datas.thumbnailpath).into(thirdBinding.thirdimage)
    }
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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {  // 네비게이션 메뉴가 클릭되면 스낵바가 나타난다.
//            R.id.account-> Snackbar.make(secondBinding.toolbar,"Navigation Account pressed", Snackbar.LENGTH_SHORT).show()
//            R.id.setting-> Snackbar.make(secondBinding.toolbar,"Navigation Setting pressed", Snackbar.LENGTH_SHORT).show()
        }
        thirdBinding.drawerLayout.closeDrawers() // 기능을 수행하고 네비게이션을 닫아준다.
        return false
    }

    override fun onBackPressed() {
        if (thirdBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            thirdBinding.drawerLayout.closeDrawers()
        } else {
            super.onBackPressed()
        }
    }
}