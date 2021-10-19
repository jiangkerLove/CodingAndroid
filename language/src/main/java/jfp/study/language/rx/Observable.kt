package jfp.study.language.rx

class Observable<T>(
    private val source: ObservableOnSubscribe<T>
) {

    fun setObserver(downStream: Observer<T>) {
        source.setObserver(downStream)
    }

    fun <R> map(action: (T) -> R): Observable<R> {
        val observable = MapObservable<T,R>(source,action)
        return Observable<R>(observable)
    }

    companion object {
        fun <T> create(subscribe: ObservableOnSubscribe<T>): Observable<T> {
            return Observable(subscribe)
        }
    }
}