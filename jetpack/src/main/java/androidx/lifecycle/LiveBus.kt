package androidx.lifecycle

object LiveBus {

    class BusLiveData<T>() : MutableLiveData<T>() {
        override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
            super.observe(owner, BusObserver(version, observer))
        }

        inner class BusObserver<T>(
            private var oldVersion: Int,
            private var observer: Observer<T>
            ) : Observer<T> {
            override fun onChanged(t: T?) {
                if (oldVersion < version) {
                    observer.onChanged(t)
                }
            }
        }
    }

    private val bus = HashMap<String, BusLiveData<*>>()

    internal fun <T> register(name: String): LiveData<T> {
        val data: BusLiveData<T> = bus[name] as? BusLiveData<T> ?: BusLiveData<T>()
        bus[name] = data
        return data
    }

    fun <T> post(name: String, t: T) {
        (bus[name] as? BusLiveData<T>?)?.postValue(t)
    }

}