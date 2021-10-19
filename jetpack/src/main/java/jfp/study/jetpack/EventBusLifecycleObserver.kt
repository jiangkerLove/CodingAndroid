package jfp.study.jetpack

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import org.greenrobot.eventbus.EventBus


class EventBusLifecycleObserver : DefaultLifecycleObserver {

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        EventBus.getDefault().register(owner)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        EventBus.getDefault().unregister(owner)
    }
}