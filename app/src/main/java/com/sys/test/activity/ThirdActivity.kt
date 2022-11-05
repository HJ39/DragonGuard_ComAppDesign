package com.sys.test.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.sys.test.databinding.ActivityThirdBinding
import com.sys.test.profiledata.ProfileData

class ThirdActivity : AppCompatActivity() {
    lateinit var datas : ProfileData
    private lateinit var thirdBinding: ActivityThirdBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        thirdBinding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(thirdBinding.root)

        datas = intent.getSerializableExtra("data") as ProfileData
        Glide.with(this).load(datas.thumbnailpath).into(thirdBinding.thirdimage)
    }
}