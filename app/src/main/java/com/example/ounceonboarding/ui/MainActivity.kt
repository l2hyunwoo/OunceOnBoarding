package com.example.ounceonboarding.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.ounceonboarding.R
import com.example.ounceonboarding.background.SessionCallback
import com.kakao.auth.AuthType
import com.kakao.auth.Session
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.util.Utility
import kotlinx.android.synthetic.main.activity_main.*
import java.security.MessageDigest
import java.util.*

private const val NUM_PAGES = 3

class MainActivity : FragmentActivity() {

    private lateinit var vp_slider : ViewPager2
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Hash Key 구하기
//        try {
//            val info = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNING_CERTIFICATES)
//            val signatures = info.signingInfo.apkContentsSigners
//            val md = MessageDigest.getInstance("SHA")
//            for (signature in signatures) {
//                val m_md : MessageDigest
//                m_md = MessageDigest.getInstance("SHA")
//                m_md.update(signature.toByteArray())
//                val key = String(Base64.encode(m_md.digest(), 0))
//                Log.d("Hash key", key)
//            }
//        } catch (e : Exception) {
//            Log.e("name not found", e.toString())
//        }

        //KakaoSDK를 활용한 Hash Key 구하기
//        val keyHash = Utility.getKeyHash(this)
//        Log.d("Hash", keyHash)
        // ViewPager2 인스턴스화
        vp_slider = findViewById(R.id.vp_scroll_tutorial)

        val pagerAdapter = ScreenSlidePagerAdapter(this)
        vp_slider.adapter = pagerAdapter
        di_tutorial.setViewPager2(vp_slider)

        val session = Session.getCurrentSession()
        val sessionCallback = SessionCallback()
        session.addCallback(sessionCallback)

        btn_kakao_login.setOnClickListener(object : View.OnClickListener{
            override fun onClick(view: View?) {
                session.open(AuthType.KAKAO_LOGIN_ALL, this@MainActivity)
            }
        })

        

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUM_PAGES

        override fun createFragment(position: Int): Fragment {
            return when(position) {
                0 -> ImageSlideFragment(R.drawable.hashtag_image)
                1 -> ImageSlideFragment(R.drawable.nomad_image)
                else -> ImageSlideFragment(R.drawable.record_image)
            }
        }
    }
}