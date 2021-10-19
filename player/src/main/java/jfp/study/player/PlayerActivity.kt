package jfp.study.player

import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.google.android.exoplayer2.MediaItem
import jfp.study.player.databinding.ActivityPlayerBinding

class PlayerActivity : AppCompatActivity() {

    lateinit var player: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityPlayerBinding>(this, R.layout.activity_player)
        player = MediaPlayer()
        val url = "https://stream7.iqilu.com/10339/upload_transcode/202002/16/20200216050645YIMfjPq5Nw.mp4"
        val item = MediaItem.fromUri(url)
        val  attributes =AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setLegacyStreamType(AudioManager.STREAM_MUSIC)
            .build()
        player.setAudioAttributes(attributes)
        player.setDataSource(url)
        player.prepareAsync()
        binding.btnPlay.setOnClickListener {
            if (!player.isPlaying) {
                player.stop()
            }else{
                player.start()
            }
        }
    }
}