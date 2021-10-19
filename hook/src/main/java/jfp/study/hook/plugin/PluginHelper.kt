package jfp.study.hook.plugin

import android.content.Context
import java.io.FileOutputStream

object PluginHelper {

    const val PLUGIN_NAME = "plugin.apk"

    fun init(context: Context) {
        moveAssetsToFile(context)
    }

    private fun moveAssetsToFile(context: Context) {
        val file = context.assets.open(PLUGIN_NAME)
        val bytes = file.readBytes()
        val filePath = context.getExternalFilesDir(PLUGIN_NAME)
        if (filePath?.isDirectory == true) {
            filePath.delete()
            filePath.createNewFile()
        }
        FileOutputStream(filePath).use { out ->
            out.write(bytes)
        }
    }

}