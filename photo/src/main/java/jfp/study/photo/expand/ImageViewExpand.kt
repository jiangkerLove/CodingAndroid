package jfp.study.photo.expand

import android.util.LruCache
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.disklrucache.DiskLruCache
import jfp.study.photo.GlideApp
import jfp.study.photo.R


fun ImageView.displayByUrl(url: String) {
    Glide.with(context)
        .load(url)
//          占位符，同步加载
        .placeholder(R.drawable.iv_default)
        .fitCenter()
//          错误时显示
        .error(R.drawable.iv_error)
//          当url为空时
        .fallback(R.drawable.iv_other)
        .into(this)
}

fun ImageView.displayByUrlApp(url: String) {
    GlideApp.with(context)
        .load(url)
        .placeholder(R.drawable.iv_default)
        .fitCenter()
        .error(R.drawable.iv_error)
        .into(this)
    val cache = object : LruCache<String,String>(10){
        override fun sizeOf(key: String?, value: String?): Int {
            return super.sizeOf(key, value)
        }
    }
}