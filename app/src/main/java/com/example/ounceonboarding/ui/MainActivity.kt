package com.example.ounceonboarding.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.ounceonboarding.R
import com.example.ounceonboarding.background.SessionCallback
import com.kakao.auth.AuthType
import com.kakao.auth.Session
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.LogoutResponseCallback
import com.kakao.usermgmt.callback.UnLinkResponseCallback
import kotlinx.android.synthetic.main.activity_main.*

private const val NUM_PAGES = 3

class MainActivity : FragmentActivity() {

    private lateinit var vp_slider : ViewPager2
    private var sessionCallback = SessionCallback()
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
        session.addCallback(sessionCallback)

        btn_kakao_login.setOnClickListener(object : View.OnClickListener{
            override fun onClick(view: View?) {
                session.open(AuthType.KAKAO_LOGIN_ALL, this@MainActivity)
            }
        })

//        btn_kakao_logout.setOnClickListener(object : View.OnClickListener{
//            override fun onClick(p0: View?) {
//                Log.d("KAKAO_API", "Logout Click Listener")
//                UserManagement.getInstance().requestLogout(object: LogoutResponseCallback() {
//                    override fun onCompleteLogout() {
//                        Log.d("KAKAO_API", "Logout Click Listener 2")
//                        Toast.makeText(this@MainActivity, "LogOut", Toast.LENGTH_SHORT).show()
//                    }
//
//                })
//            }
//
//        })
        btn_kakao_logout.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                UserManagement.getInstance().requestUnlink(object : UnLinkResponseCallback() {
                    override fun onSuccess(result: Long?) {
                        Toast.makeText(this@MainActivity, "로그아웃 되었습니다", Toast.LENGTH_SHORT).show()
                    }

                    override fun onSessionClosed(errorResult: ErrorResult?) {
                        TODO("Not yet implemented")
                    }

                })
            }

        })


    }

    override fun onDestroy() {
        super.onDestroy()

        Session.getCurrentSession().removeCallback(sessionCallback)
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