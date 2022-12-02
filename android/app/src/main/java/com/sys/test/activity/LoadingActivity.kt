package com.sys.test.activity



import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.sys.test.R

//앱 실행시 보여주는 로딩화면
class LoadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        startLoading()
    }

    //0.5초 로딩
    fun startLoading(){
        val handler = Handler()
        handler.postDelayed({ finish() }, 500)
    }
}