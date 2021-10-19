package jfp.study.language.rx

import java.util.concurrent.Executors

enum class Schedulers {
    IO,
    MAIN;

    private var IOThreadPool = Executors.newCachedThreadPool()

    fun <T> submitSubscribeWork(
        source: ObservableOnSubscribe<T>, //上游
        downStream: Observer<T>,//下游
        thread:Schedulers//指定的线程
    ) {
        when(thread){
            IO->{
                IOThreadPool.submit {
                    //从线程池抽取一个线程执行上游和下游的连接操作
                    source.setObserver(downStream)
                }
            }
            MAIN->{

            }
        }

    }

}