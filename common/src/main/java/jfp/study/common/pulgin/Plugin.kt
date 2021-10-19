package jfp.study.common.pulgin

import android.content.Context
import android.graphics.drawable.Drawable

interface Plugin {

    val name: String

    fun encrypt(str: String): String

    fun getStringFromRes(context: Context): String

    fun getDrawable(context: Context) : Drawable?
}