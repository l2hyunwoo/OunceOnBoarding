package com.example.ounceonboarding.data

import java.io.Serializable

data class ProfileInformation (
    val name : String,
    val mail : String,
    val profileUrl : String
) : Serializable