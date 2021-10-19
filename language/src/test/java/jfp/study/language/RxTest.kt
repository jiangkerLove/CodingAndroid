package jfp.study.language

import jfp.study.language.rx.Observable
import jfp.study.language.rx.ObservableOnSubscribe
import jfp.study.language.rx.Observer
import org.junit.Test

class RxTest {

    @Test
    fun normal_test(){
        Observable.create(object :ObservableOnSubscribe<String>{
            override fun setObserver(observer: Observer<String>) {
                observer.onNext("testOne")
                observer.onNext("testTwo")
            }
        }).setObserver(object : Observer<String> {
            override fun onSubscribe() {
            }

            override fun onNext(item: String) {
                println(item)
            }

            override fun onError(e: Throwable) {
            }

            override fun onComplete() {
            }

        })
    }
}