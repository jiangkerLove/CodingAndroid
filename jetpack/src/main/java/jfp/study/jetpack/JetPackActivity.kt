package jfp.study.jetpack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import jfp.study.jetpack.adapter.LiveDataRecyclerAdapter
import jfp.study.jetpack.databinding.ActivityJetpackBinding
import jfp.study.jetpack.event.OneEvent
import jfp.study.jetpack.event.TwoEvent
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class JetPackActivity : AppCompatActivity() {

    private val model: JetPackModel by viewModels()

    lateinit var binding: ActivityJetpackBinding

    private val liveDataList = mutableListOf(
        MutableLiveData<String>("0"),
        MutableLiveData<String>("1"),
        MutableLiveData<String>("2"),
        MutableLiveData<String>("3"),
        MutableLiveData<String>("4"),
        MutableLiveData<String>("5"),
        MutableLiveData<String>("6"),
        MutableLiveData<String>("7"),
        MutableLiveData<String>("8"),
        MutableLiveData<String>("9"),
        MutableLiveData<String>("10"),
        MutableLiveData<String>("11"),
        MutableLiveData<String>("12"),
        MutableLiveData<String>("13"),
        MutableLiveData<String>("14"),
        MutableLiveData<String>("15"),
        MutableLiveData<String>("16"),
        MutableLiveData<String>("17"),
        MutableLiveData<String>("18"),
        MutableLiveData<String>("19")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(EventBusLifecycleObserver())
        binding = DataBindingUtil.setContentView(this, R.layout.activity_jetpack)
        model.showValue1.observe(this, Observer {
            binding.tvClickTime.text = it
        })
        binding.btnOne.setOnClickListener {
            refresh()
        }
        binding.btnTwo.setOnClickListener {
            model.additionValue1()
        }
        binding.recycleList.adapter = LiveDataRecyclerAdapter(liveDataList)

    }

    private fun refresh(){
        liveDataList.forEach {
            it.value = (it.value?.toInt() ?: 1 * 10).toString()
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun doSome(value: TwoEvent) {

    }


    @Subscribe
    fun doSomeThing(value: OneEvent) {

    }
}