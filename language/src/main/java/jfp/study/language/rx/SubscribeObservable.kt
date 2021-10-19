package jfp.study.language.rx

class SubscribeObservable<T>(
    private val source: ObservableOnSubscribe<T>,
    private val thread: Schedulers
) : ObservableOnSubscribe<T> {

    override fun setObserver(observer: Observer<T>) {
        source.setObserver(observer)
    }

    class SubscribeObserver<T>(val downStream: Observer<T>) : Observer<T> {
        override fun onSubscribe() {

        }

        override fun onNext(item: T) {

        }

        override fun onError(e: Throwable) {

        }

        override fun onComplete() {
        }

    }
}