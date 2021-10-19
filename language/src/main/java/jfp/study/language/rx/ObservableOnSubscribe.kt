package jfp.study.language.rx

interface ObservableOnSubscribe<T> {

    fun setObserver(observer:Observer<T>)
}