package com.example.ounceonboarding.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.ounceonboarding.R
import com.example.ounceonboarding.data.ProfileInformation
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val intent = intent
        val profileInfo = intent.getSerializableExtra("profile") as ProfileInformation
        Glide.with(this).load(profileInfo.profileUrl).into(img_profile)
        tv_name.text = profileInfo.name
        tv_mail.text = profileInfo.mail
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.finish()
    }
}