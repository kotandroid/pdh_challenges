package com.example.pdh_challenge

import androidx.annotation.StringRes

data class Question(@StringRes val textResId:Int, val answer:Boolean, var passed:Boolean, var solved:Boolean) {
}