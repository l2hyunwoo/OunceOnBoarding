package com.example.ounceonboarding.background

import android.util.Log
import com.example.ounceonboarding.data.AccountViewModel
import com.example.ounceonboarding.data.ProfileInformation
import com.kakao.auth.ISessionCallback
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import com.kakao.usermgmt.response.model.Profile
import com.kakao.util.OptionalBoolean
import com.kakao.util.exception.KakaoException


class SessionCallback : ISessionCallback {
    // 로그인에 성공한 상태
    private var mName : String = ""
    private var mMail : String = ""
    private var mProfileImage : String = ""

    override fun onSessionOpened() {
        requestMe()
    }

    fun getInfo() : ProfileInformation {
        val infoList = ProfileInformation(mName, mMail, mProfileImage)
        return infoList
    }

    // 로그인에 실패한 상태
    override fun onSessionOpenFailed(exception: KakaoException) {
        Log.e("SessionCallback :: ", "onSessionOpenFailed : " + exception.message)
    }
    // 사용자 정보 요청
    fun requestMe() {
        UserManagement.getInstance()
            .me(object : MeV2ResponseCallback() {
                override fun onSessionClosed(errorResult: ErrorResult) {
                    Log.e("KAKAO_API", "세션이 닫혀 있음: $errorResult")
                }

                override fun onFailure(errorResult: ErrorResult) {
                    Log.e("KAKAO_API", "사용자 정보 요청 실패: $errorResult")
                }

                override fun onSuccess(result: MeV2Response) {
                    Log.i("KAKAO_API", "사용자 아이디: " + result.id)
                    val kakaoAccount = result.kakaoAccount
                    if (kakaoAccount != null) {
                        // 이메일
                        val email = kakaoAccount.email
                        if (email != null) {
                            mMail = email
                            Log.i("KAKAO_API", "email: $email")
                        } else if (kakaoAccount.emailNeedsAgreement() == OptionalBoolean.TRUE) {
                            // 동의 요청 후 이메일 획득 가능
                            // 단, 선택 동의로 설정되어 있다면 서비스 이용 시나리오 상에서 반드시 필요한 경우에만 요청해야 합니다.
                        } else {
                            // 이메일 획득 불가
                        }

                        // 프로필
                        val profile: Profile? = kakaoAccount.profile
                        if (profile != null) {
                            mName = profile.nickname
                            mProfileImage = profile.profileImageUrl
                            Log.d("KAKAO_API", "nickname: " + profile.getNickname())
                            Log.d("KAKAO_API", "profile image: " + profile.getProfileImageUrl())
                            Log.d("KAKAO_API", "thumbnail image: " + profile.getThumbnailImageUrl())
                        } else if (kakaoAccount.profileNeedsAgreement() == OptionalBoolean.TRUE) {
                            // 동의 요청 후 프로필 정보 획득 가능
                        } else {
                            // 프로필 획득 불가
                        }
                    }
                }
            })
    }
}