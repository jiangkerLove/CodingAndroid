## 1 开启多进程模式
- 单个应用使用多进程只有一种方法，那就是给四大组件在AndroiMenifest中指定`android:process`属性。另外还有就是可以通过JNI的native层去fork一个新的进程。
- 如果没有特别的指定运行的进程，那么运行的进程名字为自己的包名<package>
- 若设置`android:process=":remote"`,":"的含义是指要在当前的进程名前加上当前的包名，则进程名为 `<package>:remote`,并且以":"开头的进程属于当前应用的私有进程。
- 若设置为别的形式，例如`com.jiangker.process`，则这种进程为全局进程，其他应用可以通过ShareUID的方式可以和它跑到同一个进程中。Android系统会为每个应用分配一个唯一的UID，具有相同UID的应用才能共享数据。

## 2 多进程带来的问题

#### 1 静态成员和单例模式完全失效
因为Android系统给每一个应用都分配了一个独立的虚拟机，所以内存空间是不相同的，所以不能通过内存来共享数据。
#### 2 线程同步机制完全失效
线程中的同步机制作用于的是同一个对象，因为不是在一个进程中了，对象也不相同了，所以不能使用
#### 3 SharedPreferences的可靠性下降
SharedPreferences不支持多个进程去同时读写xml文件，否则会导致一定几率的数据丢失。
#### 4 Application会多次创建

## 3 多进程的通信方式

- Intent 、Bundle ：要求传递数据能被序列化，实现 Parcelable、Serializable ，适用于四大组件通信。
- 文件共享 ：适用于交换简单的数据实时性不高的场景。
- AIDL：AIDL 接口实质上是系统提供给我们可以方便实现 BInder 的工具。
- Messenger：基于 AIDL 实现，服务端串行处理，主要用于传递消息，适用于低并发一对多通信。
- ContentProvider：基于 Binder 实现，适用于一对多进程间数据共享。（通讯录 短信 等）
- Socket：TCP、UDP，适用于网络数据交换