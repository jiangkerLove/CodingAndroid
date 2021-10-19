package jfp.study.coding_android.base

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import jfp.study.coding_android.R
import jfp.study.coding_android.databinding.ActivityLifeBinding
import jfp.study.coding_android.fragment.*

open class LifeActivity : AppCompatActivity() {

    companion object {
        const val TAG = "LifeActivity"
        const val TASK_TAG = "TaskTag"
    }

    lateinit var binding: ActivityLifeBinding

    val model: LiveModel by lazy {
        ViewModelProvider(this)[LiveModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_life)
        Log.i(TAG, "onCreate: ")
        Log.i(TASK_TAG, "$this task is: $taskId")
        binding.vpFragment.apply {
            val lifeAFragment = LifeAFragment()
            supportFragmentManager.beginTransaction()
                .add(lifeAFragment, "Frag-A")
                .setMaxLifecycle(lifeAFragment,Lifecycle.State.STARTED)
                .add(LifeBFragment(),"")
                .show(lifeAFragment)
                .commitAllowingStateLoss()
            setOnClickListener {
                val fragment = supportFragmentManager.findFragmentByTag("Frag-A") ?: return@setOnClickListener
                supportFragmentManager.beginTransaction().hide(lifeAFragment).commitNow()
            }
        }
    }

    class FragmentAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
        private val fragments = arrayOf(
            LifeAFragment(),
            LifeBFragment(),
            LifeCFragment(),
            LifeDFragment(),
            LifeEFragment()
        )

        override fun getItemCount() = fragments.size

        override fun createFragment(position: Int): Fragment = fragments[position]

    }


    override fun onRestart() {
        super.onRestart()
        Log.i(TAG, "onRestart: ")
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart: ")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume: ")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause: ")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy: ")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.i(TAG, "onConfigurationChanged: ")
    }
}