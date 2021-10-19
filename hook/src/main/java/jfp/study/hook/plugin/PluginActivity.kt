package jfp.study.hook.plugin

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import dalvik.system.DexClassLoader
import jfp.study.common.pulgin.Plugin
import jfp.study.hook.R
import jfp.study.hook.databinding.ActivityPluginBinding

class PluginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityPluginBinding>(this, R.layout.activity_plugin)
        val file = getExternalFilesDir(PluginHelper.PLUGIN_NAME)
        val fileRelease = getDir("dex",Context.MODE_PRIVATE)
        val classLoader = DexClassLoader(file?.path, fileRelease.absolutePath, null, classLoader)
        val loadPlugin = classLoader.loadClass("jfp.study.plugin.PluginImpl")
        val plugin = loadPlugin.getConstructor().newInstance() as Plugin
        binding.apply {
            val context = PluginContext(this@PluginActivity)
            tvPluginName.text = plugin.getStringFromRes(context)
            ivStyle.setImageDrawable(plugin.getDrawable(context))
        }
    }
}