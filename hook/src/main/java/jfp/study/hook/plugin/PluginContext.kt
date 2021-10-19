package jfp.study.hook.plugin

import android.content.Context
import android.content.ContextWrapper
import android.content.res.AssetManager
import android.content.res.Resources

class PluginContext(
    context: Context
) : ContextWrapper(context) {

    private val mAssetManager: AssetManager = AssetManager::class.java.newInstance()

    private val mResources: Resources

    init {
        val addAssetPath =
            mAssetManager.javaClass.getDeclaredMethod("addAssetPath", String::class.java)
        addAssetPath.invoke(
            mAssetManager,
            context.getExternalFilesDir(PluginHelper.PLUGIN_NAME)?.path
        )
        val res = context.resources
        mResources = Resources(mAssetManager, res.displayMetrics, res.configuration)
    }

    override fun getAssets(): AssetManager = mAssetManager

    override fun getResources(): Resources = mResources
}