package jfp.study.plugin

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import jfp.study.common.pulgin.Plugin

class PluginImpl : Plugin {

    override val name = "style - normal"

    override fun encrypt(str: String) = str + name

    override fun getStringFromRes(context: Context): String {
        return context.getString(R.string.plugin_str)
    }

    override fun getDrawable(context: Context): Drawable? {
        return ContextCompat.getDrawable(context, R.drawable.iv_style)
    }

}