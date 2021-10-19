package jfp.study.player.exo

import android.content.Context
import com.google.android.exoplayer2.SimpleExoPlayer

class ExoPlayer(context: Context) {

    val player = SimpleExoPlayer.Builder(context).build()

}