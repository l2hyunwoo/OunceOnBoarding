package com.example.ounceonboarding.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AccountViewModel : ViewModel() {
    val accountName : MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val accountMail : MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val accountImage : MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
}