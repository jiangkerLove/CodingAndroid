package jfp.study.language.rx

class MapObservable<T,R>(
    private val subscribe: ObservableOnSubscribe<T>,
    private val action:(T) -> R
) : ObservableOnSubscribe<R> {
    override fun setObserver(observer: Observer<R>) {
        subscribe.setObserver(object :Observer<T>{
            override fun onSubscribe() {
                observer.onSubscribe()
            }

            override fun onNext(item: T) {
                observer.onNext(action(item))
            }

            override fun onError(e: Throwable) {
                observer.onError(e)
            }

            override fun onComplete() {
                observer.onComplete()
            }
        })
    }
}