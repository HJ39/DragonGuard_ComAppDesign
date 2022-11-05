package com.sys.test.activity



import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.sys.test.R

class LoadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        startLoading()
    }
    fun startLoading(){
        val handler = Handler()
        handler.postDelayed({ finish() }, 500)
    }
}